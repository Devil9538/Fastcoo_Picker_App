package com.fastcoo.fastcoopickerapp.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.R;

import java.util.ArrayList;


public class SinglePickup_Adaptor extends RecyclerView.Adapter<SinglePickup_Adaptor.MyViewHolder>  {

    ArrayList<SinglePickup_Result> results;
    Context context;
    String value;
    int position;


    @Override
    public SinglePickup_Adaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_singlepickup,parent,false);
        return new MyViewHolder(view);
    }

    public SinglePickup_Adaptor(ArrayList<SinglePickup_Result> results, Context context) {
        this.results = results;
        this.context = context;



    }


    @Override
    public void onBindViewHolder(final SinglePickup_Adaptor.MyViewHolder holder, final int position) {

       final SinglePickup_Result item  = results.get(position);
        this.position= position;

//        holder.ean_no.setText("Ean No: " +item.getEanNo());
        holder.slip_number.setText("Sku: " +item.getSlipNo());
        holder.piece.setText("Total: " +item.getPiece());
        holder.scanned.setText("Scanned: " +item.getUpdate());
        holder.shelve.setText("Shelve: " +item.getShelveNo());
        holder.sumtotal();

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
        public MyViewHolder( View itemView) {
            super(itemView);
            slip_number= itemView.findViewById(R.id.tv_sku);
            piece= itemView.findViewById(R.id.tv_piece);
            ean_no= itemView.findViewById(R.id.tv_ean_no);
            scanned= itemView.findViewById(R.id.tv_scaned);
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
}
