package com.bugslayers.quickattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.NoticeDataAdapter;
import com.bugslayers.quickattend.adapter.SwipeAdapter;
import com.bugslayers.quickattend.model.AttendanceData;
import com.bugslayers.quickattend.model.NoticeData;
import com.bugslayers.quickattend.model.Students;
import com.bugslayers.quickattend.students.StudentLoginActivity;
import com.bugslayers.quickattend.students.StudentProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class NoticeListActivity extends AppCompatActivity {
    RecyclerView noticeListrecyclerView;
    NoticeDataAdapter adapter;
    String branch;
    String year;
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    DocumentReference dataTransfer;
    CollectionReference studentReference;
    ArrayList<NoticeData> noticeList= new ArrayList<>();
    String yearArray[]={"FirstYear","SecondYear","ThirdYear", "FourthYear"};
    String branchArray[]={"EE","CS","ME"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        noticeListrecyclerView = (RecyclerView) findViewById(R.id.notice_recyclerview);
        noticeListrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adapter = new AdapterMaterials(options);
        //booksListrecyclerView.setAdapter(adapter);
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
                        String teacher = noticeData.getTeacher();
                        noticeList.add(new NoticeData(date,title,description,teacher));
                    }

                }
                adapter = new NoticeDataAdapter(NoticeListActivity.this,noticeList);
                noticeListrecyclerView.setAdapter(adapter);
               // swipeAdapter=new SwipeAdapter(list,SwipeList.this);
               // recyclerView.setAdapter(swipeAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NoticeListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.i("onFailure:",e.getLocalizedMessage());
            }
        });

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
                Toast.makeText(com.bugslayers.quickattend.activity.NoticeListActivity.this,"Sign out!",Toast.LENGTH_SHORT).show();
                Intent transfer=new Intent(NoticeListActivity.this, StudentLoginActivity.class);
                startActivity(transfer);
                finish() ;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}