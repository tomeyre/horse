package com.example.horseracing.asyncTasks.football;

import android.os.AsyncTask;

import com.example.horseracing.activities.football.FootballMatchesByDayActivity;
import com.example.horseracing.activities.horse.RaceDayEventsActivity;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.Team;
import com.example.horseracing.data.horse.RaceCourse;
import com.example.horseracing.data.horse.RaceEvent;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FootballMatchesByDayTask extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/football/competition/1/matches?from=";
    private static final String to = "&to=";
    private static final String resultsOnly = "&results_only=false";

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer prevYear;
    private Integer prevMonth;
    private Integer prevDay;
    private ArrayList<Match> matches;
    private FootballMatchesByDayActivity activity;


    public FootballMatchesByDayTask(Integer year, Integer month, Integer day, Integer prevYear, Integer prevMonth, Integer prevDay, FootballMatchesByDayActivity activity) {
        this.year = year;
        this.month = month + 1;
        this.day = day;
        this.prevYear = prevYear;
        this.prevMonth = prevMonth + 1;
        this.prevDay = prevDay;
        matches = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + prevYear + "-" + prevMonth + "-" + prevDay + to + year + "-" + month + "-" + day + resultsOnly);
            System.out.println(url + prevYear + "-" + prevMonth + "-" + prevDay + to + year + "-" + month + "-" + day + resultsOnly);

            if(json == null){return null;}

            JSONArray jsonArray = new JSONObject(json).getJSONArray("match");

            for(int i = 0; i < jsonArray.length(); i++){
                matches.add(new Match(jsonArray.getJSONObject(i).getJSONObject("match_reference").getInt("id"),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("match_date")),
                        buildTeam(jsonArray.getJSONObject(i).getJSONObject("team_score_a")),
                        buildTeam(jsonArray.getJSONObject(i).getJSONObject("team_score_b"))));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Team buildTeam(JSONObject input){
        try {
            return new Team(input.getJSONObject("team").getJSONObject("team_reference").getInt("id"),
                    input.getJSONObject("team").getString("name"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        activity.updateMatches(matches);
    }
}
