package com.example.horseracing.data.horse;


import java.util.ArrayList;
import java.util.Date;

public class RaceDay {

    private Date date;
    private ArrayList<RaceEvent> races;

    public RaceDay(Date date, ArrayList<RaceEvent> races) {
        this.date = date;
        this.races = races;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<RaceEvent> getRaces() {
        return races;
    }

    public void setRaces(ArrayList<RaceEvent> races) {
        this.races = races;
    }
}
