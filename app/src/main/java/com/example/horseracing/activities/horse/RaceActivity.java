package com.example.horseracing.activities.horse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.horseracing.Adapters.horse.RaceAdapter;
import com.example.horseracing.asyncTasks.horse.HorseRecordTask;
import com.example.horseracing.asyncTasks.horse.JockeyRecordTask;
import com.example.horseracing.asyncTasks.horse.RaceParticipantTask;
import com.example.horseracing.R;
import com.example.horseracing.asyncTasks.horse.TrainerRecordTask;
import com.example.horseracing.data.horse.DateOfSelectedRace;
import com.example.horseracing.data.horse.EventCorrectness;
import com.example.horseracing.data.horse.RaceStats;
import com.example.horseracing.data.horse.Record;
import com.example.horseracing.data.horse.RaceParticipant;
import com.example.horseracing.util.horse.CalculationUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class RaceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private Integer id;
    private Integer recordsObtained;
    private ArrayList<RaceParticipant> raceParticipants = new ArrayList<>();
    private CalculationUtil calculationUtil = new CalculationUtil();
    private TextView racePercentage;
    private DateOfSelectedRace dateOfSelectedRace = DateOfSelectedRace.getInstance();
    private EventCorrectness eventCorrectness = EventCorrectness.getInstance();
    private RaceStats raceStats = RaceStats.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getIntExtra("raceId", 0);
        setContentView(R.layout.activity_race);

        racePercentage = findViewById(R.id.racePercentage);

        recyclerView = findViewById(R.id.race_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if(id != null) {
            new RaceParticipantTask(id, this).execute();
        }

    }

    public void checkAllHorseResults(ArrayList<RaceParticipant> raceParticipants){
        this.raceParticipants = raceParticipants;
        recordsObtained = 0;
        for(RaceParticipant participant : raceParticipants){
            if(participant.getHorse() != null && participant.getHorse().getId() != null) {
                new HorseRecordTask(participant.getHorse().getId(), this).execute();
                new JockeyRecordTask(participant.getJockey().getId(), this).execute();
                new TrainerRecordTask(participant.getTrainer().getId(), this).execute();
            }else{
                new HorseRecordTask(0, this).execute();
                new JockeyRecordTask(0, this).execute();
                new TrainerRecordTask(0, this).execute();
            }
        }

    }

    synchronized public void addHorseRecord(final ArrayList<Record> records, Integer id){
        for(RaceParticipant participant : raceParticipants){
            if(participant.getHorse() != null && participant.getHorse().getId().equals(id)){
                participant.getHorse().setRecords(records);
            }
        }
        recordsObtained++;
        finishedObtainingRecords();
    }

    synchronized public void addJockeyRecord(final ArrayList<Record> records, Integer id){
        for(RaceParticipant participant : raceParticipants){
            if(participant.getHorse() != null && participant.getJockey().getId().equals(id)){
                participant.getJockey().setRecords(records);
            }
        }
        recordsObtained++;
        finishedObtainingRecords();
    }

    synchronized public void addTrainerRecord(final ArrayList<Record> records, Integer id){
        for(RaceParticipant participant : raceParticipants){
            if(participant.getHorse() != null && participant.getTrainer().getId().equals(id)){
                participant.getTrainer().setRecords(records);
            }
        }
        recordsObtained++;
        finishedObtainingRecords();
    }

    private void finishedObtainingRecords(){
        if(recordsObtained == raceParticipants.size() * 3){
            removeFutureRacesForAcurateResults();
            calculateWinner();
        }
    }

    private void removeFutureRacesForAcurateResults() {
        for (RaceParticipant participant : raceParticipants) {
            if (participant.getHorse() != null && participant.getHorse().getRecords() != null && !participant.getHorse().getRecords().isEmpty()) {
                for (int i = participant.getHorse().getRecords().size() - 1; i >= 0; i--) {
                    Date dateOfRace = participant.getHorse().getRecords().get(i).getDate();
                    Date dateOfRaceSelected = dateOfSelectedRace.getDate();
                    if (dateOfRace.getTime() > dateOfRaceSelected.getTime()) {
                        participant.getHorse().getRecords().remove(i);
                    }
                }
            }
        }
    }

    private void calculateWinner(){
        for(RaceParticipant participant : raceParticipants){
            if(participant.getHorse() != null && participant.getHorse().isRunner()) {
                participant.setChanceAtWinning(calculateHorsesChance(participant));
            }else{
                participant.setChanceAtWinning(0.0);
            }
        }
        raceParticipants.sort(participantComparator);
        for(int i = 0; i < raceParticipants.size(); i++){
            if(raceParticipants.get(i).getHorse() != null) {
                int finished = 1;
                raceParticipants.get(i).getHorse().setExpectedPosition(finished + i);
            }
        }
        updateRaceParticipantCards();
    }

    Comparator<RaceParticipant> participantComparator = new Comparator<RaceParticipant>() {
        @Override
        public int compare(RaceParticipant e1, RaceParticipant e2) {
            return e2.getChanceAtWinning().compareTo(e1.getChanceAtWinning());
        }
    };

    private void updateRaceParticipantCards(){
        mAdapter = new RaceAdapter(raceParticipants, this);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
        if(eventCorrectness.getRaces().isEmpty()){
            eventCorrectness.addRaces(raceStats.getId());
            int temp = eventCorrectness.getRacesChecked();
            eventCorrectness.setRacesChecked(++temp);
            temp = guessedWinnerCorrectly();
            temp += eventCorrectness.getGuessedWinner();
            eventCorrectness.setGuessedWinner(temp);
        } else if(!eventCorrectness.raceRegistered(raceStats.getId())){
            eventCorrectness.addRaces(raceStats.getId());
            int temp = eventCorrectness.getRacesChecked();
            eventCorrectness.setRacesChecked(++temp);
            temp = guessedWinnerCorrectly();
            temp += eventCorrectness.getGuessedWinner();
            eventCorrectness.setGuessedWinner(temp);
        }
        racePercentage.setText("races checked: " + eventCorrectness.getRacesChecked() + "\n" +
                "guessed the winner " + eventCorrectness.getGuessedWinner() + " times\n");
    }

    private Integer guessedWinnerCorrectly(){
        for(RaceParticipant participant : raceParticipants){
            if (participant.getHorse() != null && participant.getHorse().getRecords() != null && !participant.getHorse().getRecords().isEmpty()) {
                if (participant.getHorse().getExpectedPosition() == 1 && participant.getHorse().getPosition() == participant.getHorse().getExpectedPosition()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private Double calculateHorsesChance(RaceParticipant participant){
        Double score = 0.0;
        System.out.println("-----------------------------------");
        score += calculationUtil.hasTheHorseWonAnyOfTheirLastRaces(participant);
        score += calculationUtil.hasTheHorseFinishedSecondInTheirLastRaces(participant);
        score += calculationUtil.doesTheJockeyOftenWin(participant);
        score += calculationUtil.doesTheTrainerTrainWinningHorses(participant);
        score += calculationUtil.hasThisJokeyWonWithThisHorseBefore(participant);
        score += calculationUtil.hasTheHorseRacedInThisClassBeforeAndWon(participant);
        score += calculationUtil.hasTheHorseRacedWithThisWeightBeforeAndWon(participant);
//        score += calculationUtil.isTheHorseReliable(participant);
//        score += calculationUtil.isTheJockeyReliable(participant);
        score += calculationUtil.isTheHorseComfortableAtThisDistance(participant);
        score += calculationUtil.doesTheHorseFavourThisGround(participant);
        score += calculationUtil.hasTheLast6RacesBeenRecent(participant);
        score += calculationUtil.hasTheHorseMovedClass(participant);
        score += calculationUtil.horseRatingBonus(raceParticipants,participant);
        score -= calculationUtil.horseOdds(participant);
        System.out.println(participant.getHorse().getName() + " Overall score " + score + "-----------------------------------");
        return score;
    }
}
