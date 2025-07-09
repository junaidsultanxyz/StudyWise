package com.junaid.studywise.custom_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.junaid.studywise.model.Note;
import com.junaid.studywise.R;

import java.util.ArrayList;

public class NotesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Note> notesList;
    private LayoutInflater inflater;

    public NotesAdapter(Context context, ArrayList<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notesList.size();
    }

    @Override
    public Object getItem(int position) {
        return notesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notesList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_note, parent, false);
            holder = new ViewHolder();
            holder.noteIcon = convertView.findViewById(R.id.iv_note_icon);
            holder.noteTitle = convertView.findViewById(R.id.tv_note_title);
            holder.noteSummary = convertView.findViewById(R.id.tv_note_summary);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Note note = notesList.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteSummary.setText(note.getSummary());

        return convertView;
    }

    // ViewHolder pattern for better performance
    private static class ViewHolder {
        ImageView noteIcon;
        TextView noteTitle;
        TextView noteSummary;
    }

    // Method to update the list
    public void updateNotes(ArrayList<Note> newNotesList) {
        this.notesList = newNotesList;
        notifyDataSetChanged();
    }

    // Method to add a new note
    public void addNote(Note note) {
        notesList.add(0, note); // Add at the beginning
        notifyDataSetChanged();
    }

    // Method to remove a note
    public void removeNote(int position) {
        if (position >= 0 && position < notesList.size()) {
            notesList.remove(position);
            notifyDataSetChanged();
        }
    }
}