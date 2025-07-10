package com.junaid.studywise.fragments.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.junaid.studywise.AuthActivity;
import com.junaid.studywise.R;

public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText emailField, passwordField, nameField;

    public SignUpFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        emailField = view.findViewById(R.id.et_email);
        passwordField = view.findViewById(R.id.et_password);
        nameField = view.findViewById(R.id.et_full_name); // optional
        Button signUpBtn = view.findViewById(R.id.btn_sign_up);

        signUpBtn.setOnClickListener(v -> performSignUp());

        view.findViewById(R.id.btn_close).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new WelcomeFragment(), false);
        });

        view.findViewById(R.id.tv_sign_in).setOnClickListener(task -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
        });

        return view;
    }

    private void performSignUp() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();

                        if (user != null) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameField.getText().toString())
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(task2 -> {
                                        if (task.isSuccessful()) {
                                            Log.d("Firebase", "User profile updated.");
                                        }
                                    });

                            user.sendEmailVerification()
                                    .addOnCompleteListener(task3 -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Verification email sent to " + user.getEmail(), Toast.LENGTH_LONG).show();

//                                            FirebaseAuth.getInstance().signOut();
                                            ((AuthActivity) requireActivity()).switchAuthFragment(new VerifyEmailFragment(), false);
                                        } else {
                                            Toast.makeText(getContext(), "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        // Optionally go to main app
                        // startActivity(new Intent(getActivity(), MainActivity.class));
                    } else {
                        Toast.makeText(getContext(), "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
