package com.bugslayers.quickattend.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bugslayers.quickattend.R;
import com.bugslayers.quickattend.model.LightData;

import java.util.ArrayList;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.LightHolder> {
    ArrayList<LightData> list;
    Context context;

    public LightAdapter(ArrayList<LightData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LightHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.light_attend_element,parent,false);
        LightHolder holder=new LightHolder(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder( LightAdapter.LightHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
        holder.phoneNum.setText(list.get(position).getPhone_num());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LightHolder extends RecyclerView.ViewHolder {
        TextView name,email,phoneNum;
        public LightHolder( View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.light_name);
            email=(TextView) itemView.findViewById(R.id.light_email);
            phoneNum=(TextView) itemView.findViewById(R.id.light_phone_num);
        }
    }
}
