package com.example.haiminhtran.alarm;

import android.content.Intent;
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

import com.example.haiminhtran.alarm.Fragments.DayPickFragment;
import com.example.haiminhtran.alarm.Object.Time;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditAlarmActivity extends AppCompatActivity implements DayPickFragment.DayPickerListener{

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
        Intent intent = getIntent();
        time =(Time) intent.getSerializableExtra("time");
        setUpUI();

    }
    void setUpUI(){
        indicator.setText(time.getIndicator().toString());
        timePicker.setCurrentHour(Integer.parseInt(time.getHour()));
        timePicker.setCurrentMinute(Integer.parseInt(time.getMinute()));
        chooseIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(EditAlarmActivity.this,view);
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                intent.putExtra("time",time);
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
                    dayPickFragment.setListener(EditAlarmActivity.this);
                    dayPickFragment.show(getSupportFragmentManager(),"pickDayFragment");
                    return true;

                default:
                    return false;

            }

        }
    }


    @Override
    public void chooseDay(Time.Days day, boolean b) {
        time.enableDay(day,b);
    }


}
