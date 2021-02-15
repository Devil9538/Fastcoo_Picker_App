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

import com.fastcoo.fastcoopickerapp.Adaptor.Batch_adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Interface.UpdateCount;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_Result;
import com.fastcoo.fastcoopickerapp.Model.SinglePickup_list;
import com.fastcoo.fastcoopickerapp.Model.Sku;
import com.fastcoo.fastcoopickerapp.Model.Update;
import com.fastcoo.fastcoopickerapp.R;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Batch_Pickup extends AppCompatActivity implements UpdateCount {

    SharedPreferences batch_pref;
    SharedPreferences.Editor batch_editor;
    String picker_id = "", pickup_id;
    RecyclerView recyclerView;
    Toolbar toolbar;
    public EditText value;
    private static ProgressDialog mProgressDialog;
    TextView noData;
    Batch_adaptor adaptor;
    ArrayList<SinglePickup_Result> result1;
    private Context context = null;
    int count = 0;
    double update_count = 0;
    Button btn_update;
    Set<String> scaned_ean = new HashSet<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView enter;
    String temp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch__pickup);
        batch_pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        batch_editor = batch_pref.edit();
        picker_id = batch_pref.getString("user_id", null);
        pickup_id = getIntent().getStringExtra("pickup_id");
        recyclerView = findViewById(R.id.recycler_batch_list);
        toolbar = findViewById(R.id.toolbar_batchpickup);
        enter = findViewById(R.id.img_enter);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        noData = findViewById(R.id.tv_no_data_singlepickup);
        context = this;
        value = findViewById(R.id.edt_value);
        btn_update = findViewById(R.id.btn_update);

        getBatchPickup();

//        value.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                switch (actionId) {
//
//                    case EditorInfo.IME_ACTION_DONE:
//
//                        check();
//                        value.setText("");
//                        return true;
//
//
//                    default:
//                        return false;
//                }
//            }
//        });

//        value.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //after the change calling the method and passing the search input
//
//                filter(editable.toString());
//            }
//        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                scaned_ean.add(value.getText().toString());


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

    public void ListClick(String eanNum){

        value.setText(eanNum);
    }

//    private void filter(String text) {
//        //new array list that will hold the filtered data
//        ArrayList<SinglePickup_Result> filterdNames = new ArrayList<>();
//
//        //looping through existing elements
//        for (SinglePickup_Result s : result1) {
//            //if the existing elements contains the search input
//            if (s.getEanNo().contains(text.toLowerCase())) {
//                //adding the element to filtered list
//                filterdNames.add(s);
//            }
//        }
//
//        //calling a method of the adapter class and passing the filtered list
//        adaptor.filterList(filterdNames);
//    }

    public void getBatchPickup() {
        mProgressDialog = ProgressDialog.show(this, "Single PickupList", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Retrofit_Data data = Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<SinglePickup_list> call = data.batch_pickup(picker_id, pickup_id);
        call.enqueue(new Callback<SinglePickup_list>() {
            @Override
            public void onResponse(Call<SinglePickup_list> call, Response<SinglePickup_list> response) {

                SinglePickup_Result result = new SinglePickup_Result();
                if (response.body().getStatus().equals(200)) {

                    for (int i = 0; i <= response.body().getResult().size() - 1; i++) {
                        String slip = response.body().getResult().get(i).getSku();
//                        String eanno = response.body().getResult().get(i).getEanNo();
                        String piece_count = response.body().getResult().get(i).getPiece();
                        String cod_value = response.body().getResult().get(i).getCod();
                        String shelve= response.body().getResult().get(i).getShelveNo();
                        response.body().getResult().get(i).setShelveNo(shelve);
                        response.body().getResult().get(i).setSlipNo(slip);
//                        result.setEanNo(eanno);
                        response.body().getResult().get(i).setCod(cod_value);
                        response.body().getResult().get(i).setPiece(piece_count);
//                        result.setCod(cod_value);
//                        result.setPiece(piece_count);
                        result1 = (ArrayList<SinglePickup_Result>) response.body().getResult();
                        adaptor = new Batch_adaptor((ArrayList<SinglePickup_Result>) response.body().getResult(), context, Batch_Pickup.this);
                        recyclerView.setLayoutManager(new GridLayoutManager(Batch_Pickup.this, 1, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adaptor);
                        adaptor.notifyDataSetChanged();
                        mProgressDialog.dismiss();

                    }
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                    noData.setVisibility(View.VISIBLE);
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SinglePickup_list> call, Throwable t) {
                Toast.makeText(Batch_Pickup.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void check() {
        sumtotal();

        for (int j = 0; j < result1.size(); j++) {
            if (value.getText().toString().trim().equals(result1.get(j).getSku().trim())) {

                temp = value.getText().toString();
                if (Integer.valueOf(result1.get(j).getPiece()) != count) {
                    count = count + 1;

                    SinglePickup_Result item = new SinglePickup_Result();
//                    System.out.println("doublecheck" + value.getText().toString().equals(result1.get(j).getSku().get(j).getEanNo()));
                    Toast.makeText(Batch_Pickup.this, "Item Scanned", Toast.LENGTH_SHORT).show();
                    Batch_adaptor adapter = (Batch_adaptor) recyclerView.getAdapter();
                    result1.get(j).setUpdate(count);
//                        if(result1.get(j).getPiece().equals(String.valueOf(count))){
//                            result1.get(j).setPiece("0");
//                        }
                    adapter.notifyDataSetChanged();

                    if (result1.get(j).getSku().equals(String.valueOf(count))) {
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

    public void sumtotal() {
        double total = 0;

        for (SinglePickup_Result singlePickup_result : result1) {

            total += Double.valueOf(singlePickup_result.getPiece());

        }
        update_count = total;


    }


    public void updateStatus() {

        btn_update.setVisibility(View.VISIBLE);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Batch_Pickup.this);
                dialog.setTitle("Status")
                        .setCancelable(false)
                        .setMessage("Update Shipment Status")
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Retrofit_Data data = Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
                                Call<Update> call = data.update_batch(picker_id, pickup_id);
                                call.enqueue(new Callback<Update>() {
                                    @Override
                                    public void onResponse(Call<Update> call, Response<Update> response) {
                                        response.body();
                                        Toast.makeText(Batch_Pickup.this, "Status Updated", Toast.LENGTH_SHORT).show();

                                        Intent go_back = new Intent(Batch_Pickup.this, PickupList.class);
                                        startActivity(go_back);
                                        finish();
                                        btn_update.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public void onFailure(Call<Update> call, Throwable t) {
                                        Toast.makeText(Batch_Pickup.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancell", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                btn_update.setVisibility(View.GONE);
                                Toast.makeText(Batch_Pickup.this, "Update Cancelled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();

            }
        });

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
                        Intent goback = new Intent(Batch_Pickup.this, PickupList.class);
                        startActivity(goback);
                        finish();

                    }
                });
        builder.show();

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

