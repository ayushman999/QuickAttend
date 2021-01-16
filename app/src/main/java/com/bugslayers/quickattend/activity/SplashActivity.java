package com.bugslayers.quickattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.bugslayers.quickattend.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                FirebaseAuth auth = FirebaseAuth.getInstance();
//                FirebaseUser user = auth.getCurrentUser();
//                if (user != null){
//                    startActivity(new Intent(SplashActivity.this, Dashboard.class));
//                }else{
//                    startActivity(new Intent(SplashActivity.this, IntroActivity.class));
//                }
                startActivity(new Intent(SplashActivity.this, Dashboard.class));
                finish();
            }
        }, 2000);
    }
}
