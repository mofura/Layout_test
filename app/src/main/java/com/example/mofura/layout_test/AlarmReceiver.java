package com.example.mofura.layout_test;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Alarm Received!", Toast.LENGTH_LONG).show();
        Log.d("AlarmReceiver", "Alarm Received! : " + intent.getIntExtra(Intent.EXTRA_ALARM_COUNT, 0));

        Intent intentService ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            intentService = new Intent(context, TestService.class);
            // Serviceの開始
            // API26以上
            context.startForegroundService(intentService);
        }else {

            intentService = new Intent(context, LayerService.class);
            // Serviceの開始
            // API26未満
            context.startService(intentService);
        }

        Intent intentAlarm = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intentAlarm, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        // one shot
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);

    }

}
