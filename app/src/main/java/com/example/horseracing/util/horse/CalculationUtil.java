package com.example.horseracing.util.horse;

import android.util.Log;

import com.example.horseracing.data.horse.RaceStats;
import com.example.horseracing.data.horse.Record;
import com.example.horseracing.data.horse.RaceParticipant;

import java.util.ArrayList;
import java.util.Date;

public class CalculationUtil {

    private RaceStats raceStats = RaceStats.getInstance();

    public Double hasTheHorseWonAnyOfTheirLastRaces(RaceParticipant participant){
        Log.i("",participant.getHorse().getName());
        Double timesFinishedFirst = 0.0;
        for(int i = 0; i < participant.getHorse().getRecords().size(); i++){
            Log.i("","Date: " + participant.getHorse().getRecords().get(i).getDate() + " Position: " + participant.getHorse().getRecords().get(i).getPosition());
            if(participant.getHorse().getRecords().get(i).getPosition() != null && participant.getHorse().getRecords().get(i).getPosition() == 1){
                if(i == 0){
                    timesFinishedFirst += 3.0;
                }else if(i == 1 || i == 2){
                    timesFinishedFirst += 2.0;
                }else {
                    timesFinishedFirst++;
                }
            }
        }
        Log.i("","Won races: " + timesFinishedFirst);
        return timesFinishedFirst;
    }

    public Double hasTheHorseFinishedSecondInTheirLastRaces(RaceParticipant participant){
        Double finishedSecond = 0.0;
        for(int i = 0; i < participant.getHorse().getRecords().size(); i++){
            if(participant.getHorse().getRecords().get(i).getPosition() != null && participant.getHorse().getRecords().get(i).getPosition() == 2){
                finishedSecond++;
            }
        }
        Log.i("","Second place races: " + finishedSecond);
        return finishedSecond;
    }

    public Double doesTheJockeyOftenWin(RaceParticipant participant){
        Double timesFinishedFirst = 0.0;
        for(int i = 0; i < participant.getJockey().getRecords().size(); i++){
            if(participant.getJockey().getRecords().get(i).getPosition() != null && participant.getJockey().getRecords().get(i).getPosition() == 1){
                if(i == 0){
                    timesFinishedFirst += 3.0;
                }else if(i == 1 || i == 2){
                    timesFinishedFirst += 2.0;
                }
                else {
                    timesFinishedFirst++;
                }
            }
        }
        Log.i("","Jockey wins: " + timesFinishedFirst);
        return timesFinishedFirst;
    }

    public Double doesTheTrainerTrainWinningHorses(RaceParticipant participant){
        Double timesFinishedFirst = 0.0;
        for(int i = 0; i < participant.getTrainer().getRecords().size(); i++){
            if(participant.getTrainer().getRecords().get(i).getPosition() != null && participant.getTrainer().getRecords().get(i).getPosition() == 1){
                timesFinishedFirst++;
            }
        }
        Log.i("","Trainer wins: " + timesFinishedFirst);
        return timesFinishedFirst;
    }

    public Double hasThisJokeyWonWithThisHorseBefore(RaceParticipant participant){
        for(int i = 0; i < participant.getJockey().getRecords().size(); i++){
            if(participant.getHorse().getName().equalsIgnoreCase(participant.getJockey().getRecords().get(i).getName())){
                if(participant.getJockey().getRecords().get(i).getPosition() != null && participant.getJockey().getRecords().get(i).getPosition() == 1){
                    Log.i("","Jockey win with horse: " + 3);
                    return 3.0;
                }
                if(participant.getJockey().getRecords().get(i).getPosition() != null &&
                        participant.getJockey().getRecords().get(i).getPosition() <= 3 &&
                        participant.getJockey().getRecords().get(i).getPosition() >= 1) {
                    Log.i("","Jockey win with horse: " + 2);
                    return 2.0;
                }
                Log.i("","Jockey win with horse: " + 1);
                return 1.0;
            }
        }
        Log.i("","Jockey win with horse: " + 0);
        return 0.0;
    }

    public Double hasTheHorseRacedInThisClassBeforeAndWon(RaceParticipant participant) {
        boolean hasRacedAtThisClass = false;
        boolean top3 = false;
        for (int i = 0; i < participant.getJockey().getRecords().size(); i++) {
            if (participant.getJockey().getRecords().get(i).getRaceClass().equals(participant.getHorse().getRaceClass())) {
                hasRacedAtThisClass = true;
                if (participant.getJockey().getRecords().get(i).getPosition() == 1) {
                    Log.i("","Horse in class win: " + 3);
                    return 3.0;
                } else if (participant.getJockey().getRecords().get(i).getPosition() > 1 && participant.getJockey().getRecords().get(i).getPosition() <= 3) {
                    top3 = true;
                }
            }
        }
        if (top3) {
            Log.i("","Horse in class win: " + 2);
            return 2.0;
        } else if (hasRacedAtThisClass) {
            Log.i("","Horse in class win: " + 1);
            return 1.0;
        }
        Log.i("","Horse in class win: " + 0);
        return 0.0;
    }

