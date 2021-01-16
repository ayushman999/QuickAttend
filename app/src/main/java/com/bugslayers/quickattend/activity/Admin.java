package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.bugslayers.quickattend.R;


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
                finish();
            }
        });

        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, AdminTeachersInput.class);
                startActivity(intent);
                finish();

            }
        });
    }

}