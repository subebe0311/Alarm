package com.example.haiminhtran.alarm.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haiminhtran.alarm.Object.Counter;
import com.example.haiminhtran.alarm.R;

import java.util.List;

public class CounterAdapter  extends RecyclerView.Adapter<CounterAdapter.ViewHolder>{

    List<Counter> counters;
    CounterListener listener;

    public void setListener(CounterListener listener) {
        this.listener = listener;
    }

    public interface CounterListener{
        void clickCounter(Counter counter);
    }

    @Override
    public int getItemCount() {
        return counters.size();
    }

    public void setCounters(List<Counter> counters) {
        this.counters = counters;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            final Counter counter = counters.get(position);
        int h = counter.getHour();
        int m = counter.getMinute();
        int s = counter.getSecond();
        StringBuilder stringBuilder = new StringBuilder();
        if(h >0 )
            stringBuilder.append(h + " giờ ");
        if(m >0 )
            stringBuilder.append(m + " phút ");
        if(s > 0)
            stringBuilder.append(s + " giây");
        holder.counter.setText(stringBuilder.toString());
        holder.counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clickCounter(counter);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counter,parent,false);


        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView counter;
        ImageView narrow;
        public ViewHolder(View view) {
            super(view);
            counter = view.findViewById(R.id.text_counter);
            narrow = view.findViewById(R.id.img_narrow);
        }
    }
}
