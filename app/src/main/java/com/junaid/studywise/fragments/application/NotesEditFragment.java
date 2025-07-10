package com.junaid.studywise.fragments.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.junaid.studywise.R;

public class NotesEditFragment extends Fragment {
    
    private EditText etNoteTitle, etNoteContent;
    private int noteId = -1;
    private String originalTitle = "";
    private String originalContent = "";

    public NotesEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        getArgumentsData();
        setupClickListeners();
        populateData();
    }

    private void initViews(View view) {
        etNoteTitle = view.findViewById(R.id.et_note_title);
        etNoteContent = view.findViewById(R.id.et_note);
    }

    private void getArgumentsData() {
        if (getArguments() != null) {
            noteId = getArguments().getInt("noteId", -1);
            originalTitle = getArguments().getString("noteTitle", "");
            originalContent = getArguments().getString("noteContent", "");
        }
    }

    private void setupClickListeners() {
        // Handle back button in the layout if present
        View btnBack = getView().findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }
        
        // Handle share button in the layout if present
        View btnShare = getView().findViewById(R.id.btn_share);
        if (btnShare != null) {
            btnShare.setOnClickListener(v -> shareNote());
        }
        
        // Handle code button in the layout if present
        View btnCode = getView().findViewById(R.id.btn_code);
        if (btnCode != null) {
            btnCode.setOnClickListener(v -> addCode());
        }
        
        // Handle media button in the layout if present
        View btnMedia = getView().findViewById(R.id.btn_media);
        if (btnMedia != null) {
            btnMedia.setOnClickListener(v -> addMedia());
        }
    }

    private void populateData() {
        if (noteId != -1) {
            // Editing existing note
            etNoteTitle.setText(originalTitle);
            etNoteContent.setText(originalContent);
        } else {
            // Creating new note
            etNoteTitle.setText("");
            etNoteContent.setText("");
        }
    }

    private void shareNote() {
        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();
        
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(getContext(), "No content to share", Toast.LENGTH_SHORT).show();
            return;
        }
        
        String shareText = title + "\n\n" + content;
        
        // TODO: Implement actual sharing functionality
        Toast.makeText(getContext(), "Share functionality - coming soon!", Toast.LENGTH_SHORT).show();
    }
    
    private void addCode() {
        // TODO: Implement code formatting functionality
        Toast.makeText(getContext(), "Code formatting - coming soon!", Toast.LENGTH_SHORT).show();
    }
    
    private void addMedia() {
        // TODO: Implement media picker functionality
        Toast.makeText(getContext(), "Media picker - coming soon!", Toast.LENGTH_SHORT).show();
    }
}