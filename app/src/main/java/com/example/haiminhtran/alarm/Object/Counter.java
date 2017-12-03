package com.example.haiminhtran.alarm.Object;

import com.orm.SugarRecord;


public class Counter  extends SugarRecord{
    int hour,minute,second;


    public Counter() {
        hour=0;
        minute=0;
        second=0;
    }

    public Counter(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
