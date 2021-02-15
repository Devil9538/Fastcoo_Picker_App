package com.fastcoo.fastcoopickerapp.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.fastcoo.fastcoopickerapp.Adaptor.SinglePickup_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_list;
import com.fastcoo.fastcoopickerapp.Model.Sku;
import com.fastcoo.fastcoopickerapp.Model.Update;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePickup extends AppCompatActivity implements UpdateCount {

    String slip_no,picker_id="";
    SharedPreferences single_pref;
    SharedPreferences.Editor single_editor;
    RecyclerView recyclerView;
    public EditText value;
    private Context context = null;
    SinglePickup_Adaptor adaptor;
    ArrayList<SinglePickup_Result> result1;
    int count=0;
    String pickup_id;
    Toolbar toolbar;
    private static ProgressDialog mProgressDialog;
    TextView noData;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView enter;
    double update_count = 0;
    Button btn_update;
    String temp = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pickup);
        single_pref= getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        single_editor= single_pref.edit();
        picker_id= single_pref.getString("user_id",null);
        pickup_id= single_pref.getString("pickup_id",null);
        slip_no= getIntent().getStringExtra("slip_no");
        recyclerView= findViewById(R.id.recycler_single_list);
        value= findViewById(R.id.edt_value);
        enter=findViewById(R.id.img_enter);
        btn_update = findViewById(R.id.btn_update_single);
        context= this;
        getSinglepickup_data();
        result1= new ArrayList<>();
        toolbar= findViewById(R.id.toolbar_singlepickup);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noData= findViewById(R.id.tv_no_data_singlepickup);
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) SinglePickup.this);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_dark,
//                android.R.color.holo_orange_dark,
//                android.R.color.holo_blue_dark);

//        value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                switch (actionId) {
//
//                    case EditorInfo.IME_ACTION_GO:
//
//                        check();
//                        updateStatus();
//                        value.setText("");
//                        return true;
//
//
//                    default:
//                        return false;
//                }
//            }
//        });

    enter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            check();
////            updateStatus();
//            value.setText("");

            if (temp.equals(value.getText().toString())) {
            } else {
                count = 0;
            }
