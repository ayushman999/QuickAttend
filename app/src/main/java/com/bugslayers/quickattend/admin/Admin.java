package com.bugslayers.quickattend.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.AdminStudentsInput;
import com.bugslayers.quickattend.activity.AdminTeachersInput;
import com.bugslayers.quickattend.teachers.teacherRegisterActivity;


public class Admin extends AppCompatActivity {
 Button addStudent;
 Button addTeacher;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        addStudent = findViewById(R.id.add_newStudent);
        addTeacher = findViewById(R.id.add_newTeacher);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, AdminStudentsInput.class);
                startActivity(intent);

            }
        });

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, teacherRegisterActivity.class);
                startActivity(intent);


            }
        });
    }
}