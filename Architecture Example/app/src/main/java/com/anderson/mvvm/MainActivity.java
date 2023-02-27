package com.anderson.mvvm;

import android.content.Intent;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.anderson.mvvm.AddEditNoteActivity;
import com.anderson.mvvm.MainActivity;
import com.anderson.mvvm.NoteAdapter;
import com.anderson.mvvm.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> insertResultLauncher;
    private ActivityResultLauncher<Intent> updateResultLauncher;
    
    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        insertResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> activityResultInsert(result));
        updateResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> activityResultUpdate(result));
        
        binding.buttonAddNote.setOnClickListener(view -> {
            
            var intent = new Intent(this, AddEditNoteActivity.class);
            insertResultLauncher.launch(intent);        
                
        });
        
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        final var noteAdapter = new NoteAdapter();
        binding.recyclerView.setAdapter(noteAdapter);
        
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, allNotes -> noteAdapter.setNotes(allNotes));
        
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder from, RecyclerView.ViewHolder to) {
                return false;    
            }
                            
            @Override
            public void onSwiped(RecyclerView.ViewHolder holder, int direction) {
                noteViewModel.delete(noteAdapter.getNoteAt(holder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
                        
        }).attachToRecyclerView(binding.recyclerView);
        
        noteAdapter.setOnItemClickListener((view, note, adapterPosition) -> {
                
            var intent = new Intent(this, AddEditNoteActivity.class);
            
            intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
            intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
            intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
            intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
            
            updateResultLauncher.launch(intent);
                
        });
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if (item.getItemId() == R.id.delete_all_notes) {
            
            noteViewModel.deleteAllNotes();
            Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            
        }
        
        return super.onOptionsItemSelected(item);
    }
        
    
    public void activityResultInsert(ActivityResult result) {
        
        if (result.getData() != null && result.getResultCode() == RESULT_OK) {
            
            
            String title = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            
            var note = new Note(title, description, priority);
            noteViewModel.insert(note);
            
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            
        }
        
    }
    
    public void activityResultUpdate(ActivityResult result) {
        
        if (result.getData() != null && result.getResultCode() == RESULT_OK) {
            
            int id = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_ID, 0);
            String title = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String description = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            
            if (id != 0) {
                
                var note = new Note(title, description, priority);
                note.setId(id);
                
                noteViewModel.update(note);
             
                Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                
            }
            
        }
        
    }
        
    
}
