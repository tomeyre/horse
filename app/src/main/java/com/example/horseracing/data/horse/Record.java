package com.example.horseracing.data.horse;

import java.util.Date;

public class Record {

    private Date date;
    private String name;
    private String course;
    private String distance;
    private String runType;
    private String weight;
    private Integer position;
    private Integer runnerCount;
    private Integer bha;
    private Integer raceClass;
    private String going;
    private String odds;
    private Integer raceId;

    public Integer getRaceId() {
        return raceId;
    }

    public void setRaceId(Integer raceId) {
        this.raceId = raceId;
    }

    public Record(Date date, String name, String course, String distance,
                  String runType, String weight, Integer position,
                  Integer runnerCount, Integer bha, Integer raceClass,
                  String going, String odds, Integer raceId) {
        this.date = date;
        this.name = name;
        this.course = course;
        this.distance = distance;
        this.runType = runType;
        this.weight = weight;
        this.position = position;
        this.runnerCount = runnerCount;
        this.bha = bha;
        this.raceClass = raceClass;
        this.going = going;
        this.odds = odds;
        this.raceId = raceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRunType() {
        return runType;
    }

    public void setRunType(String runType) {
        this.runType = runType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getRunnerCount() {
        return runnerCount;
    }

    public void setRunnerCount(Integer runnerCount) {
        this.runnerCount = runnerCount;
    }

    public Integer getBha() {
        return bha;
    }

    public void setBha(Integer bha) {
        this.bha = bha;
    }

    public Integer getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(Integer raceClass) {
        this.raceClass = raceClass;
    }

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }
}
