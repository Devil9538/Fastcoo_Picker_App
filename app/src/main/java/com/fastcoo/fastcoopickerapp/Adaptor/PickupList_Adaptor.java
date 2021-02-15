package com.fastcoo.fastcoopickerapp.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fastcoo.fastcoopickerapp.Activity.Batch_Pickup;
import com.fastcoo.fastcoopickerapp.Activity.ShipmentList;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.R;


import java.util.ArrayList;



public class PickupList_Adaptor extends RecyclerView.Adapter<PickupList_Adaptor.MyViewHolder> {

    private ArrayList<SinglePickup_Result> singlePickup_results;
    Activity context;

    @Override
    public PickupList_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pickuplist_adaptor,parent,false);
        return new MyViewHolder(view);
    }

    public PickupList_Adaptor(ArrayList<SinglePickup_Result> singlePickup_results, Activity context) {
        this.singlePickup_results = singlePickup_results;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(PickupList_Adaptor.MyViewHolder holder, final int position) {
        holder.pickup_id.setText("Pickup Id:- " +singlePickup_results.get(position).getPickupId());
        holder.total.setText("Total:- " +singlePickup_results.get(position).getTotal());
        holder.total_pending.setText("Pending:- " +singlePickup_results.get(position).getTotalPending());

        holder.single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_single= new Intent(context, ShipmentList.class);
                go_single.putExtra("pickup_id", singlePickup_results.get(position).getPickupId());
                System.out.println("data"  +singlePickup_results.get(position).getPickupId());
                context.startActivity(go_single);



            }
        });

        holder.batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_single= new Intent(context, Batch_Pickup.class);
                go_single.putExtra("pickup_id", singlePickup_results.get(position).getPickupId());
                System.out.println("data"  +singlePickup_results.get(position).getPickupId());
                context.startActivity(go_single);
            }
        });


    }

    @Override
    public int getItemCount() {
        return singlePickup_results.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pickup_id,total,total_pending;
        Button single,batch;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pickup_id= itemView.findViewById(R.id.tv_pickup_id);
            total= itemView.findViewById(R.id.tv_total);
            total_pending= itemView.findViewById(R.id.tv_total_pending);
            single= itemView.findViewById(R.id.btn_single);
            batch= itemView.findViewById(R.id.btn_batch);





        }
    }
}
