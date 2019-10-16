package com.example.horseracing.data.horse;

import java.util.ArrayList;

public class EventCorrectness {

    private Integer guessedWinner = 0;
    private Integer racesChecked = 0;
    private ArrayList<Integer> races = new ArrayList<>();

    private static final EventCorrectness ourInstance = new EventCorrectness();

    public static EventCorrectness getInstance() {
        return ourInstance;
    }

    private EventCorrectness() {
    }

    public void reset(){
        races = new ArrayList<>();
        guessedWinner = 0;
        racesChecked = 0;
    }

    public Integer getGuessedWinner() {
        return guessedWinner;
    }

    public void setGuessedWinner(Integer guessedWinner) {
        this.guessedWinner = guessedWinner;
    }

    public Integer getRacesChecked() {
        return racesChecked;
    }

    public void setRacesChecked(Integer racesChecked) {
        this.racesChecked = racesChecked;
    }

    public ArrayList<Integer> getRaces() {
        return races;
    }

    public void addRaces(Integer id) {
        races.add(id);
    }


    public void setRaces(ArrayList<Integer> races) {
        this.races = races;
    }

    public boolean raceRegistered(Integer id){
        return races.contains(id);
    }
}
