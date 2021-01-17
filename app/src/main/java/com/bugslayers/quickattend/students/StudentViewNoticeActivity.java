package com.bugslayers.quickattend.students;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.NoticeDataAdapter;
import com.bugslayers.quickattend.model.NoticeData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class StudentViewNoticeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    RecyclerView noticerecyclerView;
    NoticeDataAdapter adapter;
    String branch;
    String year;
    String teacher;
    Button sort;
    Spinner teacherSpinner;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    DocumentReference dataTransfer;
    CollectionReference studentReference;
    ArrayList<NoticeData> noticeList= new ArrayList<>();
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};
    String teacherArray[] = {"Ayaz Ahmad","Bhandari","Mandal"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_notice);

        noticerecyclerView = (RecyclerView) findViewById(R.id.student_notice_list);
        noticerecyclerView.setLayoutManager(new LinearLayoutManager(this));
        teacherSpinner = (Spinner) findViewById(R.id.student_spinner_teacher);
        sort = (Button) findViewById(R.id.sort_notice_list);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noticeList.clear();
                fetchFirestoreData(branch,year);
            }
        });
        setupTeacherSpinner(teacherSpinner);
        branch = branchArray[0];
        year = yearArray[0];
        fetchFirestoreData(branch,year);


    }

    private void fetchFirestoreData(String branch, String year) {

        studentReference = firestore.collection("NoticeData").document(branch).collection(year);
        studentReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots)
                {
                    NoticeData noticeData = snapshot.toObject(NoticeData.class);
                    if(snapshot.exists())
                    {
                        String title = noticeData.getTitle();
                        String description = noticeData.getDescription();
                        String date = noticeData.getDate();
                        String teacherName = noticeData.getTeacher();
                        if(teacherName.equals(teacher)) {
                            noticeList.add(new NoticeData(date, title, description, teacherName));
                        }
                    }

                }
                adapter = new NoticeDataAdapter(StudentViewNoticeActivity.this,noticeList);
                noticerecyclerView.setAdapter(adapter);

                // swipeAdapter=new SwipeAdapter(list,SwipeList.this);
                // recyclerView.setAdapter(swipeAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StudentViewNoticeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.i("onFailure:",e.getLocalizedMessage());
            }
        });

    }
    private void setupTeacherSpinner(Spinner teacherSpinner) {
        ArrayAdapter<String> teacherAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, teacherArray);
        teacherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        teacherSpinner.setAdapter(teacherAdapter);
        teacherSpinner.setOnItemSelectedListener(new TeacherSpinner());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sign_out_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.sign_out_menu:
            {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(StudentViewNoticeActivity.this,"Sign out!",Toast.LENGTH_SHORT).show();
                Intent transfer=new Intent(StudentViewNoticeActivity.this, StudentLoginActivity.class);
                startActivity(transfer);
                finish() ;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}