package com.bugslayers.quickattend.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.AttendanceDataAdapter;
import com.bugslayers.quickattend.model.AttendanceData;
import com.bugslayers.quickattend.model.AttendanceList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AttendanceView extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RecyclerView recyclerView;
    AttendanceDataAdapter adapter;
    Button datepicker_btn;
    Button getAttendance_btn;
    Button getExcel_btn;
    TextView dateinput;
    String year,branch,teacher,date;
    FirebaseFirestore firestore;
    DocumentReference attendanceRef;
    List<AttendanceData> list=new ArrayList<>();
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Attendance");
        setContentView(R.layout.activity_attendance_view);
        Intent data=getIntent();
        year=data.getStringExtra("year");
        branch=data.getStringExtra("branch");
        teacher=data.getStringExtra("teacher");
        firestore=FirebaseFirestore.getInstance();
        dateinput = findViewById(R.id.datetextView);
        datepicker_btn = (Button) findViewById(R.id.datepicker_btn);
        getAttendance_btn=(Button) findViewById(R.id.get_attendanceData);
        getExcel_btn=(Button) findViewById(R.id.get_excel); 
        recyclerView = findViewById(R.id.attendance_records);

        datepicker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        getAttendance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAttendanceData();
            }
        });
        getExcel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getExcelSheet(list);
            }
        });

    }

    private void getExcelSheet(List<AttendanceData> list) {
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
        roll.setCellValue(new HSSFRichTextString("Roll number:"));
        HSSFCell status=rowA.createCell(2);
        status.setCellValue(new HSSFRichTextString("status:"));
        for(int i=0;i<list.size();i++)
        {
            HSSFRow newRow=firstSheet.createRow(i+1);
            HSSFCell nameCell= newRow.createCell(0);
            nameCell.setCellValue(new HSSFRichTextString(list.get(i).getName()));
            HSSFCell rollCell= newRow.createCell(1);
            rollCell.setCellValue(new HSSFRichTextString(list.get(i).getRoll_num()+""));
            HSSFCell statusCell= newRow.createCell(2);
            statusCell.setCellValue(new HSSFRichTextString(list.get(i).getStatus()));
        }
        FileOutputStream fos = null;
        try {
            File file ;
            file = new File(getExternalFilesDir(null), branch+date + ".xls");
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
            Toast.makeText(AttendanceView.this, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
        }
    }

    private void getAttendanceData() {
        attendanceRef=firestore.collection(branch).document(year).collection(date).document(teacher);
        attendanceRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                AttendanceList data=documentSnapshot.toObject(AttendanceList.class);
                ArrayList<String> name=data.getName();
                ArrayList<Integer> roll_num=data.getRoll_num();
                ArrayList<String> status=data.getStatus();
                setRecyclerView(name,roll_num,status);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception e) {
                Toast.makeText(AttendanceView.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView(ArrayList<String> name, ArrayList<Integer> roll_num, ArrayList<String> status) {
        for(int i=0;i<name.size();i++)
        {
            list.add(new AttendanceData(name.get(i),roll_num.get(i),status.get(i)));
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttendanceDataAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //String strDate = format.format(Calendar.getInstance().getTime());
       date = i+"-"+ ((i1+1)<10 ? "0"+(i1+1):(i1+1)) + "-" +(i2<10 ? "0"+i2:i2);
       dateinput.setText(date);

    }


}

