package com.bugslayers.quickattend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.Students;

import java.util.ArrayList;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.StudentHolder> {
    ArrayList<Students> list;
    Context context;

    public SwipeAdapter(ArrayList<Students> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_element,parent,false);
        StudentHolder studentHolder=new StudentHolder(view);
        return studentHolder;
    }

    @Override
    public void onBindViewHolder( SwipeAdapter.StudentHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.roll_num.setText(list.get(position).getRoll_num()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView roll_num;
        public StudentHolder( View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.student_name_element);
            roll_num=(TextView) itemView.findViewById(R.id.roll_num);
        }
    }
}

