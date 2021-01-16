package com.bugslayers.quickattend.students;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.activity.NoticeListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StudentProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

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
                Toast.makeText(com.bugslayers.quickattend.students.StudentProfile.this,"Sign out!",Toast.LENGTH_SHORT).show();
                Intent transfer=new Intent(StudentProfile.this, StudentLoginActivity.class);
                startActivity(transfer);
                finish() ;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}