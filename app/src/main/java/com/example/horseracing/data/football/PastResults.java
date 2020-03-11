package com.example.horseracing.data.football;

import java.util.ArrayList;
import java.util.HashMap;

public class PastResults {

    private HashMap<String, ArrayList<MatchResult>> pastResults = new HashMap<>();

    private static final PastResults ourInstance = new PastResults();

    public static PastResults getInstance() {
        return ourInstance;
    }

    private PastResults() {
    }

    public HashMap<String, ArrayList<MatchResult>> getPastResults() {
        return pastResults;
    }

    public void setPastResults(HashMap<String, ArrayList<MatchResult>> pastResults) {
        this.pastResults = pastResults;
    }
}
