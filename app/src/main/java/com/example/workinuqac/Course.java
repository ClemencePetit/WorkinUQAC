package com.example.workinuqac;

import androidx.annotation.NonNull;

public class Course {
    private String id;
    private String name;
    private String day;
    private String hours;

    public Course(){
        id="";
        name="";
        day="";
        hours="";
    }

    public Course(String id, String name, String day, String hours){
        this.hours=hours;
        this.day=day;
        this.name=name;
        this.id=id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getHours() {
        return hours;
    }

    public String getName() {
        return name;
    }

    public Course duplicate(){
        return new Course(this.id,this.name,this.day,this.hours);
    }
}
