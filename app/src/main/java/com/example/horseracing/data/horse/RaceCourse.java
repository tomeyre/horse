package com.example.horseracing.data.horse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class RaceCourse implements Serializable {

    private String location;
    private Date date;
    private String raceClass;
    private String distance;
    private int totalRiders;
    private String going;
    private boolean isHandicaped;
    private Integer raceReference;
    private ArrayList<RaceParticipant> horsesRacing = new ArrayList<>();

    public RaceCourse(String location, Date date, String raceClass, String distance,
                      int totalRiders, String going, boolean isHandicaped,
                      Integer raceReference) {
        this.location = location;
        this.date = date;
        this.raceClass = raceClass;
        this.distance = distance;
        this.totalRiders = totalRiders;
        this.going = going;
        this.isHandicaped = isHandicaped;
        this.raceReference = raceReference;
    }

    public ArrayList<RaceParticipant> getHorsesRacing() {
        return horsesRacing;
    }

    public void setHorsesRacing(ArrayList<RaceParticipant> horsesRacing) {
        this.horsesRacing = horsesRacing;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(String raceClass) {
        this.raceClass = raceClass;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getTotalRiders() {
        return totalRiders;
    }

    public void setTotalRiders(int totalRiders) {
        this.totalRiders = totalRiders;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public boolean isHandicaped() {
        return isHandicaped;
    }

    public void setHandicaped(boolean handicaped) {
        isHandicaped = handicaped;
    }

    public Integer getRaceReference() {
        return raceReference;
    }

    public void setRaceReference(Integer raceReference) {
        this.raceReference = raceReference;
    }
}
