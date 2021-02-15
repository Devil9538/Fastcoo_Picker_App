package com.fastcoo.fastcoopickerapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fastcoo.fastcoopickerapp.Adaptor.ShipmentList_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_list;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShipmentList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    String user_id="",pickup_id;
    SharedPreferences single_pref;
    SharedPreferences.Editor single_editor;
    RecyclerView recyclerView;
    ShipmentList_Adaptor adaptor;
    Toolbar toolbar;
    TextView nodata;
    private static ProgressDialog mProgressDialog;
    ArrayList<SinglePickup_Result> result1;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipmnetlist__pickup);
        single_pref= getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        single_editor= single_pref.edit();
        user_id= single_pref.getString("user_id",null);
        pickup_id=getIntent().getStringExtra("pickup_id");
        single_editor.putString("pickup_id",pickup_id);
        single_editor.commit();
        recyclerView= findViewById(R.id.recycler_shipment_list);
        toolbar=findViewById(R.id.toolbar_shipmentlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        System.out.println("data1"  +pickup_id);
        nodata= findViewById(R.id.tv_no_data_shipmentlist);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) ShipmentList.this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        getSinglePickup_data();
    }

    public void getSinglePickup_data(){

        mProgressDialog = ProgressDialog.show(this, "Shipment List", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<SinglePickup_list> call= data.single_pickup(user_id,pickup_id);
        call.enqueue(new Callback<SinglePickup_list>() {
            @Override
            public void onResponse(Call<SinglePickup_list> call, Response<SinglePickup_list> response) {

                response.body();
                SinglePickup_Result result= new SinglePickup_Result();
                if (response.body().getStatus().equals(200)){
                    for(int i=0;i<=response.body().getResult().size()-1;i++){
                        String slip_no= response.body().getResult().get(i).getSlipNo();
                        String booking_id= response.body().getResult().get(i).getBookingD();




                        result.setSlipNo(slip_no);
                        result.setBookingD(booking_id);
                        result1= (ArrayList<SinglePickup_Result>) response.body().getResult();
                        adaptor= new ShipmentList_Adaptor((ArrayList<SinglePickup_Result>) response.body().getResult(), ShipmentList.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(ShipmentList.this,1, LinearLayoutManager.VERTICAL,false));
                        recyclerView.setAdapter(adaptor);
                        adaptor.notifyDataSetChanged();
                        mProgressDialog.dismiss();


                    }
                }else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    nodata.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SinglePickup_list> call, Throwable t) {
                Toast.makeText(ShipmentList.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent goback= new Intent(ShipmentList.this, PickupList.class);
        startActivity(goback);
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

    @Override
    public void onRefresh() {
        adaptor = new ShipmentList_Adaptor(result1,this);
        getSinglePickup_data();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}