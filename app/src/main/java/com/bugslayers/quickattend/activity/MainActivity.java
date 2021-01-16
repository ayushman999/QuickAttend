package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bugslayers.quickattend.R;

public class MainActivity extends AppCompatActivity {

    Button dashboard;
    Button formSheet;
    Button noticeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dashboard=(Button) findViewById(R.id.dashboard);
        formSheet=(Button) findViewById(R.id.sheet_form);
        noticeView = (Button) findViewById(R.id.view_notices_list);

        dashboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent transfer=new Intent(MainActivity.this, Dashboard.class);
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

        }
}