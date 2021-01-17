package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bugslayers.quickattend.R;

public class MainActivity extends AppCompatActivity {

    Button takeAttendance;
    Button formSheet;
    Button noticeView;
    Button lightAttend;
    Button addNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takeAttendance =(Button) findViewById(R.id.dashboard);
        formSheet=(Button) findViewById(R.id.sheet_form);
        noticeView = (Button) findViewById(R.id.view_notices_list);
        lightAttend=(Button) findViewById(R.id.teacher_light_attend);
        addNotice=(Button) findViewById(R.id.add_notice);
        takeAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent transfer=new Intent(MainActivity.this, TakeAttendance.class);
                    startActivity(transfer);
                }
            });
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transfer=new Intent(MainActivity.this,AddNoticeActivity.class);
                startActivity(transfer);
            }
        });
        formSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent transfer=new Intent(MainActivity.this, ViewAttendanceRecords.class);
                    startActivity(transfer);
                }
            });
        noticeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent transfer=new Intent(MainActivity.this, NoticeListActivity.class);
                startActivity(transfer);
            }
        });
        lightAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transfer=new Intent(MainActivity.this, LightAttend.class);
                startActivity(transfer);
            }
        });

        }
}