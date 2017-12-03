package com.example.haiminhtran.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.haiminhtran.alarm.Adapters.ClockingAdapter;
import com.example.haiminhtran.alarm.Adapters.CounterAdapter;
import com.example.haiminhtran.alarm.Adapters.TimeAdapter;

import com.example.haiminhtran.alarm.Adapters.ViewPagerAdapter;
import com.example.haiminhtran.alarm.Fragments.AlarmFragment;
import com.example.haiminhtran.alarm.Fragments.ClockingFragment;
import com.example.haiminhtran.alarm.Fragments.CounterFragment;
import com.example.haiminhtran.alarm.Object.Clocking;
import com.example.haiminhtran.alarm.Object.Counter;
import com.example.haiminhtran.alarm.Object.Time;
import com.example.haiminhtran.alarm.Receivers.AlarmReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TimeAdapter.SendAlarmListener,TimeAdapter.EditAlarmListener {


    public static final int  EDIT_TIMER = 192;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    TimeAdapter timeAdapter;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpViewPager();


    }


    void setUpViewPager()
    {
        //create fragment view pager
        AlarmFragment alarmFragment = new AlarmFragment();
        List<Time> times = Time.listAll(Time.class);
        if(times == null )
            times = new ArrayList<>();
        timeAdapter = new TimeAdapter();
        timeAdapter.setTimes(times);
        timeAdapter.setSendAlarmManager(this);
        timeAdapter.setEditAlarmListener(this);
        timeAdapter.setContext(this);
        alarmFragment.setAdapter(timeAdapter);




        ClockingFragment clockingFragment = new ClockingFragment();
        List<Clocking> clockings = Clocking.listAll(Clocking.class);
        if(clockings == null)
            clockings = new ArrayList<>();
        ClockingAdapter clockingAdapter = new ClockingAdapter();
        clockingAdapter.setClockings(clockings);
        clockingFragment.setAdapter(clockingAdapter);




        CounterFragment counterFragment = new CounterFragment();
        List<Counter> counters = Counter.listAll(Counter.class);
       if(counters == null) {
            counters = new ArrayList<>();
            Counter c = new Counter(0,0,5);
            c.save();
            counters.add(c);
            c = new Counter(0,1,0);
            c.save();
            counters.add(c);
            c = new Counter(0,5,0);
            c.save();
           counters.add(c);
            c = new Counter(1,0,0);
            c.save();
           counters.add(c);

       }
        CounterAdapter counterAdapter = new CounterAdapter();
        counterAdapter.setCounters(counters);
        counterFragment.setAdapter(counterAdapter);

        //view pager
        ArrayList<Fragment> fragments = new ArrayList<>(3);
        fragments.add(alarmFragment);
        fragments.add(clockingFragment);
        fragments.add(counterFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.setFragments(fragments);
        mViewPager.setAdapter(viewPagerAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.background));
                        return;

                    case 1:
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.clocking_background));
                        return;
                    case 2:
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.counter_background));
                    default:
                        return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // tab layout
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(getString(R.string.alarm));
        mTabLayout.getTabAt(1).setText(getString(R.string.clocking));
        mTabLayout.getTabAt(2).setText(getString(R.string.counting_time));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.unselect_text),getResources().getColor(R.color.selected_text));
        mTabLayout.setBackgroundColor(getResources().getColor(R.color.background));
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.indicator));

    }

    @Override
    public void edit(Time time) {
        Intent intent= new Intent(this,EditAlarmActivity.class);
        intent.putExtra("time",time);
        startActivityForResult(intent,EDIT_TIMER);
    }

    @Override
    public void send(Time time, boolean isEnable) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("requestCode",time.getId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),time.getId().intValue(),intent,0);
        AlarmManager alarm =(AlarmManager) getSystemService(ALARM_SERVICE);
        Integer hour = Integer.parseInt(time.getHour());
        Integer minute = Integer.parseInt(time.getMinute());

        if(isEnable == false){
            alarm.cancel(pendingIntent);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        if (time.getIndicator() == Time.Indicator.ONCE){
                intent.putExtra("once","once");
                pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),time.getId().intValue(),intent,0);
                alarm.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }
        else if(time.getIndicator() == Time.Indicator.EVERYDAY){
            alarm.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
        }
        else if(time.getIndicator() == Time.Indicator.DAY){

                EnumMap<Time.Days,Boolean> selectedDay = time.getPickedDay();
                Time.Days days[] = Time.Days.values();
            for(int i=0 ; i< days.length;++i)
            {
                if((selectedDay.get(days[i]) == true )){
                    scheduleForDay(i+1,alarm,calendar,pendingIntent);
                }
            }


        }

    }

    void scheduleForDay(int dof,AlarmManager alarm,Calendar calendar,PendingIntent intent){
            calendar.set(Calendar.DAY_OF_WEEK,dof);
            alarm.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EDIT_TIMER && resultCode == RESULT_OK){
            Time time =(Time) data.getSerializableExtra("time");
            time.update();
            List<Time> times = Time.listAll(Time.class);
            timeAdapter.setTimes(times);
            timeAdapter.notifyDataSetChanged();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
