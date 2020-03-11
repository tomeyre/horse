package com.example.horseracing.activities.horse;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.example.horseracing.R;
import com.example.horseracing.asyncTasks.horse.HorseRecordTask;
import com.example.horseracing.asyncTasks.horse.JockeyRecordTask;
import com.example.horseracing.asyncTasks.horse.RaceEventAndCoursesTask;
import com.example.horseracing.asyncTasks.horse.RaceParticipantTask;
import com.example.horseracing.asyncTasks.horse.TrainerRecordTask;
import com.example.horseracing.data.horse.DateOfSelectedRace;
import com.example.horseracing.data.horse.Horse;
import com.example.horseracing.data.horse.RaceCourse;
import com.example.horseracing.data.horse.RaceDay;
import com.example.horseracing.data.horse.RaceEvent;
import com.example.horseracing.data.horse.RaceParticipant;
import com.example.horseracing.data.horse.Record;
import com.example.horseracing.util.horse.CalculationUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ChancesActivity extends AppCompatActivity {

    private final Integer DAYS_TO_GO_BACK = 1;

    public static final String ENGLAND = "England";
    Date thirtyDaysAgo;
    ArrayList<RaceDay> raceDays;
    private Integer recordsObtained;
    private CalculationUtil calculationUtil = new CalculationUtil();
    private Integer total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_course);

        thirtyDaysAgo = getDateThirtyDaysAgo();
        raceDays = new ArrayList<>();
        total = 0;
        populateRaceDays();


    }

    private Date getDateThirtyDaysAgo() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -DAYS_TO_GO_BACK);
        return cal.getTime();
    }

    private void populateRaceDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(thirtyDaysAgo);
        for (int i = 0; i < 1; i++) {
            new RaceEventAndCoursesTask(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this).execute();
            calendar.add(Calendar.DATE, +1);
        }
    }

    public void updateList(Date date, ArrayList<RaceEvent> races) {
        raceDays.add(new RaceDay(date, races));
        if (raceDays.size() == 1) {
            populateAllResultsOfHorsesThatRanInTheLastThirtyDays();
        }
    }

    private void populateAllResultsOfHorsesThatRanInTheLastThirtyDays() {
        for (RaceDay day : raceDays) {
            System.out.println("day " + day.getDate());
            for (RaceEvent event : day.getRaces()) {
                System.out.println("event " + event.getCourseId());
                if (event.getCountry().equalsIgnoreCase(ENGLAND)) {
                    System.out.println("england");
                    for (RaceCourse course : event.getRaces()) {
                        System.out.println(course.getLocation() + ", " + course.getRaceReference());
                        new RaceParticipantTask(course.getRaceReference(), ChancesActivity.this, day.getDate()).execute();
                    }
                }
            }
        }
    }

    synchronized public void checkAllHorseResults(ArrayList<RaceParticipant> raceParticipants,
                                                  Date dateOfRace, Integer raceId) {
        recordsObtained = 0;
        total += raceParticipants.size();
        for (RaceDay day : raceDays) {
            for (RaceEvent event : day.getRaces()) {
                for (RaceCourse race : event.getRaces()) {
                    if (race.getRaceReference() == raceId) {
                        race.setHorsesRacing(raceParticipants);
                    }
                }
            }
        }
        for (RaceParticipant participant : raceParticipants) {
            if (participant.getHorse() != null && participant.getHorse().getId() != null) {
                new HorseRecordTask(participant.getHorse().getId(), this, dateOfRace, raceId).execute();
                new JockeyRecordTask(participant.getJockey().getId(), this, dateOfRace, raceId).execute();
                new TrainerRecordTask(participant.getTrainer().getId(), this, dateOfRace, raceId).execute();
            } else {
                new HorseRecordTask(0, this, null, null).execute();
                new JockeyRecordTask(0, this, null, null).execute();
                new TrainerRecordTask(0, this, null, null).execute();
            }
        }

    }

    synchronized public void addHorseRecord(final ArrayList<Record> records, Integer id, Date dateOfRace, Integer raceId) {
        for (RaceDay day : raceDays) {
            for (RaceEvent event : day.getRaces()) {
                for (RaceCourse race : event.getRaces()) {
                    if (race.getRaceReference() == raceId) {
                        for (RaceParticipant participant : race.getHorsesRacing())
                            if (participant.getHorse() != null && participant.getHorse().getId().equals(id)) {
                                participant.getHorse().setRecords(records);
                            }
                    }
                }
            }
        }
        recordsObtained++;
        finishedObtainingRecords(dateOfRace);
    }

    synchronized public void addJockeyRecord(final ArrayList<Record> records, Integer id, Date dateOfRace, Integer raceId) {
        for (RaceDay day : raceDays) {
            for (RaceEvent event : day.getRaces()) {
                for (RaceCourse race : event.getRaces()) {
                    if (race.getRaceReference() == raceId) {
                        for (RaceParticipant participant : race.getHorsesRacing())
                            if (participant.getHorse() != null && participant.getJockey().getId().equals(id)) {
                                participant.getJockey().setRecords(records);
                            }
                    }
                }
            }
        }
        recordsObtained++;
        finishedObtainingRecords(dateOfRace);
    }

    synchronized public void addTrainerRecord(final ArrayList<Record> records, Integer id, Date dateOfRace, Integer raceId) {
        for (RaceDay day : raceDays) {
            for (RaceEvent event : day.getRaces()) {
                for (RaceCourse race : event.getRaces()) {
                    if (race.getRaceReference() == raceId) {
                        for (RaceParticipant participant : race.getHorsesRacing())
                            if (participant.getHorse() != null && participant.getTrainer().getId().equals(id)) {
                                participant.getTrainer().setRecords(records);
                            }
                    }
                }
            }
        }
        recordsObtained++;
        finishedObtainingRecords(dateOfRace);
    }

    synchronized private void finishedObtainingRecords(Date dateOfRace) {
        Integer correctTotal = 0;
        Integer racesTotal = 0;
        Integer correctEventTotal = 0;
        Integer eventRaceTotal = 0;
        if (recordsObtained == total * 3) {
//            printAllHorses();
            for (RaceDay day : raceDays) {
                for (RaceEvent event : day.getRaces()) {
                    for (RaceCourse race : event.getRaces()) {
                        removeFutureRacesForAcurateResults(dateOfRace, race.getHorsesRacing());
                        if(calculateWinner(race)){
                            correctTotal++;
                            correctEventTotal++;
                        }
                        racesTotal++;
                        eventRaceTotal++;
                    }
                    System.out.println("     -     ");
                    System.out.println("    ---    ");
                    System.out.println("   -   -   ");
                    System.out.println("  -     -   ");
                    System.out.println("  - " + correctEventTotal + "/" + eventRaceTotal + " -   ");
                    System.out.println("  -     -   ");
                    System.out.println("   -   -   ");
                    System.out.println("    ---    ");
                    System.out.println("     -     ");
                    eventRaceTotal = 0;
                    correctEventTotal = 0;
                }
            }
            System.out.println("     -     ");
            System.out.println("    ---    ");
            System.out.println("   -   -   ");
            System.out.println("  -     -   ");
            System.out.println("  - " + correctTotal + "/" + racesTotal + " -   ");
            System.out.println("  -     -   ");
            System.out.println("   -   -   ");
            System.out.println("    ---    ");
            System.out.println("     -     ");
        }
    }

    synchronized private void removeFutureRacesForAcurateResults(Date dateOfSelectedRace, ArrayList<RaceParticipant> raceParticipants) {
        for (RaceParticipant participant : raceParticipants) {
            if (participant.getHorse() != null && participant.getHorse().getRecords() != null && !participant.getHorse().getRecords().isEmpty()) {
                for (int i = participant.getHorse().getRecords().size() - 1; i >= 0; i--) {
                    Date dateOfRace = participant.getHorse().getRecords().get(i).getDate();
                    if (dateOfRace.getTime() > dateOfSelectedRace.getTime()) {
                        participant.getHorse().getRecords().remove(i);
                    }
                }
            }
        }
    }

    private void printAllHorses() {
        for (RaceDay day : raceDays) {
            for (RaceEvent event : day.getRaces()) {
                System.out.println("-------------START OF EVENT----------------");
                System.out.println(event.getLocation());
                System.out.println(event.getGoing());
                System.out.println(event.getRaces().size() + " races");
                for (RaceCourse race : event.getRaces()) {
                    System.out.println("-------------RACE----------------");
                    System.out.println(race.getRaceReference());
                    System.out.println(race.getTotalRiders() + " horses ran out of " + race.getHorsesRacing().size());
                    for (RaceParticipant horse : race.getHorsesRacing()) {
                        System.out.println(horse.getHorse().getName());
                    }
                }
                System.out.println("---------------END OF EVENT-----------------");
            }
        }
//        System.out.println(raceParticipants.size() + " / " + total);

    }

    synchronized private boolean calculateWinner(RaceCourse race) {
//                    createListOfHorsesForRace();
        for (RaceParticipant participant : race.getHorsesRacing()) {
            if (participant.getHorse() != null && participant.getHorse().isRunner()) {
                participant.setChanceAtWinning(calculateHorsesChance(participant, race.getHorsesRacing()));
            } else {
                participant.setChanceAtWinning(0.0);
            }
        }
        race.getHorsesRacing().sort(participantComparator);
        for (int i = 0; i < race.getHorsesRacing().size(); i++) {
            if (race.getHorsesRacing().get(i).getHorse() != null) {
                int finished = 1;
                race.getHorsesRacing().get(i).getHorse().setExpectedPosition(finished + i);
            }
        }
        String winner = "unknown";
        String bookiesWinnerName = "unkown";
        Double bookiesWinnerOdds = 0.0;
        for(int i = 0; i< race.getHorsesRacing().size(); i++){
            if(race.getHorsesRacing().get(i).getHorse().getPosition() == 1){
                winner = race.getHorsesRacing().get(i).getHorse().getName();
            }
            if(bookiesWinnerName.equalsIgnoreCase("unkown")){
                bookiesWinnerName = race.getHorsesRacing().get(i).getHorse().getName();
                bookiesWinnerOdds = calculateOdds(race.getHorsesRacing().get(i).getHorse());
            }else if(calculateOdds(race.getHorsesRacing().get(i).getHorse()) < bookiesWinnerOdds){
                bookiesWinnerName = race.getHorsesRacing().get(i).getHorse().getName();
                bookiesWinnerOdds = calculateOdds(race.getHorsesRacing().get(i).getHorse());
            }
        }


        System.out.println("-----------------------------------");
        String correct = race.getHorsesRacing().get(0).getHorse().getName().equalsIgnoreCase(winner) ? "CORRECT" : winner.equalsIgnoreCase(bookiesWinnerName) ? "BOOKIE CORRECT" : "";
        System.out.println(race.getDate() + " - " + race.getLocation() +
                "\n -- MY GUESS -- " +
                "\n - WINNER : " + race.getHorsesRacing().get(0).getHorse().getName() +
                "\n - SCORE : " + race.getHorsesRacing().get(0).getChanceAtWinning() +
                "\n - SECOND PLACE : " + race.getHorsesRacing().get(1).getHorse().getName() +
                "\n - SCORE : " + race.getHorsesRacing().get(1).getChanceAtWinning());
//                "\n -- BOOKIES GUESS -- " +
//                "\n - WINNER : " + bookiesWinnerName +
//                "\n - ODDS" + bookiesWinnerOdds +
//                "\n - ACTUAL : " + winner + " " + correct);
        System.out.println("-----------------------------------");
        if(correct.equalsIgnoreCase("correct")){return true;}
        return false;
    }

    private Double calculateOdds(Horse horse){
        if(horse.getOdds() != null && !horse.getOdds().equalsIgnoreCase("") && Character.isDigit(horse.getOdds().charAt(0))) {
            String[] odds = horse.getOdds().split("/");
            double a = Double.parseDouble(odds[0]);
            double b = Double.parseDouble(odds[1]);
            return a / b;
        }
        return 0.0;
    }

    Comparator<RaceParticipant> participantComparator = new Comparator<RaceParticipant>() {
        @Override
        public int compare(RaceParticipant e1, RaceParticipant e2) {
            return e2.getChanceAtWinning().compareTo(e1.getChanceAtWinning());
        }
    };

    synchronized private Double calculateHorsesChance(RaceParticipant participant, ArrayList<RaceParticipant> raceParticipants){
        Double score = 0.0;
//        System.out.println("-----------------------------------");
        if(participant.getHorse().getRecords() != null) {
            score += calculationUtil.hasTheHorseWonAnyOfTheirLastRaces(participant);
            score += calculationUtil.hasTheHorseFinishedSecondInTheirLastRaces(participant);
            score += calculationUtil.doesTheJockeyOftenWin(participant);
            score += calculationUtil.doesTheTrainerTrainWinningHorses(participant);
            score += calculationUtil.hasThisJokeyWonWithThisHorseBefore(participant);
            score += calculationUtil.hasTheHorseRacedInThisClassBeforeAndWon(participant);
            score += calculationUtil.hasTheHorseRacedWithThisWeightBeforeAndWon(participant);
//            score += calculationUtil.isTheHorseReliable(participant);
//            score += calculationUtil.isTheJockeyReliable(participant);
            score += calculationUtil.isTheHorseComfortableAtThisDistance(participant);
            score += calculationUtil.doesTheHorseFavourThisGround(participant);
            score += calculationUtil.hasTheLast6RacesBeenRecent(participant);
            score += calculationUtil.hasTheHorseMovedClass(participant);
            score += calculationUtil.horseRatingBonus(raceParticipants, participant);
            score += calculationUtil.horseOdds(participant);
//            System.out.println("-----------------------------------");
        }
//        System.out.println(participant.getHorse().getName() + " Overall score " + score + "-----------------------------------");
        return score;
    }
}
