package com.bugslayers.quickattend.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.NoticeListActivity;

public class StudentDashboard extends AppCompatActivity {
    Button sendLightBtn,sendNoticeBtn,sendCheckAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        sendLightBtn=(Button) findViewById(R.id.send_student_lightattend);
        sendNoticeBtn=(Button) findViewById(R.id.send_student_notice);
        sendCheckAttendance=(Button) findViewById(R.id.student_check_attendance);
        sendLightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transfer=new Intent(StudentDashboard.this,StudentLightAttendance.class);
                startActivity(transfer);
            }
        });
        sendNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transfer=new Intent(StudentDashboard.this, StudentViewNoticeActivity.class);
                startActivity(transfer);
            }
        });
        sendCheckAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transfer=new Intent(StudentDashboard.this, StudentCheckAttendance.class);
                startActivity(transfer);
            }
        });
    }
}