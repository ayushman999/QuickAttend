package com.bugslayers.quickattend.model;

import java.util.ArrayList;

public class AttendanceList
{
    private ArrayList<String> name;
    private ArrayList<Integer> roll_num;
    private ArrayList<String> status;

    public AttendanceList() {
    }

    public AttendanceList(ArrayList<String> name, ArrayList<Integer> roll_num, ArrayList<String> status) {
        this.name = name;
        this.roll_num = roll_num;
        this.status = status;
    }

    public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    public ArrayList<Integer> getRoll_num() {
        return roll_num;
    }

    public void setRoll_num(ArrayList<Integer> roll_num) {
        this.roll_num = roll_num;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }
}