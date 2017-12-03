package com.example.haiminhtran.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.haiminhtran.alarm.Object.Time;
import com.example.haiminhtran.alarm.Receivers.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class AlarmActivity extends AppCompatActivity {

    MediaPlayer player;
    @BindView(R.id.btn_sleep)
    TextView btnSleep;
    @BindView(R.id.btn_cancel_alarm)
    TextView btnCancel;
    @BindView(R.id.text_time_display)
    TextView text;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);

       int hour =  Calendar.getInstance().get(Calendar.HOUR);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
         String m = minute<=9?"0"+minute:""+minute;
        text.setText(hour+":"+m);

        Intent intent = getIntent();
        if(intent.getStringExtra("once")!=null) {
            if (intent.getStringExtra("once").equals("once")) {
                Time time = Time.findById(Time.class, intent.getLongExtra("requestCode", 0));
                time.enable = false;
                time.save();
            }
        }
        player = MediaPlayer.create(this,R.raw.alarm);
        player.start();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.start();
            }
        });

        btnSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                Long id =intent.getLongExtra("requestCode",0);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.MINUTE,2);
                AlarmManager alarm =(AlarmManager) getSystemService(ALARM_SERVICE);
                Intent intentRemaining = new Intent(AlarmActivity.this,AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),id.intValue(),intentRemaining,0);
                alarm.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                player.stop();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Long id =intent.getLongExtra("requestCode",0);
                Intent intentCancel = new Intent(AlarmActivity.this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),id.intValue(),intentCancel,0);
                AlarmManager alarm =(AlarmManager) getSystemService(ALARM_SERVICE);
                alarm.cancel(pendingIntent);
                player.stop();
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }
}
