package com.example.haiminhtran.alarm.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haiminhtran.alarm.Object.Clocking;
import com.example.haiminhtran.alarm.R;

import java.util.ArrayList;
import java.util.List;

public class ClockingAdapter extends  RecyclerView.Adapter<ClockingAdapter.ViewHolder>{


    List<Clocking> clockings= null;

    public void setClockings(List<Clocking> clockings) {
        this.clockings = clockings;
    }

    public List<Clocking> getClockings() {
        return clockings;
    }

    @Override
    public int getItemCount() {
        return clockings.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Clocking clocking = clockings.get(position);
        holder.order.setText(String.valueOf(clocking.getStt()));
        holder.clocking.setText(String.valueOf(clocking.getMinute())+":"+String.valueOf(clocking.getSecond()+"."+String.valueOf(clocking.getMilSecond())));
        holder.interval.setText("+"+String.valueOf(clocking.getmInterval()+":"+String.valueOf(clocking.getSecondInterval()+"."+String.valueOf(clocking.getMilSecondInterval()))));



    }

    public void add(Clocking clocking){
        if(clockings == null)
            clockings = new ArrayList<>();
        clockings.add(0,clocking);
        notifyItemInserted(0);
    }

    public void clear(){
        clockings.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clocking,parent,false);


        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView order,clocking,interval;
        public ViewHolder(View view) {
            super(view);
            order = view.findViewById(R.id.text_order);
            clocking = view.findViewById(R.id.text_clocking_display);
            interval = view.findViewById(R.id.text_compare);
        }
    }
}
