package com.anderson.mvvm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String error = intent.getStringExtra("error");
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
        
    
}
