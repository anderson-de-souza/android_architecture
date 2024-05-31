package com.anderson.architecture.lifecycle;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.anderson.architecture.database.NoteRepository;
import com.anderson.architecture.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application app) {
        super(app);
        repository = new NoteRepository(app);
        allNotes = repository.getAllNotes();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.shutdown();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

}
