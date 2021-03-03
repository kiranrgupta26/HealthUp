package com.example.health_up;

import java.util.ArrayList;
import java.util.List;

public class Appointments {
    int doctorid;
    ArrayList<String> days = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();
    public int getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(int doctorid) {
        this.doctorid = doctorid;
    }

    public void addDays(String day)
    {
        days.add(day);
    }
    public void addTimes(String time)
    {
        times.add(time);
    }
    public ArrayList<String> getDays() {
        return days;
    }

    public ArrayList<String> getTimes() {
        return times;
    }




}
