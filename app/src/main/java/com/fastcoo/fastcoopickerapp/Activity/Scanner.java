package com.fastcoo.fastcoopickerapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import com.fastcoo.fastcoopickerapp.R;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class Scanner extends AppCompatActivity implements ZBarScannerView.ResultHandler {
        Toolbar toolbar;
        private ZBarScannerView mScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        toolbar= findViewById(R.id.toolbar_scanner);
        setSupportActionBar(toolbar);
        setTitle("Scan Totes");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZBarScannerView(Scanner.this);
        contentFrame.addView(mScannerView);

    }

    @Override
    public void handleResult(Result rawResult) {
        Handler handler = new Handler();
        Intent i = new Intent(Scanner.this, AutoPickupList.class);
        i.putExtra("scanvalue",""+rawResult.getContents().toString() );
        setResult(Activity.RESULT_OK,i);
//        startActivity(i);
        finish();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(Scanner.this);
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
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
}