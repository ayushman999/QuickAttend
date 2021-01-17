package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.AttendanceData;

import java.util.ArrayList;

public class TakeAttendance extends AppCompatActivity implements AdapterView.OnItemClickListener {
    Button takeAttendance;
    String year;
    String branch;
    Spinner yearSpinner;
    Spinner branchSpinner;
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        takeAttendance=(Button) findViewById(R.id.add_student);
        yearSpinner=(Spinner) findViewById(R.id.take_attendance_year);
        branchSpinner=(Spinner) findViewById(R.id.take_attendance_branch);
        setupYearSpinner(yearSpinner);
        setupBranchSpinner(branchSpinner);
        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transfer=new Intent(TakeAttendance.this,SwipeList.class);
                transfer.putExtra("year",year);
                transfer.putExtra("branch",branch);
                startActivity(transfer);
            }
        });


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