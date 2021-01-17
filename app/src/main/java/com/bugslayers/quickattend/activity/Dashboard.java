package com.bugslayers.quickattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.bugslayers.quickattend.admin.AdminLoginActivity;
import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.students.StudentLoginActivity;
import com.bugslayers.quickattend.teachers.TeacherLoginActivity;


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
                Intent intent=new Intent(Dashboard.this, AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        teacherCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, TeacherLoginActivity.class);
                startActivity(intent);

            }
        });
        studentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this, StudentLoginActivity.class);
                startActivity(intent);

            }
        });

    }
}