package com.fastcoo.fastcoopickerapp.Adaptor;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fastcoo.fastcoopickerapp.Activity.AutoGeneratedPickup;
import com.fastcoo.fastcoopickerapp.Activity.AutoPickupList;
import com.fastcoo.fastcoopickerapp.Activity.DetailActivity;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Model.Autogenerated_Main;
import com.fastcoo.fastcoopickerapp.Model.Picklist_Detail;
import com.fastcoo.fastcoopickerapp.Model.SendTotes;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AutoPickup_Adaptor extends RecyclerView.Adapter<AutoPickup_Adaptor.MyViewHolder> {
    List<List<Picklist_Detail>> detail;
    Activity context;
    View view;
    String pickupid,slip_id,book,sendList,new_id;
    ArrayList<String> slip_array;
    ArrayList<String> send_slip_array;
    String totes;
    List<String>list_totes= new ArrayList<>();
    List<String> slip_list= new ArrayList<>();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String user_id;
    List<String> final_slip= new ArrayList<>();


    public AutoPickup_Adaptor(List<List<Picklist_Detail>> detail, Activity context,String totes) {
        this.detail = detail;
        this.context = context;
        this.totes= totes;
    }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_autopicklist_adaptor,parent,false);
        preferences= context.getSharedPreferences("MyPref",MODE_PRIVATE);
        editor= preferences.edit();
        user_id= preferences.getString("user_id",null);
        return new AutoPickup_Adaptor.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {

            slip_array= new ArrayList<>();
            send_slip_array= new ArrayList<>();

            for (int i=0;i<detail.size();i++){
            List<Picklist_Detail> new_list= detail.get(position);
            for(int j=0;j<new_list.size();j++){

                slip_id=new_list.get(j).getSlipNo();
                book=new_list.get(j).getBookingId();
                pickupid=new_list.get(j).getPickupId();
                slip_array.add(slip_id);
                slip_array.trimToSize();

                StringBuilder builder = new StringBuilder();
                for (String details : slip_array) {
                    builder.append(details + "\n");
                }
                holder.slip.setText(builder.toString());

            }
            slip_array.clear();
        }


        holder.pickup_id.setText(pickupid);
        holder.booking_id.setText(book);

        if (list_totes.size()==Integer.parseInt(totes)){
            holder.btn_detail.setVisibility(View.VISIBLE);
        }else if(!(list_totes.size() ==Integer.parseInt(totes))) {
            holder.btn_detail.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "First Scan Totes To Proceed Further", Toast.LENGTH_SHORT).show();
                }
            });
        }


        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_totes.size()==Integer.parseInt(totes)){
                    for(int i=0;i<detail.size();i++){
                        List<Picklist_Detail> new_list= detail.get(position);
                        for (int j=0;j<new_list.size();j++){

                            sendList= new_list.get(j).getSlipNo();
                            send_slip_array.add("\""+sendList+"\"");
                            send_slip_array.trimToSize();
                            Log.d("sliplist",send_slip_array.toString());
                            Intent goto_detail= new Intent(context, DetailActivity.class);
                            goto_detail.putExtra("pickupIid",new_list.get(j).getPickupId());
                            goto_detail.putExtra("new_id",new_list.get(j).getNewId());
                            goto_detail.putExtra("totes",totes);
                            goto_detail.putStringArrayListExtra("list",send_slip_array);
                            Retrofit_Data api_data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
                            Call<SendTotes> send= api_data.sendTotesInfo(user_id,new_list.get(j).getPickupId(),send_slip_array.toString(),list_totes.toString(),new_list.get(j).getNewId());
                            send.enqueue(new Callback<SendTotes>() {
                                @Override
                                public void onResponse(Call<SendTotes> call, Response<SendTotes> response) {
                                    response.body();
                                    if(response.body().getStatus()==200){
                                        Toast.makeText(context, "Totes Assigned ", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<SendTotes> call, Throwable t) {
                                    Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            context.startActivity(goto_detail);
                            context.finish();

                        }


                        send_slip_array.clear();

                    }


                }else {
                    Intent goback= new Intent(context, AutoGeneratedPickup.class);
                    context.startActivity(goback);
                    context.finish();
                    Toast.makeText(context, "Scanned Totes not equal to mentioned totes earlier", Toast.LENGTH_LONG).show();
                }


            }
        });



    }

    @Override
    public int getItemCount() {

        return detail.size();
    }

    public void getScannedlist(List<String> list){
        list_totes=list;
        Log.d("scanlist",list_totes.toString());

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView pickup_id,booking_id,slip;
        ConstraintLayout layoutMain;
        Button btn_detail;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            pickup_id= itemView.findViewById(R.id.tv_pickupid_auto);
            booking_id= itemView.findViewById(R.id.tv_booking_auto);
            slip= itemView.findViewById(R.id.tv_slip_auto);
            layoutMain= itemView.findViewById(R.id.layoutMain);
            btn_detail= itemView.findViewById(R.id.btn_autodetail);

        }



    }
}
