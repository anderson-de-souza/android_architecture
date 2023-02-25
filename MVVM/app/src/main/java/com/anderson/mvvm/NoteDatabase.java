package com.anderson.mvvm;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.os.AsyncTask;

@Database(entities = { Note.class }, version = 1, exportSchema = false) 
public abstract class NoteDatabase extends RoomDatabase {
    
    private static NoteDatabase instance;
    
    public abstract NoteDao noteDao();

    @SuppressLint("deprecated")
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
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
        
    };
    
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        
        private NoteDao noteDao;
        
        public PopulateDbAsyncTask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }
        
        @Override
        public Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            noteDao.insert(new Note("Title 4", "Description 4", 1));
            noteDao.insert(new Note("Title 5", "Description 5", 3));
            return null;
        }
        
    }
    
}
