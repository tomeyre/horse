package com.example.horseracing.data.horse;

public class RaceStats {

    private String distance;
    private Integer id;

    private static final RaceStats ourInstance = new RaceStats();

    public static RaceStats getInstance() {
        return ourInstance;
    }

    private RaceStats() {
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
