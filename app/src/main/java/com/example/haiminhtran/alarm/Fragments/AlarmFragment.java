package com.example.haiminhtran.alarm.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haiminhtran.alarm.AddAlarmActivity;
import com.example.haiminhtran.alarm.R;
import com.example.haiminhtran.alarm.Object.Time;
import com.example.haiminhtran.alarm.Adapters.TimeAdapter;

import java.io.Serializable;
import java.util.Date;

public class AlarmFragment extends Fragment {

    TimeAdapter adapter;
    public static final int ADDING_ALARM = 2;
    public void setAdapter(TimeAdapter adapter) {
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        final TextView timeSys = view.findViewById(R.id.text_time_system);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DateFormat format = new DateFormat();
                CharSequence c = format.format("hh:mm:ss",new Date());
                timeSys.setText(c);
                handler.postDelayed(this,1000);
            }
        },1000);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

        Button btnAdd = view.findViewById(R.id.btn_alarm_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddAlarmActivity.class);
                startActivityForResult(intent,ADDING_ALARM);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADDING_ALARM && resultCode == getActivity().RESULT_OK ){
            Serializable i = data.getSerializableExtra("time");
            Time time = (Time)i;
            time.save();
            adapter.addItem(time);
            adapter.notifyItemInserted(adapter.getItemCount()+1);
        }



    }
}
