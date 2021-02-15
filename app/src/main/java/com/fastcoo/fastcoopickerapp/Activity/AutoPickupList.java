package com.fastcoo.fastcoopickerapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fastcoo.fastcoopickerapp.Adaptor.AutoPickup_Adaptor;
import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Model.Picklist_Detail;
import com.fastcoo.fastcoopickerapp.Model.Picklist_Main;
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

public class AutoPickupList extends AppCompatActivity {

    String pickup_id,user_id;
    String totes_value;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    List<List<Picklist_Detail>> pickup_detail;
    AutoPickup_Adaptor adaptor;
    RecyclerView recyclerView;
    TextView noData;
    private static ProgressDialog mProgressDialog;
    Toolbar toolbar;
    ImageView scanner;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    TextView pickupid_main;
    List<String > scanned_totes = new ArrayList<>();
    TextView totes,totes_scan;
    List<String> slip = new ArrayList<>();
    List<String> final_slip= new ArrayList<>();
    List<String> new_idList= new ArrayList<>();
    List<String> final_idList= new ArrayList<String>(new LinkedHashSet<String>(new_idList));
    String re;


    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_pickup_list);
        recyclerView= findViewById(R.id.recycler_autopickupdetail_list);

        pickup_id= getIntent().getStringExtra("pickup_id");
        totes_value= (getIntent().getStringExtra("totes_value"));
        noData= findViewById(R.id.tv_no_data_auto);
        toolbar= findViewById(R.id.toolbar_autopickupdetail);
        pickupid_main= findViewById(R.id.tv_picupid_main);
        scanner= findViewById(R.id.img_scanner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        totes= findViewById(R.id.tv_totes);
        totes_scan= findViewById(R.id.tv_scanned_totes);
        preferences= getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor= preferences.edit();
        user_id= preferences.getString("user_id",null);

        getPickList();
        openScanner();


    }

    public void getPickList(){
        mProgressDialog = ProgressDialog.show(this, "PickList", "Loading..");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<Picklist_Main> call= data.getPickList(user_id,pickup_id,totes_value);
        call.enqueue(new Callback<Picklist_Main>() {
            @Override
            public void onResponse(Call<Picklist_Main> call, Response<Picklist_Main> response) {
               response.body();
               List<List<Picklist_Detail>> main_detail= new ArrayList<>();

               if(response.body().getStatus().equals(200)){
                   for(int i=0;i<response.body().getResult().size();i++){
                       System.out.println("result"+response.body().getResult().toString());
                       List<Picklist_Detail> new_list= response.body().getResult().get(i);

                       String id= new_list.get(0).getPickupId();
                       totes.setText("Total Totes Required: "+totes_value);
                       pickupid_main.setText("Pickup ID: "+id);
                       for (int j=0;j<new_list.size();j++) {
                           String pickupId= new_list.get(j).getPickupId();
                           new_list.get(j).setPickupId(pickupId);
                           String booking_id= new_list.get(j).getBookingId();
                           new_list.get(j).setBookingId(booking_id);
                           String slipNo= new_list.get(j).getSlipNo();
                           slip.add(slipNo);
                           Log.d("Slip_array",slip.toString());
                           new_list.get(j).setSlipNo(slipNo);
                           String new_id= new_list.get(j).getNewId();
                           new_idList.add(new_id);
                           Log.d("loop new-d",new_idList.toString());
                           new_list.get(j).setNewId(new_id);


                       }


                       main_detail= response.body().getResult();
                       pickup_detail=response.body().getResult();
                       adaptor = new AutoPickup_Adaptor(main_detail, AutoPickupList.this,totes_value);
                       recyclerView.setLayoutManager(new GridLayoutManager(AutoPickupList.this, 1, LinearLayoutManager.VERTICAL, false));
                       recyclerView.setAdapter(adaptor);
                       adaptor.notifyDataSetChanged();
                       mProgressDialog.dismiss();
                   }
               }else {
                   response.body().equals(null);
                   recyclerView.setVisibility(View.GONE);
                   noData.setVisibility(View.VISIBLE);
                   pickupid_main.setVisibility(View.GONE);
                   mProgressDialog.dismiss();


               }


            }

            @Override
            public void onFailure(Call<Picklist_Main> call, Throwable t) {
                Toast.makeText(AutoPickupList.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

                // Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            if(resultCode== Activity.RESULT_OK){
//                Toast.makeText(this, "Scanned", Toast.LENGTH_SHORT).show();
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,150);
                scanned_totes.add("\""+data.getStringExtra("scanvalue")+"\"");

                totes_scan.setVisibility(View.VISIBLE);
                totes_scan.setText("Number of Totes Scanned: "+String.valueOf(scanned_totes.size()));

                if(scanned_totes.size()==Integer.parseInt(totes_value)){

                    scanner.setVisibility(View.GONE);
                    Toast.makeText(this, "All Totes Scanned", Toast.LENGTH_SHORT).show();
                    AutoPickup_Adaptor adaptor= (AutoPickup_Adaptor)recyclerView.getAdapter();
                    adaptor.getScannedlist(scanned_totes);
                    adaptor.notifyDataSetChanged();
                    final_slip.clear();
                    final_idList.clear();


                }

                Log.d("scanned_totes",scanned_totes.toString());
            }
        }

    }

    public void openScanner(){
        scanner.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                }else {
                    Intent scanner = new Intent(AutoPickupList.this,Scanner.class);
                    startActivityForResult(scanner,100);
                }

            }
        });
    }

//    public void sendData(){
//        for (int i=0;i<scanned_totes.size();i++){
//            slip.get(i);
//            new_idList.get(i);
//            final_slip.add("\""+slip.get(i)+"\"");
//            final_slip.toString().replace("[", "").replace("]", "");
//            re= final_slip.toString();
//            final_idList.add("\""+new_idList.get(i)+"\"");
//            Set<String> toRetain = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
//            toRetain.addAll(new_idList);
//            Set<String> set = new LinkedHashSet<String>(new_idList);
//            set.retainAll(new LinkedHashSet<String>(toRetain));
//            final_idList = new ArrayList<String>(set);
//
//            Log.d("To be sent",final_slip.toString());
//            Log.d("final idlist",final_idList.toString());
//        }
//
//    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,AutoGeneratedPickup.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        AutoPickupList.this.finish();
    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return true;
    }

}