package com.example.horseracing.data.football;

public class Rank {

    private Integer position;
    private String name;
    private Integer played;
    private Integer wins;
    private Integer draws;
    private Integer loses;
    private Integer goalsScored;
    private Integer goalsConceded;
    private Integer goalDif;
    private Integer point;

    public Rank(Integer position, String name, Integer played, Integer wins, Integer draws, Integer loses, Integer goalsScored, Integer goalsConceded, Integer goalDif, Integer point) {
        this.position = position;
        this.name = name;
        this.played = played;
        this.wins = wins;
        this.draws = draws;
        this.loses = loses;
        this.goalsScored = goalsScored;
        this.goalsConceded = goalsConceded;
        this.goalDif = goalDif;
        this.point = point;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPlayed() {
        return played;
    }

    public void setPlayed(Integer played) {
        this.played = played;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    public Integer getLoses() {
        return loses;
    }

    public void setLoses(Integer loses) {
        this.loses = loses;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(Integer goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public Integer getGoalDif() {
        return goalDif;
    }

    public void setGoalDif(Integer goalDif) {
        this.goalDif = goalDif;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        sb.append("Position: " + position + "\nPlayed: " + played + "\nWon: " + wins + "\nLose: " + loses + "\nDraw: " + draws);
        return sb.toString();
    }
}
