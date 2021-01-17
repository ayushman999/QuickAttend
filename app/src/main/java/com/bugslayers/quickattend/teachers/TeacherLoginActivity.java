package com.bugslayers.quickattend.teachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.MainActivity;
import com.bugslayers.quickattend.activity.TakeAttendance;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TeacherLoginActivity extends AppCompatActivity {
    EditText e;
    Button b1;
    String TAG="abcd";
    static String name;


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String Email="";
    private final CollectionReference teacherRef = db.collection("teacher");
    ArrayList<String> teachers=new ArrayList<>();
    ArrayList<String> teachersName=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        e= findViewById(R.id.log);

        b1=findViewById(R.id.login);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=e.getText().toString();
                loadnote(v);
            }
        });


    }
    public void loadnote(View v) {

        teacherRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    String email=documentSnapshot.getString("email");
                    String Name=documentSnapshot.getString("name");

                    teachersName.add(Name);
                    teachers.add(email);
                }
                Log.d(TAG,"problem "+teachers.size());
                if(teachers.contains(Email))
                {
                    int i= teachers.indexOf(Email);
                    name=teachersName.get(i);

                    Log.d(TAG,"Name is "+name);
                    Intent transfer = new Intent(TeacherLoginActivity.this, MainActivity.class);
                    transfer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(transfer);
                    finish();
                }
                else{
                    Toast.makeText(TeacherLoginActivity.this, "invalid email", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TeacherLoginActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public static String getTeacherName()
    {
        return name;
    }
}