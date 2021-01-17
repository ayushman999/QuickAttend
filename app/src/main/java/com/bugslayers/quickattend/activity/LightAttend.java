package com.bugslayers.quickattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class LightAttend extends AppCompatActivity {
    Button createRoomBtn;
    FirebaseFirestore firestore;
    CollectionReference room;
    EditText roomIdEdit;
    String roomId;
    boolean exist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_attend);
        roomIdEdit=(EditText) findViewById(R.id.room_Id);
        createRoomBtn=(Button) findViewById(R.id.create_room);
        firestore=FirebaseFirestore.getInstance();
        room=firestore.collection("LightAttend");
        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exist=false;
                roomId=roomIdEdit.getText().toString();
                checkForExistingRoom();            }
        });
    }

    private void createRoomFirestore() {
        if(exist)
        {
            Toast.makeText(this, "Room already exists!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            makeRoomOnline();
            HashMap<String,Object> map=new HashMap<>();
            map.put("name","Ayushman");
            map.put("phone_num","9695558135");
            map.put("email","ayushman.ams999@gmail.com");
            room.document(roomId).collection("Attendees").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(LightAttend.this, "Created!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure( Exception e) {
                    Toast.makeText(LightAttend.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
            Intent transfer=new Intent(LightAttend.this,LightAttendRoom.class);
            transfer.putExtra("roomId",roomId);
            startActivity(transfer);
        }

    }

    private void checkForExistingRoom() {
        room.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot snapshot:queryDocumentSnapshots)
                {
                    String id=snapshot.getId();

                    if(id.equals(roomId))
                    {
                        exist=true;

                    }
                }
                createRoomFirestore();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(LightAttend.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void makeRoomOnline() {
        HashMap<String,Object> map=new HashMap<>();
        map.put("validity","online");
        room.document(roomId).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(LightAttend.this, "Your room is online now!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LightAttend.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}