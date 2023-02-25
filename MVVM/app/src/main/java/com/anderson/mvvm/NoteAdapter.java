package com.anderson.mvvm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.anderson.mvvm.Note;
import com.anderson.mvvm.databinding.NoteItemBinding;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes = new ArrayList<>();

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new NoteViewHolder(NoteItemBinding.inflate(layoutInflater));
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding noteItem;

        public NoteViewHolder(NoteItemBinding noteItem) {
            super(noteItem.getRoot());
            this.noteItem = noteItem;
        }

        public void bind(Note note) {
            noteItem.viewDescription.setText(note.getDescription());
            noteItem.viewPriority.setText(String.valueOf(note.getPriority()));
            noteItem.viewTitle.setText(note.getTitle());
        }
        
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }
    
}
