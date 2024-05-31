package com.anderson.architecture;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.anderson.architecture.databinding.ActivityAddEditNoteBinding;

public class AddEditNoteActivity extends AppCompatActivity {
    
    public static final String EXTRA_ID = "com.anderson.mvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.anderson.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.anderson.mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.anderson.mvvm.EXTRA_PRIORITY";
    
    private ActivityAddEditNoteBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        
        binding.selectPriority.setMinValue(1);
        binding.selectPriority.setMaxValue(10);
        
        Intent intent = getIntent();
        
        if (intent.hasExtra(EXTRA_ID)) {
            
            setTitle("Edit Note");
            
            binding.fieldTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            binding.fieldDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            binding.selectPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            
        } else {
            setTitle("Add Note");
        }
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        if (item.getItemId() == R.id.save_note) {
            saveNote();
        }
        
        return super.onOptionsItemSelected(item);
    }
        
    
    public void saveNote() {
        
        String title = binding.fieldTitle.getText().toString().trim();
        String description = binding.fieldDescription.getText().toString().trim();
        int priority = binding.selectPriority.getValue();
        
        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        
        var intent = new Intent();
        
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_DESCRIPTION, description);
        intent.putExtra(EXTRA_PRIORITY, priority);
        
        int id = getIntent().getIntExtra(EXTRA_ID, 0);
        
        if (id != 0) {
            intent.putExtra(EXTRA_ID, id);
        }
        
        setResult(RESULT_OK, intent);
        finish();
        
    }
        
    
}
