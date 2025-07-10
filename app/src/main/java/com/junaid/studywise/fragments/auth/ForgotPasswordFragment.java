package com.junaid.studywise.fragments.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.junaid.studywise.AuthActivity;
import com.junaid.studywise.R;

public class ForgotPasswordFragment extends Fragment {
    
    private FirebaseAuth mAuth;
    private EditText emailField;
    
    public ForgotPasswordFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        initComponents(view);
        setupClickListeners(view);

        return view;
    }

    private void initComponents(View view) {
        mAuth = FirebaseAuth.getInstance();
        emailField = view.findViewById(R.id.et_email);
    }

    private void setupClickListeners(View view) {
        // Back button
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
        });

        // Send reset link button
        AppCompatButton sendResetLinkBtn = view.findViewById(R.id.btn_send_reset_link);
        sendResetLinkBtn.setOnClickListener(v -> sendPasswordResetEmail());

        // Sign in link
        view.findViewById(R.id.tv_sign_in).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
        });
    }

    private void sendPasswordResetEmail() {
        String email = emailField.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please enter your email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), 
                            "Password reset email sent to " + email, 
                            Toast.LENGTH_LONG).show();
                        // Navigate back to login
                        ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
                    } else {
                        String errorMessage = task.getException() != null ? 
                            task.getException().getMessage() : 
                            "Failed to send reset email";
                        Toast.makeText(getContext(), 
                            "Error: " + errorMessage, 
                            Toast.LENGTH_LONG).show();
                    }
                });
    }
}
