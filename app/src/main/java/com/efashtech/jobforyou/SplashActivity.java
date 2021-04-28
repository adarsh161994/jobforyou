package com.efashtech.jobforyou;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //method 1 for splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, loginRegisterEmployeeActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
//        getSupportActionBar().hide();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //method 2 for splash screen

//        Thread td = new Thread(){
//            public void run(){
//                try {
//                    sleep(5000);
//
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//                finally {
//                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        };td.start();

    }
}