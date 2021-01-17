package com.bugslayers.quickattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.Students;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminStudentsInput extends AppCompatActivity implements AdapterView.OnItemClickListener {
    FirebaseFirestore firestore;
    CollectionReference studentRef;
    String year,branch,name;
    String roll_num;
    Spinner yearSpinner;
    Spinner branchSpinner;
    Button addStudent;
    EditText nameEdit;
    EditText roll_numEdit;
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students_input);
        yearSpinner=(Spinner) findViewById(R.id.admin_student_year);
        branchSpinner=(Spinner) findViewById(R.id.admin_student_branch);
        addStudent=(Button) findViewById(R.id.add_student);
        nameEdit=(EditText) findViewById(R.id.admin_student_name);
        roll_numEdit=(EditText) findViewById(R.id.admin_student_roll_num);
        setupYearSpinner(yearSpinner);
        setupBranchSpinner(branchSpinner);
        firestore=FirebaseFirestore.getInstance();
        studentRef=firestore.collection("StudentData");
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=nameEdit.getText().toString();
                roll_num=roll_numEdit.getText().toString();

                if (name.equals("") || roll_num.equals("")) {
                    Toast.makeText(AdminStudentsInput.this, "fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    addStudentData(name, roll_num, year);
                }
            }
        });
    }

    private void addStudentData(String name, String roll_num,  String year) {



        int roll = Integer.parseInt(roll_num);
        Students student = new Students(name, roll);
        studentRef.document(branch).collection(year).document(roll_num).set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminStudentsInput.this, "Student Added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(AdminStudentsInput.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupBranchSpinner(Spinner branchSpinner) {
        ArrayAdapter<String> branchAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,branchArray);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);
        branchSpinner.setOnItemSelectedListener(new BranchSpinner());
    }

    private void setupYearSpinner(Spinner yearSpinner) {
        ArrayAdapter<String> yearAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,yearArray);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(new YearSpinner());
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