//                    Toast.makeText(context, "different value", Toast.LENGTH_SHORT).show();


            check();
            value.setText("");
        }
    });


    }

    public void getSinglepickup_data(){

        mProgressDialog = ProgressDialog.show(this, "Single PickupList", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<SinglePickup_list>call= data.singlepickup_data(picker_id,slip_no);
        call.enqueue(new Callback<SinglePickup_list>() {
            @Override
            public void onResponse(Call<SinglePickup_list> call, Response<SinglePickup_list> response) {

                SinglePickup_list result= new SinglePickup_list();
                if (response.body().getStatus().equals(200)){

                    for (int i=0;i<=response.body().getResult().size()-1;i++){
                        String slip= response.body().getResult().get(i).getSku();
//                        String eanno= response.body().getResult().get(i).getEanNo();
                        String piece_count= response.body().getResult().get(i).getPiece();
                        String cod_value= response.body().getResult().get(i).getCod();



                        response.body().getResult().get(i).setSlipNo(slip);
//                        result.setEanNo(eanno);
                        response.body().getResult().get(i).setCod(cod_value);
                        response.body().getResult().get(i).setPiece(piece_count);
//                        result.setCod(cod_value);
//                        result.setPiece(piece_count);
                        result1=(ArrayList<SinglePickup_Result>) response.body().getResult();
                        adaptor= new SinglePickup_Adaptor((ArrayList<SinglePickup_Result>) response.body().getResult(),context);
                        recyclerView.setLayoutManager(new GridLayoutManager(SinglePickup.this,1, LinearLayoutManager.VERTICAL,false));
                        recyclerView.setAdapter(adaptor);
                            adaptor.notifyDataSetChanged();
                        mProgressDialog.dismiss();

                    }
                }else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noData.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SinglePickup_list> call, Throwable t) {
                Toast.makeText(SinglePickup.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void sumtotal() {
        double total = 0;

        for (SinglePickup_Result singlePickup_result : result1) {

            total += Double.valueOf(singlePickup_result.getPiece());

        }
        update_count = total;


    }

    public void check(){


//                for (int j = 0; j<= result1.size()-1; j++) {
//                    if (value.getText().toString().equals(result1.get(j).getEanNo().trim())) {
//                        count++;
//                        SinglePickup_Result item = new SinglePickup_Result();
//                        System.out.println("doublecheck"+value.getText().toString().equals(result1.get(j).getEanNo()));
////                        Toast.makeText(SinglePickup.this, "checked", Toast.LENGTH_SHORT).show();
//                        SinglePickup_Adaptor adapter = (SinglePickup_Adaptor)recyclerView.getAdapter();
//                        result1.get(j).setUpdate(count);
//                        adapter.notifyDataSetChanged();
//
//                    }
//
//                }

        for (int j = 0; j < result1.size(); j++) {
            if (value.getText().toString().trim().equals(result1.get(j).getSku().trim())) {

                temp = value.getText().toString();
                if (Integer.valueOf(result1.get(j).getPiece()) != count) {
                    count = count + 1;

                    SinglePickup_Result item = new SinglePickup_Result();
                    System.out.println("doublecheck" + value.getText().toString().equals(result1.get(j).getSku()));
                    Toast.makeText(SinglePickup.this, "Item Scanned", Toast.LENGTH_SHORT).show();
                    SinglePickup_Adaptor adapter = (SinglePickup_Adaptor) recyclerView.getAdapter();
                    result1.get(j).setUpdate(count);
//                        if(result1.get(j).getPiece().equals(String.valueOf(count))){
//                            result1.get(j).setPiece("0");
//                        }
                    adapter.notifyDataSetChanged();

                    if (result1.get(j).getPiece().equals(String.valueOf(count))) {
                        result1.get(j).setUpdate(count);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(context, "All Item scanned", Toast.LENGTH_SHORT).show();
//                        count= 0;

                        /*updateStatus();

                        adaptor.notifyDataSetChanged();*/
                    }

                } else {
                    Toast.makeText(context, "already slot full", Toast.LENGTH_SHORT).show();

                }
            }

        }

    }

    public void updateStatus(){

//        for (int j = 0; j<= result1.size()-1; j++) {
//            if (result1.get(j).getPiece().equals(String.valueOf(result1.get(j).getUpdate()))) {

        btn_update.setVisibility(View.VISIBLE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SinglePickup.this);
                dialog.setTitle("Status")
                        .setCancelable(false)
                        .setMessage("Update Shipment Status")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Retrofit_Data data = Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
                                Call<Update> call = data.update(picker_id, slip_no, pickup_id);
                                call.enqueue(new Callback<Update>() {
                                    @Override
                                    public void onResponse(Call<Update> call, Response<Update> response) {
                                        response.body();
                                        Toast.makeText(SinglePickup.this, "Status Updated", Toast.LENGTH_SHORT).show();
                                        Intent go_back= new Intent(SinglePickup.this,PickupList.class);
                                        startActivity(go_back);
                                        finish();

                                    }

                                    @Override
                                    public void onFailure(Call<Update> call, Throwable t) {
                                        Toast.makeText(SinglePickup.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btn_update.setVisibility(View.GONE);
                                Toast.makeText(SinglePickup.this, "Update Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

//            } else {
//                Toast.makeText(context, "Please Scan All Shipments", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(
                this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to go back");
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        builder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent goback = new Intent(SinglePickup.this, PickupList.class);
                        startActivity(goback);
                        finish();

                    }
                });
        builder.show();
//        Intent goback= new Intent(SinglePickup.this,PickupList.class);
//        startActivity(goback);
//        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }



    @Override
    public void getCount(double count) {
        sumtotal();
        if (update_count==count){
            Toast.makeText(this, "All Package Scanned", Toast.LENGTH_SHORT).show();
            updateStatus();
        }else{
//            Toast.makeText(this,"All Package Not Scanned" ,Toast.LENGTH_LONG).show();
        }
    }
}