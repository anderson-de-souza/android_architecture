package com.anderson.mvvm;

import android.content.Intent;
import android.os.*;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.anderson.mvvm.NoteAdapter;
import com.anderson.mvvm.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityResultCallback<ActivityResult> {

    private ActivityResultLauncher<Intent> resultLauncher;
    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), this);
        
        binding.buttonAddNote.setOnClickListener(view -> {
            
            var intent = new Intent(this, AddNoteActivity.class);
            resultLauncher.launch(intent);        
                
        });
        
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        final var noteAdapter = new NoteAdapter();
        binding.recyclerView.setAdapter(noteAdapter);
        
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {

            @Override
            public void onChanged(List<Note> allNotes) {
                noteAdapter.setNotes(allNotes);
                Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();            
            }

        });
        
        
        
    }
    
    @Override
    public void onActivityResult(ActivityResult result) {
        
        if (result.getData() != null && result.getResultCode() == RESULT_OK) {
            
            
            String title = result.getData().getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String description = result.getData().getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int priority = result.getData().getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);
            
            var note = new Note(title, description, priority);
            noteViewModel.insert(note);
            
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            
        }
        
    }
        
    
}
