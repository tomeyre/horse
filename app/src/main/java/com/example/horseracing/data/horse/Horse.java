package com.example.horseracing.data.horse;

import java.util.ArrayList;

public class Horse {

    private String name;
    private Integer age;
    private String sex;
    private Integer lastRan;
    private Integer officialRating;
    private String casualty;
    private Integer id;
    private String owner;
    private String trainer;
    private String jockey;
    private Integer position;
    private Integer expectedPosition;
    private String odds;
    private String handicap;
    private String going;
    private Integer raceClass;
    private ArrayList<Record> records;
    private boolean runner;

    public String getGoing() {
        return going;
    }

    public void setGoing(String going) {
        this.going = going;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public Integer getExpectedPosition() {
        return expectedPosition;
    }

    public void setExpectedPosition(Integer expectedPosition) {
        this.expectedPosition = expectedPosition;
    }

    public String getOdds() {
        return odds;
    }

    public void setOdds(String odds) {
        this.odds = odds;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Horse(String name, Integer age, String sex, Integer lastRan,
                 Integer officialRating, Integer id,
                 String owner, String trainer, String jockey,
                 Integer position, String odds, String handicap,
                 Integer raceClass, String going, boolean runner) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.lastRan = lastRan;
        this.officialRating = officialRating;
        this.id = id;
        this.owner = owner;
        this.trainer = trainer;
        this.jockey = jockey;
        this.position = position;
        this.odds = odds;
        this.handicap = handicap;
        this.raceClass = raceClass;
        this.going = going;
        this.runner = runner;
    }

    public String getHandicap() {
        return handicap;
    }

    public void setHandicap(String handicap) {
        this.handicap = handicap;
    }

    public Integer getRaceClass() {
        return raceClass;
    }

    public void setRaceClass(Integer raceClass) {
        this.raceClass = raceClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getLastRan() {
        return lastRan;
    }

    public void setLastRan(Integer lastRan) {
        this.lastRan = lastRan;
    }

    public Integer getOfficialRating() {
        return officialRating;
    }

    public void setOfficialRating(Integer officialRating) {
        this.officialRating = officialRating;
    }

    public String getCasualty() {
        return casualty;
    }

    public void setCasualty(String casualty) {
        this.casualty = casualty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public boolean isRunner() {
        return runner;
    }

    public void setRunner(boolean runner) {
        this.runner = runner;
    }
}
