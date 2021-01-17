package com.bugslayers.quickattend.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.AttendanceList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentAttendanceProfile extends AppCompatActivity {
    String branch,year,teacher,roll_num;
    FirebaseFirestore firestore;
    CollectionReference teacherRef;
    int absent,present;
    TextView percentageView,absentView,presentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        absent=0;
        present=0;
        setContentView(R.layout.activity_student_attendance_profile);
        percentageView=(TextView) findViewById(R.id.attendance_percentage);
        absentView=(TextView) findViewById(R.id.absent_num);
        presentView=(TextView) findViewById(R.id.present_num);
        Intent data=getIntent();
        branch=data.getStringExtra("branch");
        year=data.getStringExtra("year");
        teacher=data.getStringExtra("teacher");
        roll_num=data.getStringExtra("roll_num");
        firestore=FirebaseFirestore.getInstance();
        teacherRef=firestore.collection(branch).document(year).collection(teacher);
        getAttendanceData();

    }

    private void getAttendanceData() {
        int roll=Integer.parseInt(roll_num);
        teacherRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots)
                {
                    int position=0;
                    AttendanceList data=snapshot.toObject(AttendanceList.class);
                    ArrayList<Integer> rollNum=data.getRoll_num();
                    ArrayList<String> name=data.getName();
                    ArrayList<String> status=data.getStatus();
                    for(int i=0;i<rollNum.size();i++)
                    {
                        if(rollNum.get(i)==roll)
                        {
                            position=i;
                        }
                    }
                    if(status.get(position).equals("Present"))
                    {
                        present++;
                    }
                    else
                    {
                        absent++;
                    }
                }
                calculatePercentage();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentAttendanceProfile.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void calculatePercentage() {
        if(present+absent==0)
        {
            return;
        }
        double percentage=(present*100)/(present+absent);
        if(percentage<75)
        {
            percentageView.setTextColor(getResources().getColor(R.color.red));
        }
        presentView.setText(present+"");
        absentView.setText(absent+"");
        percentageView.setText(percentage+"%");
    }
}