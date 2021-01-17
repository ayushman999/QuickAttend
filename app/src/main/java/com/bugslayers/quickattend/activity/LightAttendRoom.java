package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.LightAdapter;
import com.bugslayers.quickattend.model.LightData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LightAttendRoom extends AppCompatActivity {
    String roomId;
    FirebaseFirestore firestore;
    CollectionReference room;
    TextView roomDisplay;
    LightAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<LightData> attendees=new ArrayList<>();
    RecyclerView attendeeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_attend_room);
        Intent data=getIntent();
        roomDisplay=(TextView) findViewById(R.id.room_id_list);
        roomId=data.getStringExtra("roomId");
        firestore=FirebaseFirestore.getInstance();
        room=firestore.collection("LightAttend").document(roomId).collection("Attendees");
        attendeeList=(RecyclerView) findViewById(R.id.room_attendees);
        layoutManager=new LinearLayoutManager(this);
        attendeeList.setLayoutManager(layoutManager);
        adapter=new LightAdapter(attendees,this);
        attendeeList.setAdapter(adapter);
        roomDisplay.setText("Room ID:"+roomId);

    }

    @Override
    protected void onStart() {
        super.onStart();
        room.addSnapshotListener(this,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot value,  FirebaseFirestoreException error) {
                if(error!=null)
                {
                    return;
                }
                attendees.clear();
                for(QueryDocumentSnapshot snapshot:value)
                {
                    LightData data=snapshot.toObject(LightData.class);
                    String name=data.getName();
                    String pone_num=data.getPhone_num();
                    String email=data.getEmail();
                    attendees.add(new LightData(name,pone_num,email));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}