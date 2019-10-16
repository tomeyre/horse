package com.example.horseracing.data.horse;

public class RaceParticipant {

    private Horse horse;
    private Jockey jockey;
    private Trainer trainer;
    private Double chanceAtWinning;

    public Double getChanceAtWinning() {
        return chanceAtWinning;
    }

    public void setChanceAtWinning(Double chanceAtWinning) {
        this.chanceAtWinning = chanceAtWinning;
    }

    public RaceParticipant(Horse horse, Jockey jockey, Trainer trainer) {
        this.horse = horse;
        this.jockey = jockey;
        this.trainer = trainer;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Jockey getJockey() {
        return jockey;
    }

    public void setJockey(Jockey jockey) {
        this.jockey = jockey;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}
