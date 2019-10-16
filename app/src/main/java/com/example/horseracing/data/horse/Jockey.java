package com.example.horseracing.data.horse;

import java.util.ArrayList;

public class Jockey {

    private String name;
    private Integer id;
    private ArrayList<Record> records;

    public Jockey(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public ArrayList<Record> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Record> records) {
        this.records = records;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
