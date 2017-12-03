package com.example.haiminhtran.alarm.Receivers;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.haiminhtran.alarm.AlarmActivity;

/**
 * Created by KA on 12/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, AlarmActivity.class);
        intent1.putExtra("requestCode",intent.getLongExtra("requestCode",0));
        intent1.putExtra("once",intent.getStringExtra("once"));
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
