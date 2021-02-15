package com.fastcoo.fastcoopickerapp.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.fastcoo.fastcoopickerapp.Activity.Batch_Pickup;
import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.R;

import java.util.ArrayList;



public class Batch_adaptor extends RecyclerView.Adapter<Batch_adaptor.MyViewHolder>  {

    ArrayList<SinglePickup_Result> results;
    Context context;
    String value;
    int position;
    Batch_Pickup batch_pickup;


    @Override
    public Batch_adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_singlepickup,parent,false);
        return new MyViewHolder(view);
    }

    public Batch_adaptor(ArrayList<SinglePickup_Result> results, Context context, Batch_Pickup batch_pickup) {
        this.results = results;
        this.context = context;
        this.batch_pickup = batch_pickup ;

    }
    @Override
    public void onBindViewHolder(final Batch_adaptor.MyViewHolder holder, final int position) {

        final SinglePickup_Result item  = results.get(position);
        this.position= position;

//        holder.ean_no.setText("Ean No: " +item.getEanNo());
        holder.slip_number.setText("Sku: " +item.getSku());
        holder.piece.setText("Total: " +item.getPiece());
        holder.scanned.setText("Scanned: " +item.getUpdate());
        holder.shelve.setText("Shelve: " +item.getShelveNo());
        holder.sumtotal();

//        holder.layoutMain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                batch_pickup.ListClick(item.getEanNo().toString());
//            }
//        });

    }

    public int retrunPosition(){
        return position;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView slip_number,piece,ean_no,scanned,shelve;
        ConstraintLayout layoutMain;
        public MyViewHolder( View itemView) {
            super(itemView);
            slip_number= itemView.findViewById(R.id.tv_sku);
            piece= itemView.findViewById(R.id.tv_piece);
            ean_no= itemView.findViewById(R.id.tv_ean_no);
            scanned= itemView.findViewById(R.id.tv_scaned);
            layoutMain= itemView.findViewById(R.id.layoutMain);
            shelve= itemView.findViewById(R.id.tv_shelve);

        }
        public void sumtotal(){

            double total_update = 0;
            for(SinglePickup_Result singlePickup_result : results){

                total_update += Double.valueOf(singlePickup_result.getUpdate());

            }

            ((UpdateCount)context).getCount(total_update);
        }

    }
//    public void filterList(ArrayList<SinglePickup_Result> filterdNames) {
//        this.results = filterdNames;
//        notifyDataSetChanged();
//    }
}

