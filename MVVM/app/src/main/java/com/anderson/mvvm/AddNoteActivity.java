package com.anderson.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.anderson.mvvm.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {
    
    public static final String EXTRA_TITLE = "com.anderson.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.anderson.mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.anderson.mvvm.EXTRA_PRIORITY";
    
    private ActivityAddNoteBinding binding;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.selectPriority.setMinValue(1);
        binding.selectPriority.setMaxValue(10);
        
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
        
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
        
        setResult(RESULT_OK, intent);
        finish();
        
    }
        
    
}
