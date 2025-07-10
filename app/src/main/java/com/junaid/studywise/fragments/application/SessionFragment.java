package com.junaid.studywise.fragments.application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.junaid.studywise.R;

public class SessionFragment extends Fragment {

    // Static variables to persist timer state across fragment switches
    private static Handler staticTimerHandler;
    private static Runnable staticTimerRunnable;
    private static boolean isTimerRunning = false;
    private static boolean isSessionStarted = false;
    private static long totalTimeInSeconds = 0;

    private TextView tvHours, tvMinutes, tvSeconds;
    private AppCompatButton btnStart, btnStop, btnAddNotes;

    public SessionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session, container, false);
        
        initComponents(view);
        setupClickListeners();
        setupTimer();
        
        // Update UI based on current timer state
        updateUIState();
        updateTimerDisplay();
        
        return view;
    }

    private void initComponents(View view) {
        tvHours = view.findViewById(R.id.tv_hours);
        tvMinutes = view.findViewById(R.id.tv_minutes);
        tvSeconds = view.findViewById(R.id.tv_seconds);
        btnStart = view.findViewById(R.id.btn_start);
        btnStop = view.findViewById(R.id.btn_stop);
        btnAddNotes = view.findViewById(R.id.btn_add_notes);
        
        // Set initial state
        btnStart.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0);
        btnStart.setCompoundDrawablePadding(8);
        
        // Close button
        view.findViewById(R.id.btn_close).setOnClickListener(v -> {
            if (isTimerRunning) {
                pauseTimer();
            }
            // Navigate back to previous fragment
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
        
        // Initially disable stop button
        btnStop.setEnabled(false);
    }

    private void setupClickListeners() {
        btnStart.setOnClickListener(v -> {
            if (!isSessionStarted) {
                startSession();
            } else if (isTimerRunning) {
                pauseTimer();
            } else {
                resumeTimer();
            }
        });
        
        btnStop.setOnClickListener(v -> endSession());
        
        btnAddNotes.setOnClickListener(v -> {
            // TODO: Navigate to notes
            Toast.makeText(getContext(), "Add Notes clicked", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupTimer() {
        // Initialize static timer only once
        if (staticTimerHandler == null) {
            staticTimerHandler = new Handler(Looper.getMainLooper());
            staticTimerRunnable = new Runnable() {
                @Override
                public void run() {
                    if (isTimerRunning) {
                        totalTimeInSeconds++;
                        // Update display only if fragment is currently visible
                        if (getActivity() != null && isAdded() && !isDetached()) {
                            updateTimerDisplay();
                        }
                        staticTimerHandler.postDelayed(this, 1000);
                    }
                }
            };
        }
    }

    private void startSession() {
        isSessionStarted = true;
        isTimerRunning = true;
        updateUIState();
        
        if (staticTimerHandler != null) {
            staticTimerHandler.post(staticTimerRunnable);
        }
        Toast.makeText(getContext(), "Study session started!", Toast.LENGTH_SHORT).show();
    }

    private void pauseTimer() {
        isTimerRunning = false;
        updateUIState();
        
        if (staticTimerHandler != null) {
            staticTimerHandler.removeCallbacks(staticTimerRunnable);
        }
        Toast.makeText(getContext(), "Session paused", Toast.LENGTH_SHORT).show();
    }

    private void resumeTimer() {
        isTimerRunning = true;
        updateUIState();
        
        if (staticTimerHandler != null) {
            staticTimerHandler.post(staticTimerRunnable);
        }
        Toast.makeText(getContext(), "Session resumed", Toast.LENGTH_SHORT).show();
    }

    private void endSession() {
        isTimerRunning = false;
        isSessionStarted = false;
        updateUIState();
        
        if (staticTimerHandler != null) {
            staticTimerHandler.removeCallbacks(staticTimerRunnable);
        }
        
        // Show session summary
        String sessionTime = String.format("%02d:%02d:%02d",
            totalTimeInSeconds / 3600, 
            (totalTimeInSeconds % 3600) / 60, 
            totalTimeInSeconds % 60);
        Toast.makeText(getContext(), "Session ended! Total time: " + sessionTime, Toast.LENGTH_LONG).show();
        
        // Reset timer for next session
        totalTimeInSeconds = 0;
        updateTimerDisplay();
    }

    private void updateTimerDisplay() {
        if (tvHours != null && tvMinutes != null && tvSeconds != null) {
            long hours = totalTimeInSeconds / 3600;
            long minutes = (totalTimeInSeconds % 3600) / 60;
            long seconds = totalTimeInSeconds % 60;
            
            tvHours.setText(String.format("%02d", hours));
            tvMinutes.setText(String.format("%02d", minutes));
            tvSeconds.setText(String.format("%02d", seconds));
        }
    }

    private void updateUIState() {
        if (btnStart != null && btnStop != null) {
            if (!isSessionStarted) {
                // Initial state
                btnStart.setText("Start");
                btnStart.setBackgroundResource(R.drawable.button_primary);
                btnStart.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0);
                btnStart.setCompoundDrawablePadding(8);
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
            } else if (isTimerRunning) {
                // Running state
                btnStart.setText("Pause");
                btnStart.setBackgroundResource(R.drawable.button_pause);
                btnStart.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause, 0, 0, 0);
                btnStart.setCompoundDrawablePadding(8);
                btnStart.setEnabled(true);
                btnStop.setEnabled(true);
            } else {
                // Paused state
                btnStart.setText("Resume");
                btnStart.setBackgroundResource(R.drawable.button_primary);
                btnStart.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play, 0, 0, 0);
                btnStart.setCompoundDrawablePadding(8);
                btnStart.setEnabled(true);
                btnStop.setEnabled(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Don't stop the timer when fragment is destroyed
        // Only stop if the activity is finishing
        if (getActivity() != null && getActivity().isFinishing()) {
            if (staticTimerHandler != null) {
                staticTimerHandler.removeCallbacks(staticTimerRunnable);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Timer continues running in background
        // No need to stop it for fragment switches
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update UI state and display when fragment becomes visible
        updateUIState();
        updateTimerDisplay();
        
        // Restart timer display updates if timer is running
        if (isTimerRunning && staticTimerHandler != null) {
            staticTimerHandler.post(staticTimerRunnable);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // Don't stop the timer when fragment is detached
        // Timer should continue running across fragment switches
    }

    // Static method to clean up timer when app is really finished
    public static void cleanupTimer() {
        if (staticTimerHandler != null) {
            staticTimerHandler.removeCallbacks(staticTimerRunnable);
            staticTimerHandler = null;
            staticTimerRunnable = null;
        }
        isTimerRunning = false;
        isSessionStarted = false;
        totalTimeInSeconds = 0;
    }

    // Static method to get current timer state (useful for other parts of the app)
    public static boolean isSessionActive() {
        return isSessionStarted;
    }

    public static boolean isTimerCurrentlyRunning() {
        return isTimerRunning;
    }

    public static long getCurrentSessionTime() {
        return totalTimeInSeconds;
    }
}
