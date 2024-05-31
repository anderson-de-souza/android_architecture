package com.anderson.architecture.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.anderson.architecture.model.Note;

@Database(entities = { Note.class }, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    
    private static NoteDatabase instance;
    
    public abstract NoteDAO noteDAO();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database")
            .fallbackToDestructiveMigration()
            .addCallback(roomCallBack)
            .build();
        }
        return instance;
    }
    
    public static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            
            new Thread(() -> {
                var noteDao = instance.noteDAO();
                noteDao.insert(new Note("Title 1", "Description 1", 1));
                noteDao.insert(new Note("Title 2", "Description 2", 2));
                noteDao.insert(new Note("Title 3", "Description 3", 3));
                noteDao.insert(new Note("Title 4", "Description 4", 1));
                noteDao.insert(new Note("Title 5", "Description 5", 3));
            }).start();
            
        }
        
    };
    
}
