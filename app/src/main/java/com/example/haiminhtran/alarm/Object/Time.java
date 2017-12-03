package com.example.haiminhtran.alarm.Object;

import android.net.Uri;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;

public class Time  extends SugarRecord implements Serializable {



    String hour="0";
    String minute="0";
    Indicator indicator;
   public boolean enable=false;
    Uri music=null;
    EnumMap<Days,Boolean> pickedDay;
     public  enum Indicator{
        ONCE,EVERYDAY,DAY;


         @Override
         public String toString() {
             String s = null;
             if(this == ONCE)
                 s = "Một lần";
             if(this == EVERYDAY)
                 s = "Hằng ngày";
             if(this == DAY)
                 s = "Chọn ngày";

             return s;
         }
     };

    public Time() {
        super();
        indicator = Indicator.ONCE;

        pickedDay = new EnumMap<Days, Boolean>(Days.class);
        pickedDay.put(Days.SUNDAY,false);
        pickedDay.put(Days.MONDAY,false);
        pickedDay.put(Days.TUESDAY,false);
        pickedDay.put(Days.WEDNESDAY,false);
        pickedDay.put(Days.THURSDAY,false);
        pickedDay.put(Days.FRIDAY,false);
        pickedDay.put(Days.SATURDAY,false);

    }

    public  enum Days{
        SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY;

    }


    public Time(String hour, String minute, Indicator indicator, boolean enable) {
        super();
        this.hour = hour;
        this.minute = minute;
        this.indicator = indicator;
        this.enable = enable;

    }


    public void enableDay(Days day,boolean b){
        pickedDay.put(day,b);
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Uri getMusic() {
        return music;
    }

    public void setMusic(Uri music) {
        this.music = music;
    }




    public EnumMap<Days, Boolean> getPickedDay() {
        return pickedDay;
    }
}
