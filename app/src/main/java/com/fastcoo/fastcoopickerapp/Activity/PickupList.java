package com.fastcoo.fastcoopickerapp.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fastcoo.fastcoopickerapp.Adaptor.PickupList_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_list;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickupList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SharedPreferences pickuplist_preference;
    SharedPreferences.Editor pickuplist_editor;
    String user_id="";
    String id;
    RecyclerView recyclerView;
    PickupList_Adaptor adaptor;
    ArrayList<SinglePickup_Result> result1;
    private static ProgressDialog mProgressDialog;
    TextView no_data;
    Toolbar toolbar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup_list);
        recyclerView= findViewById(R.id.recycler_pickup_list);
        pickuplist_preference =this.getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        pickuplist_editor= pickuplist_preference.edit();
        user_id= pickuplist_preference.getString("user_id",null);
        no_data= findViewById(R.id.tv_no_data);
        toolbar= findViewById(R.id.toolbar_pendingjob);
        setSupportActionBar(toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) PickupList.this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSinglePickup_List();
    }

    public void getSinglePickup_List(){

        mProgressDialog = ProgressDialog.show(this, "PickList Id", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<SinglePickup_list> call= data.pickup_list(user_id);
        call.enqueue(new Callback<SinglePickup_list>() {

            @Override
            public void onResponse(Call<SinglePickup_list> call, Response<SinglePickup_list> response) {


                    SinglePickup_Result result= new SinglePickup_Result();
                    if (response.body().getStatus().equals(200)){

                    for(int i=0;i<=response.body().getResult().size()-1;i++){
                    String pickup_id= response.body().getResult().get(i).getPickupId();
                    String total= response.body().getResult().get(i).getTotal();
                    String total_pending= response.body().getResult().get(i).getTotalPending();
                    result.setPickupId(pickup_id);
                    result.setTotal(total);
                    result.setTotalPending(total_pending);
                    result1=(ArrayList<SinglePickup_Result>) response.body().getResult();
                    adaptor= new PickupList_Adaptor((ArrayList<SinglePickup_Result>) response.body().getResult(), PickupList.this);
                    recyclerView.setLayoutManager(new GridLayoutManager(PickupList.this,1, LinearLayoutManager.VERTICAL,false));
                    recyclerView.setAdapter(adaptor);
                    adaptor.notifyDataSetChanged();
                    mProgressDialog.dismiss();


                }


                }else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    no_data.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                    
                    }
            }

            @Override
            public void onFailure(Call<SinglePickup_list> call, Throwable t) {
                Toast.makeText(PickupList.this,t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent goback= new Intent(PickupList.this, Dashboard.class);
        startActivity(goback);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

    @Override
    public void onRefresh() {
        adaptor = new PickupList_Adaptor(result1,this);
        getSinglePickup_List();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}