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

    private OnItemClickListener onItemClickListener;
    private List<Note> notes = new ArrayList<>();

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    public Note getNoteAt(int position) {
        return notes.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding noteItem;

        public NoteViewHolder(View itemView) {
            super(itemView);
            
            this.noteItem = NoteItemBinding.bind(itemView);
            
            noteItem.getRoot().setOnClickListener(view -> {
                
                int position = getAdapterPosition();
                    
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(view, notes.get(position), position);
                }
                    
            });
            
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

    public interface OnItemClickListener {
        void onItemClick(View view, Note note, int adapterPosition);
    }
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    
}
