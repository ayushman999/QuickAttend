package com.bugslayers.quickattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bugslayers.quickattend.R;

public class ViewAttendanceRecords extends AppCompatActivity implements AdapterView.OnItemClickListener{

    Button attendance;
    //FirebaseStorage storage;
    //FirebaseFirestore db;
    Spinner yearSpinner;
    Spinner branchSpinner;
    Spinner teacherSpinner;
    String branch;
    String year;
    String teacher;
    String yearArray[] = {"FirstYear", "SecondYear", "ThirdYear", "FourthYear"};
    String branchArray[] = {"EE", "CS", "ME", "CSE", "Imsc"};
    String teacherArray[] = {"Ayushman", "Sameer", "Suman"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Attendance");
        setContentView(R.layout.activity_form_sheet);


        //db = FirebaseFirestore.getInstance();

        yearSpinner = (Spinner) findViewById(R.id.spinner_batch);
        branchSpinner = (Spinner) findViewById(R.id.spinner_branch);
        teacherSpinner = (Spinner) findViewById(R.id.spinner_teacher);
        attendance = (Button) findViewById(R.id.view_atttendance);

        setupYearSpinner(yearSpinner);
        setupBranchSpinner(branchSpinner);
        setupTeacherSpinner(teacherSpinner);

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //uploadInfo();
                Intent intent = new Intent(ViewAttendanceRecords.this, AttendanceView.class);
                //intent.putExtra("year", year);
                //intent.putExtra("branch", branch);
                //intent.putExtra("teacher", teacher);
                startActivity(intent);
                finish();
            }
        });

    }

    private void setupYearSpinner(Spinner yearSpinner) {
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, yearArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new ViewAttendanceRecords.YearSpinner());


    }

    private void setupBranchSpinner(Spinner branchSpinner) {
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchArray);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);
        branchSpinner.setOnItemSelectedListener(new ViewAttendanceRecords.BranchSpinner());
    }

    private void setupTeacherSpinner(Spinner teacherSpinner) {
        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teacherArray);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(new ViewAttendanceRecords.TeacherSpinner());
    }


    class BranchSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            branch = branchArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            branch = branchArray[0];
        }
    }

    class YearSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            year = yearArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            year = yearArray[0];
        }
    }

    class TeacherSpinner implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            teacher = teacherArray[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            teacher = teacherArray[0];
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}

//        private void uploadInfo() {
//
//
//
////        String id = UUID.randomUUID().toString();
////        Map<String, Map<String,String>> doc = new HashMap<>();
////        Map<String,String> item = new HashMap<>();
////
////        item.put("user1","P");
////        item.put("user2","A");
////        doc.put("01-01-2020",item);
////
////        db.collection("Attendance data").document(branchTitle).set(doc)
////                .addOnCompleteListener(new OnCompleteListener<Void>() {
////                    @Override
////                    public void onComplete(@NonNull Task<Void> task) {
////                        Toast.makeText(InfoActivity.this, "Uploaded successfully..", Toast.LENGTH_SHORT).show();
////                    }
////                }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Toast.makeText(InfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
////
////            }
////        });
//
//        }



