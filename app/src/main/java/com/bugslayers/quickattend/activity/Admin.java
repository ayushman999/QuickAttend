package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.students.StudentLoginActivity;
import com.bugslayers.quickattend.teachers.TeacherLoginActivity;

public class Admin extends AppCompatActivity {
Button addStudent;
Button addTeacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addStudent=findViewById(R.id.add_newStudent);
        addTeacher=findViewById(R.id.add_newTeacher);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Admin.this, AdminStudentsInput.class));
            }
        });
        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity( new Intent(Admin.this, TeacherLoginActivity.class));
           }
        });
    }
}