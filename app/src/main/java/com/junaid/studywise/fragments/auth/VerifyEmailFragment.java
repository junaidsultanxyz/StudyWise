package com.junaid.studywise.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.junaid.studywise.AuthActivity;
import com.junaid.studywise.R;

public class VerifyEmailFragment extends Fragment {
    
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    
    public VerifyEmailFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify_email, container, false);

        initComponents(view);
        setupClickListeners(view);

        return view;
    }

    private void initComponents(View view) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    private void setupClickListeners(View view) {
        // Close button
        view.findViewById(R.id.btn_close).setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new WelcomeFragment(), false);
        });

        // Resend email button
        Button resendEmailBtn = view.findViewById(R.id.btn_resend_email);
        resendEmailBtn.setOnClickListener(v -> resendVerificationEmail());

        // Back to login button
        Button backToLoginBtn = view.findViewById(R.id.btn_back_to_login);
        backToLoginBtn.setOnClickListener(v -> {
            ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
        });
    }

    private void resendVerificationEmail() {
        if (currentUser != null) {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(), 
                                "Verification email sent to " + currentUser.getEmail(), 
                                Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), 
                                "Failed to send verification email: " + task.getException().getMessage(), 
                                Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(getContext(), 
                "No user found. Please sign up first.", 
                Toast.LENGTH_SHORT).show();
            ((AuthActivity) requireActivity()).switchAuthFragment(new SignUpFragment(), false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check if user has verified their email when returning to this fragment
        if (currentUser != null) {
            currentUser.reload().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    currentUser = mAuth.getCurrentUser();
                    if (currentUser != null && currentUser.isEmailVerified()) {
                        Toast.makeText(getContext(), 
                            "Email verified successfully!", 
                            Toast.LENGTH_SHORT).show();
                        ((AuthActivity) requireActivity()).switchAuthFragment(new LoginFragment(), false);
                    }
                }
            });
        }
    }
}
