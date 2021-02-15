package com.fastcoo.fastcoopickerapp.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.fastcoo.fastcoopickerapp.Activity.SinglePickup;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.R;

import java.util.ArrayList;



public class ShipmentList_Adaptor extends RecyclerView.Adapter<ShipmentList_Adaptor.MyViewHolder> {
    ArrayList<SinglePickup_Result> results;
    Activity context;

    public ShipmentList_Adaptor(ArrayList<SinglePickup_Result> results, Activity context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public ShipmentList_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_shipmentlist_adaptor,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShipmentList_Adaptor.MyViewHolder holder, final int position) {
        holder.slip_no.setText("Awb No: "+results.get(position).getSlipNo());
        holder.booking_id.setText("Reference No: "+results.get(position).getBookingD());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_singlepickup= new Intent(context, SinglePickup.class);
                go_singlepickup.putExtra("slip_no",results.get(position).getSlipNo());
                context.startActivity(go_singlepickup);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slip_no,booking_id;
        Button details;
        public MyViewHolder( View itemView) {
            super(itemView);
            slip_no= itemView.findViewById(R.id.tv_slip_no);
            booking_id= itemView.findViewById(R.id.tv_booking_id);
            details= itemView.findViewById(R.id.btn_detail);

        }
    }
}
