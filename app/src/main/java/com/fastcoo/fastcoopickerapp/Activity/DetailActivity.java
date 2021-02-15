package com.fastcoo.fastcoopickerapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fastcoo.fastcoopickerapp.Adaptor.Batch_adaptor;
import com.fastcoo.fastcoopickerapp.Adaptor.Detail_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.Sku_Detail;
import com.fastcoo.fastcoopickerapp.Model.Sku_Main;
import com.fastcoo.fastcoopickerapp.Model.SlipDetail;
import com.fastcoo.fastcoopickerapp.Model.Update;
import com.fastcoo.fastcoopickerapp.Model.UpdateAutoPickup;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements UpdateCount {

        String pickup_id,totes_value,user_id,new_id;
        ArrayList<String>getList;
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        ArrayList<Sku_Detail> sku_details;
        RecyclerView recyclerView;
        Detail_Adaptor adaptor;
        double update_count = 0;
        int count = 0;
        String temp = "";
        EditText edt_value;
        private Context context = null;
        ImageView enter;
        Button send_Update;
        private static ProgressDialog mProgressDialog;
        Toolbar toolbar;
        int slip_count=0;

        @Override
         protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pref =this.getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor= pref.edit();
        recyclerView= findViewById(R.id.recycler_details);
        user_id= pref.getString("user_id",null);
        pickup_id= getIntent().getStringExtra("pickupIid");
        new_id= getIntent().getStringExtra("new_id");
        Log.d("detailpickup",pickup_id);
        getList= new ArrayList<>();
        getList= getIntent().getStringArrayListExtra("list");
        totes_value= getIntent().getStringExtra("totes");
        edt_value= findViewById(R.id.edt_value_detail);
        context=this;
        enter= findViewById(R.id.img_enter_detail);
        send_Update= findViewById(R.id.btn_update_detail);
        toolbar= findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d("received_item",getList.toString());

        getSkuDetails();

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.equals(edt_value.getText().toString())) {

                } else {
                    count = 0;
                    Toast.makeText(context, "Sku Not Exist", Toast.LENGTH_SHORT).show();
                }
