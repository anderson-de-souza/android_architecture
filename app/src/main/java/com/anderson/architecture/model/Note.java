package com.anderson.architecture.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

@Entity(tableName = "note_table")
public @Data class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;
    
    // @Ignore
    // @ColumnInfo(name = "priority_column")
    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

}
