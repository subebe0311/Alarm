package com.example.haiminhtran.alarm.Object;

import com.orm.SugarRecord;
import com.orm.dsl.Table;

@Table
public class Clocking extends SugarRecord {
    int stt;;
    int minute;
    int milSecond;
    int second;

    int mInterval;
    int milSecondInterval;
    int secondInterval;

    public Clocking() {
    }

    public Clocking(int stt, int minute, int second, int milSecond, int mInterval, int secondInterval, int milSecondInterval) {
        this.stt = stt;
        this.minute = minute;
        this.second = second;
        this.milSecond = milSecond;
        this.mInterval = mInterval;
        this.secondInterval = secondInterval;
        this.milSecondInterval = milSecondInterval;
    }

    public int getmInterval() {
        return mInterval;
    }

    public void setmInterval(int mInterval) {
        this.mInterval = mInterval;
    }

    public int getMilSecondInterval() {
        return milSecondInterval;
    }

    public void setMilSecondInterval(int milSecondInterval) {
        this.milSecondInterval = milSecondInterval;
    }

    public int getSecondInterval() {
        return secondInterval;
    }

    public void setSecondInterval(int secondInterval) {
        secondInterval = secondInterval;
    }

    public int getStt() {
        return stt;
    }

    public void setStt(int stt) {
        this.stt = stt;
    }


    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getMilSecond() {
        return milSecond;
    }

    public void setMilSecond(int milSecond) {
        this.milSecond = milSecond;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }
}
