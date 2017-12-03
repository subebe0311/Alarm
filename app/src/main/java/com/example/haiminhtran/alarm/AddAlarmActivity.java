package com.example.haiminhtran.alarm;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.haiminhtran.alarm.Fragments.DayPickFragment;
import com.example.haiminhtran.alarm.Object.Time;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAlarmActivity extends AppCompatActivity implements DayPickFragment.DayPickerListener{


    public static final int MUSIC_SEARCH = 2;

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.indicator)
    TextView indicator;
    @BindView(R.id.chooseIndicator)
    LinearLayout chooseIndicator;
    @BindView(R.id.music)
    TextView music;
    @BindView(R.id.chooseMusic)
    LinearLayout chooseMusic;
    @BindView(R.id.time_picker)
    TimePicker timePicker;


    Time time=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        ButterKnife.bind(this);
        if(time == null) {
            time = new Time();
            time.setIndicator(Time.Indicator.ONCE);
            time.setHour(String.valueOf(timePicker.getCurrentHour()));
            time.setMinute(String.valueOf(timePicker.getCurrentMinute()));
        }
        setUpUI();


    }

    void setUpUI()
    {
        chooseIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(AddAlarmActivity.this,view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.menu_indicator,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener( new IndicatorListener());
                popupMenu.show();

            }
        });

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int m) {
                time.setHour(String.valueOf(h));
                time.setMinute(String.valueOf(m));
            }
        });


        chooseMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExternalStorageReadable()){
                    performMusicSearch();
                }
                else {
                    Toast.makeText(AddAlarmActivity.this," sd card không khả dụng",Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.putExtra("time",(Serializable) time);
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void performMusicSearch(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("audio/mpeg3");
        startActivityForResult(intent,MUSIC_SEARCH);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MUSIC_SEARCH && resultCode == RESULT_OK && data != null){
            time.setMusic(data.getData());
            String name  =data.getData().getLastPathSegment();
            music.setText(name);
            return ;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public void chooseDay(Time.Days day, boolean b) {
        time.enableDay(day,b);
    }

    class IndicatorListener implements PopupMenu.OnMenuItemClickListener{
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.once:

                    time.setIndicator(Time.Indicator.ONCE);
                    indicator.setText(getString(R.string.once));
                    return true;

                case R.id.every_day:
                    time.setIndicator(Time.Indicator.EVERYDAY);
                    indicator.setText(getString(R.string.everyday));
                   return true;

                case R.id.daypick:
                    time.setIndicator(Time.Indicator.DAY);
                    indicator.setText(getString(R.string.daypick));
                    DayPickFragment dayPickFragment = new DayPickFragment();
                    dayPickFragment.setDays(time.getPickedDay());
                    dayPickFragment.setListener(AddAlarmActivity.this);
                    dayPickFragment.show(getSupportFragmentManager(),"pickDayFragment");
                    return true;

                default:
                    return false;

            }

        }
    }
}
