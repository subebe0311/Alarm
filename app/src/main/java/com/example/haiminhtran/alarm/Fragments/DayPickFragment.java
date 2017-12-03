package com.example.haiminhtran.alarm.Fragments;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.haiminhtran.alarm.R;
import com.example.haiminhtran.alarm.Object.Time;

import java.util.EnumMap;

public class DayPickFragment extends DialogFragment {

    public static final int NUMBER_OF_DAYS = 7;

    DayPickerListener listener;

    public DayPickFragment() {
        // Required empty public constructor
    }

    public interface  DayPickerListener{
        void chooseDay(Time.Days day, boolean b);
    }


    EnumMap<Time.Days,Boolean> days=null;

    public void setListener(DayPickerListener listener) {
        this.listener = listener;
    }

    public void setDays(EnumMap<Time.Days, Boolean> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean []isChosen = null;
        final Time.Days []day = Time.Days.values();
        if(days == null)
        days = new EnumMap<Time.Days, Boolean>(Time.Days.class);
        else {
            isChosen = new boolean[NUMBER_OF_DAYS];

            for(int i=0; i<NUMBER_OF_DAYS; ++i)
                isChosen[i] = days.get(day[i]) ;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pickday);
        builder.setMultiChoiceItems(R.array.day, isChosen, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if(b){
                    days.put(day[i],true);
                }
                else if (days.get(day[i]) == true) {
                        days.put(day[i],false);}

                listener.chooseDay(day[i],b);
            }
        });

        builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
