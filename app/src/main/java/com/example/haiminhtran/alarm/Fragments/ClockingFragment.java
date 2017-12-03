package com.example.haiminhtran.alarm.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.haiminhtran.alarm.Adapters.ClockingAdapter;
import com.example.haiminhtran.alarm.Object.Clocking;
import com.example.haiminhtran.alarm.Object.Time;
import com.example.haiminhtran.alarm.R;


import java.util.List;


public class ClockingFragment extends Fragment{


    ClockingAdapter adapter;
    RecyclerView recyclerView;
    Button btnStart,btnPause;
    int second=0;
    int minute=0;
    int millSecond=0;
    Handler handler;
    TextView textView;
    Clocking current;
    int order=0;
    public void setAdapter(ClockingAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(container.getContext()).inflate(R.layout.fragment_clocking,container,false);
        handler = new Handler();
        recyclerView = view.findViewById(R.id.clocking_list_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnStart = view.findViewById(R.id.btn_start_clocking);
        btnPause  = view.findViewById(R.id.btn_pause_clocking);
        textView = view.findViewById(R.id.text_clocking);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getClockings() != null) {
                    Clocking.deleteAll(Clocking.class);
                    adapter.clear();
                }
                current = null;
                order = 0;
                millSecond =0;
                second=0;
                minute=0;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        millSecond++;
                        if(millSecond==10)
                        {
                            second++;
                            millSecond=0;
                        }
                        if(second==60)
                        {
                            minute++;
                            second=0;
                        }

                        textView.setText(String.valueOf(minute) + ":"+String.valueOf(second)+"."+String.valueOf(millSecond));

                        handler.postDelayed(this,100);
                    }
                },100);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ms = millSecond;
                int s = second;
                int m = minute;
                int mInterval=0;
                int sInterval = 0 ;
                int milSInterval = 0 ;
                if(current == null){
                    mInterval =0;
                    sInterval = 0;
                    milSInterval =0;

                }
                else {

                    if (ms >= current.getMilSecond()){
                        milSInterval = ms - current.getMilSecond();
                    }
                    else {
                        milSInterval = ms +1 - current.getMilSecond();
                    }

                    if(s >= current.getSecond()){
                        sInterval = s - current.getSecond();
                    }
                    else {
                        sInterval = s +1 - current.getSecond();
                    }

                    if(m >= current.getMinute()){
                        mInterval = m - current.getMinute();
                    }
                    else {
                        mInterval = m +1 -current.getMinute();
                    }

                }
                current = new Clocking(++order,m,s,ms,mInterval,sInterval,milSInterval);
                Clocking.save(current);
                adapter.add(current);
            }
        });


        return view;
    }
}
