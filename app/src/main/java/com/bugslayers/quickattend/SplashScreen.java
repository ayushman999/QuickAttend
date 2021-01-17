package com.bugslayers.quickattend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bugslayers.quickattend.activity.Dashboard;
import com.bugslayers.quickattend.activity.IntroActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent transfer=new Intent(SplashScreen.this, IntroActivity.class);
                startActivity(transfer);
                finish();
            }
        },2000);
    }
}