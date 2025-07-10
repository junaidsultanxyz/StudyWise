package com.junaid.studywise.fragments.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.junaid.studywise.custom_adapters.NotesAdapter;
import com.junaid.studywise.model.Note;
import com.junaid.studywise.R;

import java.util.ArrayList;

public class NotesFragment extends Fragment {
    private ListView listViewNotes;
    private FloatingActionButton fabAddNote;
    private NotesAdapter notesAdapter;
    private ArrayList<Note> notesList;
    private View rootView;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance() {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get any arguments passed to fragment
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
        setupNotesList();
        setupAdapter();
        setupClickListeners();
    }

    private void initializeViews() {
        listViewNotes = rootView.findViewById(R.id.listview_notes);
        fabAddNote = rootView.findViewById(R.id.fab_add_note);
    }

    private void setupNotesList() {
        notesList = new ArrayList<>();

        // Sample data - replace with your backend API calls
        notesList.add(new Note(1, "Note Title 1", "Summary of the note content", "Full content here", "2024-01-01", "2024-01-01"));
        notesList.add(new Note(2, "Note Title 2", "Summary of the note content", "Full content here", "2024-01-02", "2024-01-02"));
        notesList.add(new Note(3, "Note Title 3", "Summary of the note content", "Full content here", "2024-01-03", "2024-01-03"));
        notesList.add(new Note(4, "Note Title 4", "Summary of the note content", "Full content here", "2024-01-04", "2024-01-04"));
        notesList.add(new Note(5, "Note Title 5", "Summary of the note content", "Full content here", "2024-01-05", "2024-01-05"));

        // TODO: Replace with actual backend call
        // loadNotesFromBackend();
    }

    private void setupAdapter() {
        notesAdapter = new NotesAdapter(getContext(), notesList);
        listViewNotes.setAdapter(notesAdapter);
    }

    private void setupClickListeners() {
        // ListView item click listener
        listViewNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = notesList.get(position);
                // Handle note click - open note details
                Toast.makeText(getContext(), "Clicked: " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();

                // Navigate to note edit fragment using Navigation Component
                Bundle args = new Bundle();
                args.putInt("noteId", selectedNote.getId());
                args.putString("noteTitle", selectedNote.getTitle());
                args.putString("noteContent", selectedNote.getContent());
                
                androidx.navigation.Navigation.findNavController(view)
                    .navigate(R.id.action_notes_to_notes_edit, args);

                // Or if using FragmentManager:
                // FragmentManager fragmentManager = getParentFragmentManager();
                // NoteDetailsFragment detailsFragment = NoteDetailsFragment.newInstance(selectedNote.getId());
                // fragmentManager.beginTransaction()
                //     .replace(R.id.fragment_container, detailsFragment)
                //     .addToBackStack(null)
                //     .commit();
            }
        });

        // ListView item long click listener (for delete/edit options)
        listViewNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Note selectedNote = notesList.get(position);
                // Handle long click - show context menu or delete option
                Toast.makeText(getContext(), "Long clicked: " + selectedNote.getTitle(), Toast.LENGTH_SHORT).show();

                // TODO: Show context menu, delete confirmation, or edit options
                showNoteOptionsDialog(selectedNote, position);
                return true; // Return true to consume the long click event
            }
        });

        // FAB click listener
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add new note
                Toast.makeText(getContext(), "Add new note", Toast.LENGTH_SHORT).show();

                // Navigate to note edit fragment for creating a new note
                Bundle args = new Bundle();
                args.putInt("noteId", -1); // -1 indicates new note
                args.putString("noteTitle", "");
                args.putString("noteContent", "");
                
                Navigation.findNavController(v)
                    .navigate(R.id.action_notes_to_notes_edit, args);

                // Or if using FragmentManager:
                // FragmentManager fragmentManager = getParentFragmentManager();
                // AddNoteFragment addNoteFragment = AddNoteFragment.newInstance();
                // fragmentManager.beginTransaction()
                //     .replace(R.id.fragment_container, addNoteFragment)
                //     .addToBackStack(null)
                //     .commit();
            }
        });
    }

    private void showNoteOptionsDialog(Note note, int position) {
        // TODO: Implement options dialog (Edit, Delete, Share, etc.)
        // You can use AlertDialog or BottomSheetDialog
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Note Options")
               .setItems(new String[]{"Edit", "Delete", "Share"}, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which) {
                           case 0: // Edit
                               // Navigate to edit note
                               break;
                           case 1: // Delete
                               deleteNote(position);
                               break;
                           case 2: // Share
                               shareNote(note);
                               break;
                       }
                   }
               })
               .show();
        */
    }

    // Method to refresh notes list (call this when returning from other fragments)
    public void refreshNotesList() {
        // TODO: Fetch updated notes from your backend
        if (notesAdapter != null) {
            notesAdapter.notifyDataSetChanged();
        }
    }

    // Method to add a new note to the list
    public void addNewNote(Note note) {
        if (notesAdapter != null) {
            notesAdapter.addNote(note);
        }
    }

    // Method to remove a note from the list
    public void removeNote(int position) {
        if (notesAdapter != null) {
            notesAdapter.removeNote(position);
        }
    }

    // Method to update a specific note
    public void updateNote(Note updatedNote) {
        for (int i = 0; i < notesList.size(); i++) {
            if (notesList.get(i).getId() == updatedNote.getId()) {
                notesList.set(i, updatedNote);
                notesAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    // Method to load notes from backend (replace sample data)
    private void loadNotesFromBackend() {
        // TODO: Implement your backend API call here
        // Example:
        /*
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<Note>> call = apiService.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    notesList.clear();
                    notesList.addAll(response.body());
                    notesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load notes", Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    // Method to delete a note
    private void deleteNote(int position) {
        Note noteToDelete = notesList.get(position);

        // TODO: Call backend API to delete note
        // After successful deletion, remove from list
        removeNote(position);
        Toast.makeText(getContext(), "Note deleted", Toast.LENGTH_SHORT).show();
    }

    // Method to share a note
    private void shareNote(Note note) {
        // TODO: Implement note sharing functionality
        /*
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, note.getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, note.getContent());
        startActivity(Intent.createChooser(shareIntent, "Share Note"));
        */
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh notes when fragment becomes visible
        refreshNotesList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up references
        rootView = null;
        listViewNotes = null;
        fabAddNote = null;
        notesAdapter = null;
    }
}