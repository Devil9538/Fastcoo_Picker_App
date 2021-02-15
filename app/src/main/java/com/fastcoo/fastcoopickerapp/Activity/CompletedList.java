package com.fastcoo.fastcoopickerapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import com.fastcoo.fastcoopickerapp.R;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fastcoo.fastcoopickerapp.Adaptor.Completedadaptor;
import com.fastcoo.fastcoopickerapp.Adaptor.PickupList_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Model.ModelPojo;
import com.fastcoo.fastcoopickerapp.Model.Result;
import com.fastcoo.fastcoopickerapp.Model.Sku;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompletedList extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    SharedPreferences complete_preference;
    SharedPreferences.Editor complete_editor;
    String user_id="";
    Completedadaptor completedadaptor;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<Result> results;
    List<Sku> sku_result;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private static ProgressDialog mProgressDialog;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_list);
        complete_preference =this.getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        complete_editor= complete_preference.edit();
        user_id= complete_preference.getString("user_id",null);
        recyclerView= findViewById(R.id.recycler_completed);
        toolbar= findViewById(R.id.toolbar_completed);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) CompletedList.this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        no_data= findViewById(R.id.tv_completed_no_data);
        getCompletedList();
    }

    void getCompletedList(){

        mProgressDialog = ProgressDialog.show(this, "List", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<ModelPojo> call= data.complete(user_id);
        call.enqueue(new Callback<ModelPojo>() {
            @Override
            public void onResponse(Call<ModelPojo> call, Response<ModelPojo> response) {
                Result result= new Result();



                if (response.body().getStatus().equals(200)) {
                    for(int i=0;i<=response.body().getResult().size()-1;i++){

                        String slip= response.body().getResult().get(i).getSlipNo();
                        String status= response.body().getResult().get(i).getPickedStatus();
                        String booking= response.body().getResult().get(i).getBookingId();
                        sku_result = response.body().getResult().get(i).getSku();
                        result.setSku(sku_result);
                        System.out.println("sku value: "+String.valueOf(sku_result.get(0).getSku()));
                        result.setBookingId(booking);
                        result.setPickedStatus(status);
                        result.setSlipNo(slip);

                        results= (ArrayList<Result>) response.body().getResult();
                        completedadaptor = new Completedadaptor((ArrayList<Result>) response.body().getResult(),CompletedList.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(CompletedList.this,1, LinearLayoutManager.VERTICAL,false));
                        recyclerView.setAdapter(completedadaptor);
                        completedadaptor.notifyDataSetChanged();
                        mProgressDialog.dismiss();
                    }


                }else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    no_data.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ModelPojo> call, Throwable t) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

    @Override
    public void onRefresh() {
        completedadaptor = new Completedadaptor(results,this);
        getCompletedList();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}