package com.anderson.architecture.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.anderson.architecture.database.NoteDAO;
import com.anderson.architecture.database.NoteDatabase;
import com.anderson.architecture.model.Note;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    
    private NoteDAO noteDao;
    
    private LiveData<List<Note>> allNotes;

    private ExecutorService executor;
    
    public NoteRepository(Application app) {
        executor = Executors.newSingleThreadExecutor();
        NoteDatabase database = NoteDatabase.getInstance(app);
        noteDao = database.noteDAO();
        allNotes = noteDao.getAllNotes();
    }
    
    public void insert(Note note) {
        executor.execute(() -> noteDao.insert(note));
    }
    
    public void delete(Note note) {
        executor.execute(() -> noteDao.delete(note));
    }
    
    public void update(Note note) {
        executor.execute(() -> noteDao.update(note));
    }
    
    public void deleteAllNotes() {
       executor.execute(() -> noteDao.deleteAllNotes());
    }
    
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void shutdown() {
        executor.shutdown();
    }
    
}