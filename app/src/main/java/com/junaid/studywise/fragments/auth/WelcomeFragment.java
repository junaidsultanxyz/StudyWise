package com.junaid.studywise.fragments.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.junaid.studywise.R;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {
        // Required empty constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_welcome, container, false);

        Button signUpBtn = view.findViewById(R.id.btn_sign_up);
        Button logInBtn = view.findViewById(R.id.btn_log_in);

        signUpBtn.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .addToBackStack(null)
                        .commit()
        );

        logInBtn.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .addToBackStack(null)
                        .commit()
        );

        return view;
    }
}
