package com.anderson.architecture.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class AlarmReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, @NonNull Intent intent) {
        String error = intent.getStringExtra("error");
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
        
    
}
