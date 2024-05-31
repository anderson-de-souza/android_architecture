package com.anderson.architecture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anderson.architecture.R;
import com.anderson.architecture.databinding.NoteItemBinding;
import com.anderson.architecture.model.Note;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder> {

    private OnItemClickListener onItemClickListener;
    
    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }
    
    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<>() {
        
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId();
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle())
            && oldItem.getDescription().equals(newItem.getDescription())
            && oldItem.getPriority() == newItem.getPriority();
        }
        
    };

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public Note getNoteAt(int position) {
        return getItem(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private NoteItemBinding noteItem;

        public NoteViewHolder(View itemView) {
            super(itemView);
            
            this.noteItem = NoteItemBinding.bind(itemView);
            
            noteItem.getRoot().setOnClickListener(view -> {
                
                int position = getAdapterPosition();
                    
                if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(view, getItem(position), position);
                }
                    
            });
            
        }

        public void onBind(@NonNull Note note) {
            
            noteItem.viewDescription.setText(note.getDescription());
            noteItem.viewPriority.setText(String.valueOf(note.getPriority()));
            noteItem.viewTitle.setText(note.getTitle());
            
        }
        
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Note note, int adapterPosition);
    }
    
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    
}
