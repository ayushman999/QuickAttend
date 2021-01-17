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

import com.bugslayers.quickattend.R;

public class StudentCheckAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText rollNumEdit;
    Button checkAttend;
    Spinner yearSpinner;
    Spinner branchSpinner;
    Spinner teacherSpinner;
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String teacherArray[]={"Ayushman","Suman","Sameer"};
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
                Intent transfer=new Intent(StudentCheckAttendance.this, StudentAttendanceProfile.class);
                transfer.putExtra("branch",branch);
                transfer.putExtra("year",year);
                transfer.putExtra("roll_num",roll_num);
                transfer.putExtra("teacher",teacher);
                startActivity(transfer);
            }
        });
    }

    private void setupTeacherSpinner(Spinner teacherSpinner) {
        ArrayAdapter<String> teacherAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,teacherArray);
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
            teacher=teacherArray[position];


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            teacher=teacherArray[0];
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