    public Double hasTheHorseRacedWithThisWeightBeforeAndWon(RaceParticipant participant){
        boolean hasRacedAtThisWeight = false;
        boolean top3 = false;
        int count = 0;
        for(Record record : participant.getHorse().getRecords()){
            if(count < 5) {
                if (record.getWeight().equals(participant.getHorse().getHandicap())) {
                    hasRacedAtThisWeight = true;
                    if (record.getPosition() == 1) {
                        Log.i("","Horse with weight win: " + 3);
                        return 3.0;
                    } else if (record.getPosition() > 1 && record.getPosition() <= 3) {
                        top3 = true;
                    }
                }
            }else{break;}
            count++;
        }
        if(top3){
            Log.i("","Horse with weight win: " + 2);
            return 2.0;
        }else if(hasRacedAtThisWeight){
            Log.i("","Horse with weight win: " + 1);
            return 1.0;
        }
        Log.i("","Horse with weight win: " + 0);
        return 0.0;
    }

    public Double isTheHorseComfortableAtThisDistance(RaceParticipant participant){
        Double count = 0.0;
        for(Record record : participant.getHorse().getRecords()){
            if(record.getDistance().equalsIgnoreCase(raceStats.getDistance())){
                if(record.getPosition() == 1){
                    count += 2.0;
                }
                else if(record.getPosition() == 2){
                    count += 1.0;
                }
            }
        }
        return count;
    }

    public Double isTheHorseReliable(RaceParticipant participant){
        Double racesFinished = 0.0;
        for(int i = 0; i < participant.getHorse().getRecords().size(); i++){
            if(participant.getHorse().getRecords().get(i).getPosition() != null &&
            participant.getHorse().getRecords().get(i).getPosition() > 0){
                racesFinished++;
            }
        }
        if(racesFinished == 0.0){
            Log.i("","Horse reliable: 0.0");
            return racesFinished;
        }
        Log.i("","Horse reliable: " + racesFinished * (100 / participant.getHorse().getRecords().size()));
        return racesFinished * (100 / participant.getHorse().getRecords().size());
    }

    public Double isTheJockeyReliable(RaceParticipant participant){
        Double racesFinished = 0.0;
        for(int i = 0; i < participant.getJockey().getRecords().size(); i++){
            if(participant.getJockey().getRecords().get(i).getPosition() != null &&
                    participant.getJockey().getRecords().get(i).getPosition() > 0){
                racesFinished++;
            }
        }
        if(racesFinished == 0.0){
            Log.i("","Jockey reliable: 0.0");
            return racesFinished;
        }
        Log.i("","Jockey reliable: " + racesFinished * (100 / participant.getJockey().getRecords().size()));
        return racesFinished * (100 / participant.getJockey().getRecords().size());
    }

    public Double doesTheHorseFavourThisGround(RaceParticipant participant){
        Double favoredGround = 0.0;
        if(!participant.getHorse().getGoing().equalsIgnoreCase("")) {
            for (Record record : participant.getHorse().getRecords()){
                if(record.getGoing().equalsIgnoreCase(participant.getHorse().getGoing())){
                    if(record.getPosition() == 1 || record.getPosition() == 2){
                        favoredGround++;
                    }
                }
            }
        }
        Log.i("","Horse favour this ground: " + favoredGround);
        return favoredGround;
    }

    public Double hasTheLast6RacesBeenRecent(RaceParticipant participant){
        long days;
        Double racesOften = 0.0;
        if(participant.getHorse().getRecords().isEmpty()){
            return 0.0;
        }else {
            for(Record record : participant.getHorse().getRecords()) {
                days = new Date().getTime() - record.getDate().getTime() / (1000 * 60 * 60 * 24);
                racesOften += days < 30 ? 1.0 : 0.0;
            }
        }
        return racesOften;
    }

    public Double hasTheHorseMovedClass(RaceParticipant participant){
        if(participant.getHorse().getRecords().isEmpty()){
            return 0.0;
        }
        if((participant.getHorse().getRecords().get(0).getRaceClass() > participant.getHorse().getRaceClass()) && participant.getHorse().getRaceClass() != 0 && participant.getHorse().getRecords().get(0).getRaceClass() != 0){
            return 2.0;
        }
        if((participant.getHorse().getRecords().get(0).getRaceClass() == participant.getHorse().getRaceClass()) && participant.getHorse().getRaceClass() != 0 && participant.getHorse().getRecords().get(0).getRaceClass() != 0){
            return 1.0;
        }
        return 0.0;
    }

    public Double horseRatingBonus(ArrayList<RaceParticipant> participants, RaceParticipant participant){
        Double ratingBonus = 0.0;
        for(int i =0; i < participants.size(); i++){
            if(participant.getHorse().getOfficialRating() >= participants.get(i).getHorse().getOfficialRating()){
                ratingBonus++;
            }
        }
        Log.i("","Horse rating bonus: " + ratingBonus);
        return ratingBonus;
    }

    public Double horseOdds(RaceParticipant participant){
        String[] odds = participant.getHorse().getOdds().split("/");
        if(Character.isDigit(odds[0].charAt(0))) {
            Log.i("", "Horse odds bonus: " + (Double.parseDouble(odds[0]) + 0.0) / (Double.parseDouble(odds[1]) + 0.0));
            return (Double.parseDouble(odds[0]) + 0.0) / (Double.parseDouble(odds[1]) + 0.0);
        }
        return 1.0;
    }

}
