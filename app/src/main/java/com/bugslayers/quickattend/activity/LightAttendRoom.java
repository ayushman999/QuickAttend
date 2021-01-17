package com.bugslayers.quickattend.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.LightAdapter;
import com.bugslayers.quickattend.model.LightData;
import com.bugslayers.quickattend.students.StudentLoginActivity;
import com.bugslayers.quickattend.students.StudentProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.light_excel, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.create_light_excel:
            {
                createExcel();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void createExcel() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet No 1");
        HSSFRow rowA = firstSheet.createRow(0);
        HSSFCell cellA = rowA.createCell(0);
        cellA.setCellValue(new HSSFRichTextString("Name:"));
        HSSFCell roll=rowA.createCell(1);
        roll.setCellValue(new HSSFRichTextString("email:"));
        HSSFCell status=rowA.createCell(2);
        status.setCellValue(new HSSFRichTextString("Phone Number:"));
        FileOutputStream fos = null;
        for(int i=0;i<attendees.size();i++)
        {
            HSSFRow newRow=firstSheet.createRow(i+1);
            HSSFCell nameCell= newRow.createCell(0);
            nameCell.setCellValue(new HSSFRichTextString(attendees.get(i).getName()));
            HSSFCell rollCell= newRow.createCell(1);
            rollCell.setCellValue(new HSSFRichTextString(attendees.get(i).getEmail()+""));
            HSSFCell statusCell= newRow.createCell(2);
            statusCell.setCellValue(new HSSFRichTextString(attendees.get(i).getPhone_num()));
        }

        try {
            File file ;
            file = new File(getExternalFilesDir(null), roomId + ".xls");
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(LightAttendRoom.this, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
        }
    }

}