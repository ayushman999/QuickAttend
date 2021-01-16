package com.bugslayers.quickattend.teachers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bugslayers.quickattend.R;

public class TeacherProfile extends AppCompatActivity {
String TAG="abcd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        Intent intent = getIntent();
        String name=intent.getStringExtra("NAME");
        Log.d(TAG,"name is "+name);
        TextView textView=findViewById(R.id.teacher_name);
        String s="Teacher name is "+name;
        textView.setText(s);
    }
}