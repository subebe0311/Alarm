package com.example.haiminhtran.alarm.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.haiminhtran.alarm.Adapters.CounterAdapter;
import com.example.haiminhtran.alarm.AlarmActivity;
import com.example.haiminhtran.alarm.Object.Counter;
import com.example.haiminhtran.alarm.R;

public class CounterFragment extends Fragment implements CounterAdapter.CounterListener {

    CounterAdapter adapter;
    Handler handler;
    TextView textView;
    Runnable current=null;
    int h,m,s;
    Button btnStart;

    @Override
    public void clickCounter(Counter counter) {
        h = counter.getHour();
        m = counter.getMinute();
        s = counter.getSecond();
        textView.setText(String.valueOf(h)+":"+ String.valueOf(m)+":"+String.valueOf(s));

        if(current!=null)
        handler.removeCallbacks(current);
        current = new Runnable() {
            @Override
            public void run() {
                if(h==0 && m == 0 && s == 0)
                {
                    Intent intent = new Intent(getActivity(), AlarmActivity.class);
                    startActivity(intent);
                    return;
                }

                if(s >0) s--;
                else if(s==0){

                    if(m >0){
                        m--;
                        s=59;


                    }else if(m==0){
                        if(h>0){
                            h--;
                            m=59;
                            s=59;
                        }
                    }
                }




                textView.setText(String.valueOf(h)+":" + String.valueOf(m)+":"+String.valueOf(s));


                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(current,1000);
    }

    public CounterAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CounterAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_counter,container,false);
        handler = new Handler();
        adapter.setListener(this);
        btnStart = view.findViewById(R.id.btn_start_counter);
        btnStart.setEnabled(false);
        textView = view.findViewById(R.id.text_counter_display);
        RecyclerView recyclerView = view.findViewById(R.id.counter_list_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
