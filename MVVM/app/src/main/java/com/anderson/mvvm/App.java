package com.anderson.mvvm;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.core.app.AlarmManagerCompat;
import com.anderson.mvvm.AlarmReceiver;
import java.io.PrintWriter;
import java.io.StringWriter;

public class App extends Application {
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        final var handler = Thread.getDefaultUncaughtExceptionHandler();
        
        Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> {
            var intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("error", toString(exception));
            var pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            var alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            AlarmManagerCompat.setExact(alarmManager, AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);
            handler.uncaughtException(thread, exception);
        });
        
    }
    
    public static String toString(Throwable error) {
        
        final var stringWriter = new StringWriter();
        final var printWriter = new PrintWriter(stringWriter);
        
        do {
            
            error.printStackTrace(printWriter);
            error = error.getCause();
            
        } while (error != null);
        
        String result = stringWriter.toString();
        printWriter.close();
        
        return result;
        
    }
        
    
}
