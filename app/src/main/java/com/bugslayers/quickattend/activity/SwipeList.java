package com.bugslayers.quickattend.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.SwipeAdapter;
import com.bugslayers.quickattend.model.AttendanceData;
import com.bugslayers.quickattend.model.Students;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SwipeList extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeAdapter swipeAdapter;
    int temp;
    String tempName;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Students> list=new ArrayList<>();
    ArrayList<AttendanceData> attendanceList=new ArrayList<AttendanceData>();
    Button sendData;
    //Firebase firestore
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    DocumentReference dataTransfer;
    CollectionReference studentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_list);
        Intent intent=getIntent();
        String branch=intent.getStringExtra("branch");
        String year=intent.getStringExtra("year");
        fetchFirestoreData(branch,year);
        recyclerView=(RecyclerView) findViewById(R.id.swipe_list);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        sendData=(Button) findViewById(R.id.upload_attendance);
        sendData.setVisibility(View.GONE);
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        sendData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                uploadAttendanceData(branch,year);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadAttendanceData(String branch, String year) {
        Collections.sort(attendanceList);
        String date= LocalDate.now().toString();
        dataTransfer=firestore.collection(branch).document(year).collection(date).document("Ayushman");
        Map<String,Object> data=new HashMap<>();
        data.put("list",attendanceList);
        dataTransfer.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SwipeList.this, "Success!", Toast.LENGTH_SHORT).show();
                attendanceList.clear();
                list.clear();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(SwipeList.this, "Failed to Upload!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchFirestoreData(String branch, String year) {
        studentReference=firestore.collection("StudentData").document(branch).collection(year);
        studentReference.orderBy("roll_num", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots)
                {
                    Students student=snapshot.toObject(Students.class);
                    if(snapshot.exists())
                    {
                        String name=student.getName();
                        int roll_num=student.getRoll_num();
                        list.add(new Students(name,roll_num));
                    }

                }
                swipeAdapter=new SwipeAdapter(list,SwipeList.this);
                recyclerView.setAdapter(swipeAdapter);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(SwipeList.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                Log.i("onFailure:",e.getLocalizedMessage());
            }
        });

    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, @NonNull  RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull  RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();
            switch (direction)
            {
                case ItemTouchHelper.LEFT:
                {
                    tempName=list.get(position).getName();
                    temp=list.get(position).getRoll_num();
                    list.remove(position);
                    swipeAdapter.notifyItemRemoved(position);
                    attendanceList.add(new AttendanceData(tempName,temp,"Absent"));
                    Snackbar.make(recyclerView,tempName+ ": Absent", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    attendanceList.remove(attendanceList.size()-1);
                                    list.add(position,new Students(tempName,temp));
                                    swipeAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    if(list.size()==0)
                    {
                        sendData.setVisibility(View.VISIBLE);
                    }
                    break;
                }
                case ItemTouchHelper.RIGHT:
                {
                    tempName=list.get(position).getName();
                    temp=list.get(position).getRoll_num();
                    list.remove(position);
                    swipeAdapter.notifyItemRemoved(position);
                    attendanceList.add(new AttendanceData(tempName,temp,"Present"));
                    Snackbar.make(recyclerView,tempName+": Present", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    attendanceList.remove(attendanceList.size()-1);
                                    list.add(position,new Students(tempName,temp));
                                    swipeAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    if(list.size()==0)
                    {
                        sendData.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
        }
    };
}