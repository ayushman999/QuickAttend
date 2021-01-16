package com.bugslayers.quickattend.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.Admin;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLoginActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b1;
    String admin_email;
    String admin_password;
    private final String password="abcd1234";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference admin = db.collection("Admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        e1=findViewById(R.id.admin_email);
        b1=findViewById(R.id.admin_login);
        e2=findViewById(R.id.admin_password);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_email=e1.getText().toString();
                admin_password=e2.getText().toString();

                enter(v);
            }
        });
    }
private void enter(View view){
admin.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
    String email;

    String name;
    @Override
    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

        DocumentSnapshot documentSnapshot=queryDocumentSnapshots.getDocuments().get(0);
         email=documentSnapshot.getString("email");

         if (admin_email.equals(email)&& admin_password.equals(password)){
             Intent transfer = new Intent(AdminLoginActivity.this, Admin.class);

             startActivity(transfer);

         }
         else {
             Toast.makeText(AdminLoginActivity.this, "invalid id", Toast.LENGTH_SHORT).show();
         }
    }
});
}
}
