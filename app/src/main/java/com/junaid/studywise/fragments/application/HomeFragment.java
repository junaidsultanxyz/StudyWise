package com.junaid.studywise.fragments.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.junaid.studywise.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    
    private CircleImageView profileImage;
    private TextView welcomeText;
    private TextView studyHoursText;
    private AppCompatButton scheduleSessionBtn;
    private AppCompatButton startNewSessionBtn;
    private AppCompatButton browseMaterialsBtn;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        initComponents(view);
        setupClickListeners();
        updateUserInfo();
        
        return view;
    }

    private void initComponents(View view) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        
        profileImage = view.findViewById(R.id.iv_profile_image);
        welcomeText = view.findViewById(R.id.tv_welcome);
        studyHoursText = view.findViewById(R.id.tv_study_hours);
        scheduleSessionBtn = view.findViewById(R.id.btn_schedule_session);
        startNewSessionBtn = view.findViewById(R.id.btn_start_new_session);
        browseMaterialsBtn = view.findViewById(R.id.btn_browse_materials);
        
        // Settings button
        view.findViewById(R.id.btn_settings).setOnClickListener(v -> {
            // TODO: Navigate to settings
            Toast.makeText(getContext(), "Settings clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupClickListeners() {
        scheduleSessionBtn.setOnClickListener(v -> {
            // TODO: Navigate to schedule session
            Toast.makeText(getContext(), "Schedule Session clicked", Toast.LENGTH_SHORT).show();
        });

        startNewSessionBtn.setOnClickListener(v -> {
            // TODO: Navigate to start new session
            Toast.makeText(getContext(), "Start New Session clicked", Toast.LENGTH_SHORT).show();
        });

        browseMaterialsBtn.setOnClickListener(v -> {
            // TODO: Navigate to browse materials
            Toast.makeText(getContext(), "Browse Materials clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateUserInfo() {
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                welcomeText.setText("Welcome back, " + displayName);
            } else {
                welcomeText.setText("Welcome back!");
            }
            
            // TODO: Load user profile image from Firebase Storage or URL
            // For now, using default profile image
        } else {
            welcomeText.setText("Welcome!");
        }
        
        // TODO: Load actual study hours from database
        // For now, using placeholder data
        studyHoursText.setText("12");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserInfo();
    }
}
