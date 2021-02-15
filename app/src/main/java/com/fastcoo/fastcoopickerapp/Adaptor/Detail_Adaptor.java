package com.fastcoo.fastcoopickerapp.Adaptor;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fastcoo.fastcoopickerapp.Activity.ShipmentList;
import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.Sku_Detail;
import com.fastcoo.fastcoopickerapp.Model.SlipDetail;
import com.fastcoo.fastcoopickerapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Detail_Adaptor extends RecyclerView.Adapter<Detail_Adaptor.MyViewHolder> {

    View view;
    ArrayList<Sku_Detail> sku_details;
    Activity context;
    ArrayList<String> slip_detail;
    ArrayList<String > totes_value;
    int selectedPosition=-1;
    String piece1,scanned;
    private boolean onBind;

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_detail_adaptor,parent,false);

        return new Detail_Adaptor.MyViewHolder(view);
    }

    public Detail_Adaptor(ArrayList<Sku_Detail> sku_details, Activity context) {
        this.sku_details = sku_details;
        this.context = context;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {


        final Sku_Detail model = sku_details.get(position);
        slip_detail= new ArrayList<>();
        totes_value= new ArrayList<>();
        holder.sku.setText("Sku: "+sku_details.get(position).getSku());
        holder.piece.setText("Piece: "+sku_details.get(position).getPiece().toString());
        holder.booking.setText("Booking Id: "+sku_details.get(position).getBookingId());
        holder.pickup.setText("Pickup Id: "+sku_details.get(position).getPickupId());
        holder.count.setText(String.valueOf("Scanned: "+sku_details.get(position).getUpdate()));
        holder.shelve.setText("Shelve No: "+sku_details.get(position).getShelveNo());
        if(sku_details.get(position).getStockLocation()==null){
            holder.stock.setText("Stock Loc: "+ "N/A");
        }else {
            holder.stock.setText("Stock Loc: "+sku_details.get(position).getStockLocation());
        }

        Picasso.with(context)
                .load(sku_details.get(position).getSkuPath())
                .into(holder.detail_img);
        holder.sumtotal();
        for (int i=0;i<sku_details.size();i++){
            List<SlipDetail> getSlip=  sku_details.get(position).getSlipDetails();
            for (int j=0;j<getSlip.size();j++){
                String slip= getSlip.get(j).getSlipNo();
                slip_detail.add(slip);
                slip_detail.trimToSize();

                StringBuilder builder = new StringBuilder();
                for (String details : slip_detail) {
                    builder.append("Awb No:"+details +"\n");
                    holder.slip.setText(builder.toString().trim());
                    holder.slip.setTextColor(context.getResources().getColor(R.color.fmp_red));
                }

                Log.d("lets",totes_value.toString());

            }

            for(int k=0;k<getSlip.size();k++){
                String totes= getSlip.get(k).getTodsBarcode();
                totes_value.add(totes);
                totes_value.trimToSize();
                StringBuilder builder = new StringBuilder();
                for (String details : totes_value) {
                    builder.append("Box No:" +details + "\n");
                    holder.totes.setText(builder.toString());
                    holder.totes.setTextColor(context.getResources().getColor(R.color.black));
                }

            }
            slip_detail.clear();
            totes_value.clear();


        }
        if(model.isSelected()){
            holder.indicator.setText(sku_details.get(position).getIndicator());
            ((CardView)holder.cardView).setCardBackgroundColor(Color.parseColor("#75EE26"));

        }

    }

    @Override
    public int getItemCount() {
        return sku_details.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sku,piece,booking,slip,pickup,count,indicator,totes,shelve,stock;
        CardView cardView;
        ImageView detail_img;
        public MyViewHolder( View itemView) {
            super(itemView);
            sku= itemView.findViewById(R.id.tv_sku_detail);
            piece= itemView.findViewById(R.id.tv_piece_detail);
            booking= itemView.findViewById(R.id.tv_book_detail);
            slip= itemView.findViewById(R.id.tv_slip_detail);
            pickup = itemView.findViewById(R.id.tv_pick);
            count= itemView.findViewById(R.id.tv_count_detail);
            indicator= itemView.findViewById(R.id.tv_indicator);
            cardView= itemView.findViewById(R.id.post_card_view);
            detail_img= itemView.findViewById(R.id.img_product);
            totes= itemView.findViewById(R.id.tv_totes_list);
            shelve= itemView.findViewById(R.id.tv_shelve);
            stock= itemView.findViewById(R.id.tv_stock);

        }



        public void sumtotal(){

            double total_update = 0;
            for(Sku_Detail sku_detail : sku_details){

                total_update += Double.valueOf(sku_detail.getUpdate());

            }

            ((UpdateCount)context).getCount(total_update);
        }

    }

}
