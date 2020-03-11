package com.example.horseracing.data.football;

import java.util.ArrayList;
import java.util.HashMap;

public class Rankings {

    private HashMap<String, Rank> ranks = new HashMap<>();

    private static final Rankings ourInstance = new Rankings();

    public static Rankings getInstance() {
        return ourInstance;
    }

    private Rankings() {
    }

    public HashMap<String, Rank> getRanks() {
        return ranks;
    }

    public void setRanks(HashMap<String, Rank> ranks) {
        this.ranks = ranks;
    }
}
