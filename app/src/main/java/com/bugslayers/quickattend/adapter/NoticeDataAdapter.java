
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
import com.bugslayers.quickattend.model.NoticeData;

import java.util.List;

public class NoticeDataAdapter extends RecyclerView.Adapter<NoticeDataAdapter.ViewHolder> {


    Context context;
    List<NoticeData> noticeList;

    public NoticeDataAdapter(Context context, List<NoticeData> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(noticeList != null && noticeList.size()>0){
            NoticeData model = noticeList.get(position);
            holder.title.setText(model.getTitle());
            holder.description.setText(model.getDescription());
            holder.date.setText(model.getDate());
            //holder.teacher.setText(model.getTeacher());
        }
        else{
            return;
        }
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,date,teacher;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.notice_title);
            description = itemView.findViewById(R.id.notice_description);
            date = itemView.findViewById(R.id.notice_date);
            //teacher = itemView.findViewById(R.id.notice_teacher);
        }
    }
}
