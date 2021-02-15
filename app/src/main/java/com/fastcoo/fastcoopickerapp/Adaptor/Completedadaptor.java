package com.fastcoo.fastcoopickerapp.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.fastcoo.fastcoopickerapp.Model.Result;
import com.fastcoo.fastcoopickerapp.R;

import java.util.ArrayList;


public class Completedadaptor extends RecyclerView.Adapter<Completedadaptor.MyViewHolder> {
    ArrayList<Result> arrayList;
    Context context;

    public Completedadaptor(ArrayList<Result> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }



    @Override
    public Completedadaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_completeapadtor,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Completedadaptor.MyViewHolder holder, int position) {
        holder.booking_id.setText("Booking_Id: " +arrayList.get(position).getBookingId());
        holder.sku.setText("Sku: " +arrayList.get(position).getSku().get(0).getSku());
        holder.slip.setText("SlipNo: " +arrayList.get(position).getSlipNo().trim());
        holder.status.setText("Status: " +arrayList.get(position).getPickedStatus());
//        holder.ean.setText("Ean: " +arrayList.get(position).getSku().get(0).getEanNo());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView slip,sku,booking_id,status,ean;

        public MyViewHolder( View itemView) {
            super(itemView);

            sku= itemView.findViewById(R.id.tv_sku);
            slip= itemView.findViewById(R.id.tv_slip_no);
            booking_id= itemView.findViewById(R.id.tv_bookid);
            status= itemView.findViewById(R.id.tv_status);
            ean= itemView.findViewById(R.id.tv_ean);
        }
    }
}
