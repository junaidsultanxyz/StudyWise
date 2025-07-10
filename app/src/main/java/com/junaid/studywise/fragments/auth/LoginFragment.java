package com.junaid.studywise.fragments.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.junaid.studywise.AuthActivity;
import com.junaid.studywise.MainActivity;
import com.junaid.studywise.R;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private EditText emailField, passwordField;

    public LoginFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initComponents(view);

        Button loginBtn = view.findViewById(R.id.btn_log_in);
        loginBtn.setOnClickListener(v -> performLogin());

        view.findViewById(R.id.btn_close).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new WelcomeFragment(), false);
        });

        view.findViewById(R.id.tv_sign_up).setOnClickListener(task -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new SignUpFragment(), false);
        });

        view.findViewById(R.id.tv_forget_password).setOnClickListener(task -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new ForgotPasswordFragment(), false);
        });

        return view;
    }

    private void initComponents(View view){
        mAuth = FirebaseAuth.getInstance();

        emailField = view.findViewById(R.id.et_email);
        passwordField = view.findViewById(R.id.et_password);
    }

    private void setBackButton(){

    }
    private void performLogin() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();

                        if (user != null && user.isEmailVerified()) {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                            requireActivity().finish();
                        }
                        else {
                            Toast.makeText(getContext(), "Please verify your email before logging in.", Toast.LENGTH_LONG).show();
                            ((AuthActivity) requireActivity()).switchAuthFragment(new VerifyEmailFragment(), false);
                            FirebaseAuth.getInstance().signOut();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}