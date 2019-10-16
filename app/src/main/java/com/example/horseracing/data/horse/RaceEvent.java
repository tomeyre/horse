package com.example.horseracing.data.horse;

import java.util.ArrayList;

public class RaceEvent {

    private String location;
    private Integer meetingId;
    private String status;
    private Integer courseId;
    private String country;
    private String going;
    private String surface;
    private String weather;
    private ArrayList<RaceCourse> races;

    public RaceEvent(String location, Integer meetingId, String status, Integer courseId, String country,
                     String going, String surface, String weather, ArrayList<RaceCourse> races){
        this.location = location;
        this.meetingId = meetingId;
        this.status = status;
        this.courseId = courseId;
        this.country = country;
        this.going = going;
        this.surface = surface;
        this.weather = weather;
        this.races = races;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public ArrayList<RaceCourse> getRaces() {
        return races;
    }

    public void setRaces(ArrayList<RaceCourse> races) {
        this.races = races;
    }
}
