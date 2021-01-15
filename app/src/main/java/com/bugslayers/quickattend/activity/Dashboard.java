package com.bugslayers.quickattend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bugslayers.quickattend.R;


public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Dashboard");
        setContentView(R.layout.activity_dashboard);

        LinearLayout adminCard=findViewById(R.id.admincard);
        LinearLayout studentCard=findViewById(R.id.studentcard);
        LinearLayout teacherCard=findViewById(R.id.teachercard);

        adminCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(DashboardActivity.this,AdminLoginActivity.class);
                //startActivity(intent);
                //finish();
            }
        });

        teacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(Dashboard.this, TeacherLoginActivity.class);
                //startActivity(intent);
                //finish();

            }
        });
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(DashboardActivity.this, StudentLoginActivity.class);
                //startActivity(intent);
                //finish();

            }
        });

    }
}