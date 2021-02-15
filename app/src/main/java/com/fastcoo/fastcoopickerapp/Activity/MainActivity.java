package com.fastcoo.fastcoopickerapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fastcoo.fastcoopickerapp.Model.Login_Details;
import com.fastcoo.fastcoopickerapp.R;

import com.fastcoo.fastcoopickerapp.Interface.Retrofit_Data;
import com.fastcoo.fastcoopickerapp.Service.Retrofit_Service;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    SharedPreferences login_preference;
    SharedPreferences.Editor login_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login= findViewById(R.id.btn_login);
        email= findViewById(R.id.edt_email);
        password= findViewById(R.id.edt_password);
        login_preference =getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        login_editor= login_preference.edit();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Please Enter Valid Email Id");

                }
                else if(email.getText().toString().equals("")){
                    email.setError("Email Can not be empty");


                }else  if( password.getText().toString().equals("")){

                    password.setError("Password can not be empty");
                }
                else {
                    getLoginDetails();

                }

            }
        });

        String value = login_preference.getString("user_id",null);
        if (value == null) {
            // the key does not exist
        } else {
            Intent i = new Intent(MainActivity.this, Dashboard.class);
            startActivity(i);

        }
    }

    public void getLoginDetails(){

       Retrofit_Data data= Retrofit_Service.getRetrofitInstance().create(Retrofit_Data.class);
        Call<Login_Details> call=data.login(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<Login_Details>() {
            @Override
            public void onResponse(Call<Login_Details> call, Response<Login_Details> response) {

                if(response.body().getStatus().equals(200)){

                    String user_id= response.body().getResult().getUserId();
                    String username= response.body().getResult().getUsername();
                    String mobile_no= response.body().getResult().getMobileNo();
                    login_editor.putString("user_id",user_id);
                    login_editor.putString("username",username);
                    login_editor.putString("mobile_no",mobile_no);
                    login_editor.commit();
                    Intent dashboard= new Intent(MainActivity.this, Dashboard.class);
                    startActivity(dashboard);
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    finish();


                }else{
                    Toast.makeText(MainActivity.this, "Wrong Credentails!!!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Login_Details> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}