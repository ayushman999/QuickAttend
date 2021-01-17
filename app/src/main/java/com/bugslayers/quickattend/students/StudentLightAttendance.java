package com.bugslayers.quickattend.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.LightAttend;
import com.bugslayers.quickattend.model.LightData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentLightAttendance extends AppCompatActivity {
    EditText roomIdEdit,nameEdit,emailEdit,phoneNumEdit;
    Button sendData;
    String roomId,name,email,phoneNum;
    CollectionReference roomRef;
    FirebaseFirestore firestore;
    String validity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_light_attendance);
        roomIdEdit=(EditText) findViewById(R.id.light_student_room_id);
        nameEdit=(EditText) findViewById(R.id.light_student_name);
        emailEdit=(EditText) findViewById(R.id.light_student_email);
        phoneNumEdit=(EditText) findViewById(R.id.light_student_phone_num);
        sendData=(Button) findViewById(R.id.light_student_button);
        firestore=FirebaseFirestore.getInstance();
        roomRef=firestore.collection("LightAttend");
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validity="notDefined";
                getEditData();
                checkDatabaseValidity();
            }
        });

    }

    private void checkDatabaseValidity() {
        roomRef.document(roomId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                validity = documentSnapshot.getString("validity");
                if (validity != null) {
                    if (validity.equals("online")) {
                        addStudentFirestore();
                    }
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentLightAttendance.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void addStudentFirestore() {
        if(validity.equals("online")) {
            LightData data = new LightData(name, phoneNum, email);
            roomRef.document(roomId).collection("Attendees").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(StudentLightAttendance.this, "You are in!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StudentLightAttendance.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, validity + "\n" + "Room does not exists", Toast.LENGTH_SHORT).show();

        }

    }

    private void getEditData() {
        roomId=roomIdEdit.getText().toString();
        name=nameEdit.getText().toString();
        phoneNum=phoneNumEdit.getText().toString();
        email=emailEdit.getText().toString();
    }
}