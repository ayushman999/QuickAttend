package com.bugslayers.quickattend.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bugslayers.quickattend.R;

import java.util.ArrayList;

public class StudentCheckAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText rollNumEdit;
    Button checkAttend;
    Spinner yearSpinner;
    Spinner branchSpinner;
    Spinner teacherSpinner;
    ArrayList<String> teacherName=StudentDashboard.getTeachersName();
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};
    String roll_num;
    String branch;
    String year;
    String teacher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_check_attendance);
        rollNumEdit=(EditText) findViewById(R.id.student_roll_num);
        checkAttend=(Button) findViewById(R.id.student_check_attendance);
        yearSpinner=(Spinner) findViewById(R.id.student_year);
        branchSpinner=(Spinner) findViewById(R.id.student_branch);
        teacherSpinner=(Spinner) findViewById(R.id.student_teacher);
        setupYearSpinner(yearSpinner);
        setupBranchSpinner(branchSpinner);
        setupTeacherSpinner(teacherSpinner);
        checkAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll_num=rollNumEdit.getText().toString();
                if (roll_num.isEmpty()){
                    Toast.makeText(StudentCheckAttendance.this, "enter roll No.", Toast.LENGTH_SHORT).show();
                }else {
                Intent transfer=new Intent(StudentCheckAttendance.this, StudentAttendanceProfile.class);
                transfer.putExtra("branch",branch);
                transfer.putExtra("year",year);
                transfer.putExtra("roll_num",roll_num);
                transfer.putExtra("teacher",teacher);
                startActivity(transfer);
            }}
        });
    }

    private void setupTeacherSpinner(Spinner teacherSpinner) {
        ArrayAdapter<String> teacherAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,teacherName);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(new TeacherSpinner());
    }


    private void setupYearSpinner(Spinner yearSpinner) {
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,yearArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new YearSpinner());
    }

    private void setupBranchSpinner(Spinner branchSpinner) {
        ArrayAdapter<String> branchAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,branchArray);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);
        branchSpinner.setOnItemSelectedListener(new BranchSpinner());
    }
    class BranchSpinner implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            branch=branchArray[position];


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            branch=branchArray[0];
        }
    }
    class TeacherSpinner implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            teacher=teacherName.get(position);


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            teacher=teacherName.get(0);
        }
    }
    class YearSpinner implements AdapterView.OnItemSelectedListener
    {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            year=yearArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            year=yearArray[0];
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}