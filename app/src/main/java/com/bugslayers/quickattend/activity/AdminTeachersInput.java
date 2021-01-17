package com.bugslayers.quickattend.activity;

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
import com.bugslayers.quickattend.model.Teachers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminTeachersInput extends AppCompatActivity implements AdapterView.OnItemClickListener{

    FirebaseFirestore firestore;
    CollectionReference teacherRef;
    String emailid,name;
    Button addTeacher;
    EditText nameEdit;
    EditText emailidEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_teachers_input);

        addTeacher = (Button) findViewById(R.id.add_teacher);
        nameEdit=(EditText) findViewById(R.id.admin_teacher_name);
        emailidEdit=(EditText) findViewById(R.id.admin_teacher_id);

        firestore = FirebaseFirestore.getInstance();
        teacherRef = firestore.collection("teacher");
        addTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                emailid = emailidEdit.getText().toString();
                addTeacherData(name,emailid);

            }
        });
    }

    private void addTeacherData(String name, String email) {
        Teachers teacher= new Teachers(email,name);

        teacherRef.document().set(teacher).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminTeachersInput.this, "Teacher Added!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(AdminTeachersInput.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}