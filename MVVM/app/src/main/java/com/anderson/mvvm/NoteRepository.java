package com.anderson.mvvm;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class NoteRepository {
    
    private NoteDao noteDao;
    
    private LiveData<List<Note>> allNotes;
    
    public NoteRepository(Application app) {
        NoteDatabase database = NoteDatabase.getInstance(app);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }
    
    public void insert(Note note) {
        new Thread(() -> noteDao.insert(note)).start();
    }
    
    public void delete(Note note) {
        new Thread(() -> noteDao.delete(note)).start();
    }
    
    public void update(Note note) {
        new Thread(() -> noteDao.update(note)).start();
    }
    
    public void deleteAllNotes() {
       new Thread(() -> noteDao.deleteAllNotes()).start();
    }
    
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
    
    
}