//                    Toast.makeText(context, "different value", Toast.LENGTH_SHORT).show();


                check();
                edt_value.setText("");
            }
        });


    }

    public void getSkuDetails(){

        mProgressDialog = ProgressDialog.show(this, "PickList", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<Sku_Main> call= data.getSkuList(user_id,pickup_id,totes_value,getList.toString());
        call.enqueue(new Callback<Sku_Main>() {
            @Override
            public void onResponse(Call<Sku_Main> call, Response<Sku_Main> response) {
                response.body();
                sku_details= new ArrayList<>();
                ArrayList<String> slip_no= new ArrayList<>();

                if(response.body().getStatus().equals(200)){

                  for(int i=0;i<response.body().getResult().size();i++){

                        String pickup_id= response.body().getResult().get(i).getPickupId();
                        String booking_id= response.body().getResult().get(i).getBookingId();
                        String sku= response.body().getResult().get(i).getSku();
                        String new_id= response.body().getResult().get(i).getNewId();
                        response.body().getResult().get(i).setNewId(new_id);
                        int piece = response.body().getResult().get(i).getPiece();
                      response.body().getResult().get(i).setPickupId(pickup_id);
                      response.body().getResult().get(i).setBookingId(booking_id);
                      response.body().getResult().get(i).setSku(sku);
                      response.body().getResult().get(i).setPiece(piece);
                      List<SlipDetail> slipList= response.body().getResult().get(i).getSlipDetails();

                      for(int j=0;j<slipList.size();j++){

                          String slip= slipList.get(j).getSlipNo();
                          slip_no.add(slip);

                      }

                      sku_details= (ArrayList<Sku_Detail>) response.body().getResult();
                      adaptor= new Detail_Adaptor(sku_details,DetailActivity.this);
                      recyclerView.setLayoutManager(new GridLayoutManager(DetailActivity.this, 1, LinearLayoutManager.VERTICAL,false));
                      recyclerView.setAdapter(adaptor);
                      adaptor.notifyDataSetChanged();
                      mProgressDialog.dismiss();
                  }

                }
            }

            @Override
            public void onFailure(Call<Sku_Main> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }
        });

    }

    public void sumtotal() {
        double total = 0;

        for (Sku_Detail singlePickup_result : sku_details) {

            total += Double.valueOf(singlePickup_result.getPiece());

        }
        update_count = total;


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this,AutoPickupList.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pickup_id",pickup_id);
        intent.putExtra("totes_value",totes_value);
        startActivity(intent);
        DetailActivity.this.finish();
    }

    public void getUpdateStatus(){
        send_Update.setVisibility(View.VISIBLE);
        send_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setTitle("Status")
                        .setCancelable(false)
                        .setMessage("Update Shipment Status")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit_Data data = Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
                                Call<UpdateAutoPickup> call = data.getUpdateStatus(user_id,new_id,pickup_id);
                                call.enqueue(new Callback<UpdateAutoPickup>() {
                                    @Override
                                    public void onResponse(Call<UpdateAutoPickup> call, Response<UpdateAutoPickup> response) {
                                        response.body();
                                        if(response.body().equals(200)) {
                                            Toast.makeText(DetailActivity.this, "Status Updated", Toast.LENGTH_SHORT).show();
                                        }
                                        Intent go_back = new Intent(DetailActivity.this, AutoPickupList.class);
                                        go_back.putExtra("pickup_id",pickup_id);
                                        go_back.putExtra("totes_value",totes_value);
                                        go_back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(go_back);
                                        send_Update.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<UpdateAutoPickup> call, Throwable t) {
                                        Toast.makeText(DetailActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                send_Update.setVisibility(View.GONE);
                                Toast.makeText(DetailActivity.this, "Update Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

    }

    public void check() {
        sumtotal();

        for (int j = 0; j < sku_details.size(); j++) {
            if (edt_value.getText().toString().trim().equals(sku_details.get(j).getSku().trim())) {
                List<SlipDetail> slip_detail= sku_details.get(j).getSlipDetails();
                for (int i=0;i<slip_detail.size();i++){
                    if(!slip_detail.get(i).getPieces().equals(String.valueOf(slip_detail.get(i).getScaned())) &&
                    slip_detail.get(i).getScanStatus().equals(false)){
                        slip_count= slip_count +1;
                        int count= slip_count;
                        LayoutInflater li = LayoutInflater.from(context);
                        View promptsView = li.inflate(R.layout.info, null);
                        TextView tell= promptsView.findViewById(R.id.tv_tell);
                        tell.setText("Put:  "+sku_details.get(j).getSku()+ "  " +"To:  "+
                                slip_detail.get(i).getTodsBarcode());
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(promptsView);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {


                                            }
                                        });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        Detail_Adaptor adapter = (Detail_Adaptor) recyclerView.getAdapter();
                        slip_detail.get(i).setScaned(slip_count);
                        adapter.notifyDataSetChanged();
                        if(slip_detail.get(i).getPieces().equals(String.valueOf(slip_detail.get(i).getScaned()))){
                            slip_count=0;
                            slip_detail.get(i).setScanStatus(true);
                            adaptor.notifyDataSetChanged();
                        }
                        break;

                    }
                }

                temp = edt_value.getText().toString();
                if (Integer.valueOf(sku_details.get(j).getPiece()) != count) {
                    count = count + 1;

                    Sku_Detail item = new Sku_Detail();
                    Toast.makeText(DetailActivity.this, "Item Scanned", Toast.LENGTH_SHORT).show();
                    Detail_Adaptor adapter = (Detail_Adaptor) recyclerView.getAdapter();
                    sku_details.get(j).setUpdate(count);
                    adapter.notifyDataSetChanged();

                    if (sku_details.get(j).getPiece()==count) {
                        sku_details.get(j).setUpdate(count);
                        sku_details.get(j).setIndicator("Picked");
                        sku_details.get(j).setSelected(true);
                        Collections.swap(sku_details,j,0);
                        adapter.notifyItemMoved(j,0);
                        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_CONFIRM,150);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context, "All Item scanned", Toast.LENGTH_SHORT).show();
                    }else if(!(sku_details.get(j).getPiece()==count)) {
                        ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                        toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
                        adapter.notifyDataSetChanged();
                    }


                } else {
                    Toast.makeText(context, "Already slot full", Toast.LENGTH_SHORT).show();

                }
            }

        }

    }




    @Override
    public void getCount(double count) {
        sumtotal();
        if (update_count==count){
            Toast.makeText(this, "All Package Scanned", Toast.LENGTH_SHORT).show();
            getUpdateStatus();
        }
    }

    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }
}