package com.bugslayers.quickattend.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class StudentAttendanceProfile extends AppCompatActivity {
    String branch,year,teacher,roll_num;
    FirebaseFirestore firestore;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_profile);
        Intent data=getIntent();
        branch=data.getStringExtra("branch");
        year=data.getStringExtra("year");
        teacher=data.getStringExtra("teacher");
        roll_num=data.getStringExtra("roll_num");
        firestore=FirebaseFirestore.getInstance();
        getAttendanceData();

    }

    private void getAttendanceData() {
        firestore.collection(branch).document(year).collection()
    }
}