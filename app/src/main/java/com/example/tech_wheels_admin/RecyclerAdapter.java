package com.example.tech_wheels_admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<myhistory> myhistories;
    private Context context;

    public RecyclerAdapter(List<myhistory> myhistories, Context context) {
        this.myhistories = myhistories;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.textview_layout,viewGroup,false);
        Log.d("inrecadapter","inflated");

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        myhistory myhistory=myhistories.get(i);
        viewHolder.timeslot.setText(myhistory.getTimeslot());
        viewHolder.email.setText(myhistory.getEmail());
        viewHolder.date.setText(myhistory.getDate());
        viewHolder.vehiclemodel.setText(myhistory.getVehicleModel());
        viewHolder.timestamp.setText(myhistory.getTimestamp());
        viewHolder.regno.setText(myhistory.getRegno());
        viewHolder.status.setText(myhistory.getStatus());


    }

    @Override
    public int getItemCount() {
        return myhistories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView timeslot;
        public TextView email;
        public TextView date;
        public TextView regno;
        public TextView vehiclemodel;
        public TextView timestamp;
        public TextView status;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            timeslot=(TextView)itemView.findViewById(R.id.timeslot);
            email=(TextView)itemView.findViewById(R.id.email);
            date=(TextView)itemView.findViewById(R.id.date);
            regno=(TextView)itemView.findViewById(R.id.regno);
            vehiclemodel=(TextView)itemView.findViewById(R.id.vehiclemodel);
            timestamp=(TextView)itemView.findViewById(R.id.timestamp);
            status=(TextView)itemView.findViewById(R.id.status);

        }


    }



}

