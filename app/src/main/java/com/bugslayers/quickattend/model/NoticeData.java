package com.bugslayers.quickattend.model;

public class NoticeData {
    String date;
    String title;
    String description;
    String teacher;

    public NoticeData() {
    }

    public NoticeData(String date, String title, String description, String teacher) {
        this.date = date;
        this.title = title;
        this.description = description;
        this.teacher = teacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
