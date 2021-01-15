package com.bugslayers.quickattend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.AttendanceData;

import java.util.List;

public class AttendanceDataAdapter extends RecyclerView.Adapter<AttendanceDataAdapter.ViewHolder> {


    Context context;
    List<AttendanceData> attendanceList;

    public AttendanceDataAdapter(Context context, List<AttendanceData> attendanceList) {
        this.context = context;
        this.attendanceList = attendanceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(attendanceList != null && attendanceList.size()>0){
            AttendanceData model = attendanceList.get(position);
            holder.name.setText(model.getName());
            holder.roll.setText(model.getRoll_num()+"");
            holder.status.setText(model.getStatus());
        }
        else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,roll,status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTextView);
            roll = itemView.findViewById(R.id.rollnoTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }
    }
}
