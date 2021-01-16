package com.bugslayers.quickattend.model;

import java.util.ArrayList;

public class AttendanceData implements Comparable<AttendanceData> {
    private int roll_num;
    private String status;
    private String name;
    private ArrayList<AttendanceData> list;


    public AttendanceData(String name,int roll_num, String status) {
        this.roll_num = roll_num;
        this.status = status;
        this.name=name;
    }

    public int getRoll_num() {
        return roll_num;
    }

    public void setRoll_num(int roll_num) {
        this.roll_num = roll_num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<AttendanceData> getList() {
        return list;
    }

    public void setList(ArrayList<AttendanceData> list) {
        this.list = list;
    }

    @Override
    public int compareTo(AttendanceData o) {
        int rollnum=o.getRoll_num();
        return this.roll_num-rollnum;
    }
}
