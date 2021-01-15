package com.bugslayers.quickattend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.adapter.AttendanceDataAdapter;
import com.bugslayers.quickattend.model.AttendanceData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceView extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RecyclerView recyclerView;
    AttendanceDataAdapter adapter;
    Button datepicker_btn;
    TextView dateinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Attendance");
        setContentView(R.layout.activity_attendance_view);

        dateinput = findViewById(R.id.datetextView);
        datepicker_btn = (Button) findViewById(R.id.datepicker_btn);

        recyclerView = findViewById(R.id.attendance_records);

        setRecyclerView();

        datepicker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
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
       String date = i+"-"+ ((i1+1)<10 ? "0"+(i1+1):(i1+1)) + "-" +(i2<10 ? "0"+i2:i2);
       dateinput.setText(date);

    }
    private void setRecyclerView() {

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttendanceDataAdapter(this,getList());
        recyclerView.setAdapter(adapter);

    }

    private List<AttendanceData> getList() {
        List<AttendanceData> list = new ArrayList<>();
        list.add(new AttendanceData("User1",11101,"P"));
        list.add(new AttendanceData("User2",11102,"A"));
        list.add(new AttendanceData("User3",11103,"A"));
        list.add(new AttendanceData("User4",11104,"P"));
        return list;

    }
}

