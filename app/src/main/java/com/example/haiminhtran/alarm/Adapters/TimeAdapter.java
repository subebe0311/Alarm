package com.example.haiminhtran.alarm.Adapters;


import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.haiminhtran.alarm.R;
import com.example.haiminhtran.alarm.Object.Time;

import java.util.List;


public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>{

    List<Time> times;
    Context context;



    SendAlarmListener sendAlarmManager;
    EditAlarmListener editAlarmListener;

   public interface  SendAlarmListener{
        void send(Time time,boolean isEnable);
    }
    public interface  EditAlarmListener{
        void edit(Time time);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSendAlarmManager(SendAlarmListener sendAlarmManager) {
        this.sendAlarmManager = sendAlarmManager;
    }

    public void setEditAlarmListener(EditAlarmListener editAlarmListener) {
        this.editAlarmListener = editAlarmListener;
    }

    public void addItem(Time i){
        if(times == null){
            throw  new NullPointerException();
        }
        else {
            times.add(i);
        }
    }

    public void setTimes(List<Time> times) {
        this.times = times;
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
            final Time time = times.get(position);
            final String minutes = Integer.parseInt(time.getMinute())<=9?"0"+time.getMinute():time.getMinute();
            holder.time.setText(time.getHour()+":"+minutes);
            holder.enable.setText(time.isEnable()?"Bật":"Tắt");
            holder.indicator.setText(time.getIndicator()== Time.Indicator.ONCE?"Một lần":
                        time.getIndicator()== Time.Indicator.EVERYDAY?"Hàng Ngày":"Theo ngày");
            holder.btn.setChecked(time.isEnable());

            holder.btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    holder.enable.setText(b?"Bật":"Tắt");
                    time.setEnable(b);
                    time.save();
                    sendAlarmManager.send(time,b);
                }
            });

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(context,view);
                    MenuInflater inflater = popupMenu.getMenuInflater();
                    inflater.inflate(R.menu.menu_timer,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.delete_timer:
                                    Time.delete(time);
                                    times.remove(time);
                                    notifyItemRemoved(position);
                                    return true;

                                case R.id.edit_timer:
                                    editAlarmListener.edit(time);



                                    return true;
                                default:
                                    return false;
                            }



                        }
                    });
                    popupMenu.show();
                }
            });

    }





    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm,parent,false);
        return new ViewHolder(view);
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView time,indicator,enable;
        SwitchCompat btn;
        LinearLayout edit;

        public ViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.text_time);
            indicator = view.findViewById(R.id.text_indicator);
            enable = view.findViewById(R.id.text_enable);
            btn = view.findViewById(R.id.btn_enable);
            edit = view.findViewById(R.id.edit_timer);
        }


    }
}
