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
import com.bugslayers.quickattend.model.NoticeData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class AddNoticeActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    CollectionReference studentRef;
    String year;
    String branch;

    String title;
    String description;
    String teacher;
    String date;

    EditText titleText;
    EditText descriptionText;
    EditText teacherName;
    Spinner yearSpinner;
    Spinner branchSpinner;
    Button addNotice;

    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        yearSpinner=(Spinner) findViewById(R.id.admin_student_year);
        branchSpinner=(Spinner) findViewById(R.id.admin_student_branch);
        addNotice=(Button) findViewById(R.id.addnotice);

        titleText = (EditText) findViewById(R.id.notice_title_text);
        descriptionText = (EditText)findViewById(R.id.notice_description_text);
        teacherName = (EditText)findViewById(R.id.notice_teacher);

        firestore=FirebaseFirestore.getInstance();
        studentRef=firestore.collection("NoticeData");
        addNotice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        title = titleText.getText().toString();
                        description = descriptionText.getText().toString();
                        teacher = teacherName.getText().toString();
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String formatteddate = df.format(c);

                        date = formatteddate;
                        if(title.isEmpty()||description.isEmpty()||teacher.isEmpty()){
                            Toast.makeText(AddNoticeActivity.this, "enter details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            addNoticeData(date, title, description, teacher);
                        }
                    }
                });
    }

    private void addNoticeData(String date, String title, String description,String teacher) {


                NoticeData noticeData = new NoticeData(date,title,description,teacher);
                String id = UUID.randomUUID().toString();
                year = yearArray[0];
                branch = branchArray[0];
                studentRef.document(branch).collection(year).document(id).set(noticeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddNoticeActivity.this, "Notice Added!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNoticeActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

