package com.bugslayers.quickattend.students;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bugslayers.quickattend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class StudentRegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText brnach;
    EditText name;
    String emailText;
    String passwordText;
    String brnachText;
    String nameText;
    Button register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        mAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextTextPassword);
        brnach = (EditText) findViewById(R.id.editTextBranch);
        name = (EditText) findViewById(R.id.editTextFullName);

        register = (Button)findViewById(R.id.registerUser);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerStudent();
            }
        });
    }

    private void registerStudent() {
        emailText = email.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        nameText = name.getText().toString().trim();
        brnachText = brnach.getText().toString().trim();
        if(emailText.isEmpty())
        {
            Toast.makeText(this,"Enter your email!",Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            Toast.makeText(this,"Enter a valid email address!",Toast.LENGTH_SHORT).show();
        }
        else if(passwordText.isEmpty())
        {
            Toast.makeText(this,"Enter password",Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(this, "okk", Toast.LENGTH_SHORT).show();

            final ProgressDialog progressDialog = new ProgressDialog(StudentRegisterActivity.this);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Registering the student...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //User user = new User(fullName, birthDate , email);
                                Map<String,String> student= new HashMap<>();
                                student.put("name",nameText);
                                student.put("email",emailText);
                                student.put("branch",brnachText);

                                FirebaseDatabase.getInstance().getReference("Students")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(StudentRegisterActivity.this,"User has been successfully registered",Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                            Intent intent=new Intent(StudentRegisterActivity.this,  StudentLoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(StudentRegisterActivity.this,"Failed to register! Try Again!!",Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(StudentRegisterActivity.this,"Failed to register! Try Again!!",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

        }
    }

    private void createAlert(String heading, String message, String possitive)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(heading)
                .setMessage(message)
                .setPositiveButton(possitive,null)
                .create().show();
    }

}