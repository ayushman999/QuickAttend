package com.bugslayers.quickattend.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.NoticeListActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentDashboard extends AppCompatActivity {
    Button sendLightBtn,sendNoticeBtn,sendCheckAttendance;
    static ArrayList<String> teachersName=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        sendLightBtn=(Button) findViewById(R.id.send_student_lightattend);
        sendNoticeBtn=(Button) findViewById(R.id.send_student_notice);
        sendCheckAttendance=(Button) findViewById(R.id.student_check_attendance);
        fetchTeacherList();
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

    private void fetchTeacherList() {
        FirebaseFirestore.getInstance().collection("teacher").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    String name=documentSnapshot.getString("name");
                    teachersName.add(name);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentDashboard.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static ArrayList<String> getTeachersName()
    {
        return teachersName;
    }
}