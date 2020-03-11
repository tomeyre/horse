package com.example.horseracing.asyncTasks.football;

import android.os.AsyncTask;

import com.example.horseracing.activities.football.FootballMatchesByDayActivity;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.MatchResult;
import com.example.horseracing.data.football.PastResults;
import com.example.horseracing.data.football.Team;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SixMonthsOfResults extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/football/competition/1/matches?from=";
    private static final String to = "&to=";
    private static final String resultsOnly = "&results_only=true";

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer prevYear;
    private Integer prevMonth;
    private Integer prevDay;
    private PastResults pastResults = PastResults.getInstance();

    public SixMonthsOfResults(Integer year, Integer month, Integer day, Integer prevYear, Integer prevMonth, Integer prevDay){
        this.year = year;
        this.month = month + 1;
        this.day = day;
        this.prevYear = prevYear;
        this.prevMonth = prevMonth + 1;
        this.prevDay = prevDay;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + prevYear + "-" + prevMonth + "-" + prevDay + to + year + "-" + month + "-" + day + resultsOnly);
            System.out.println(url + prevYear + "-" + prevMonth + "-" + prevDay + to + year + "-" + month + "-" + day + resultsOnly);

            if(json == null){return null;}

            JSONArray jsonArray = new JSONObject(json).getJSONArray("match");

            HashMap<String, ArrayList<MatchResult>> pastResultsMap = pastResults.getPastResults();

            for(int i = 0; i < jsonArray.length(); i++){
                ArrayList<MatchResult> matchResults = new ArrayList<>();
                if(pastResultsMap.get(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name")) == null){
                    matchResults.add(new MatchResult(true,
                                    jsonArray.getJSONObject(i).getJSONObject("match_outcome").getString("outcome").equalsIgnoreCase("WIN") ?
                                            jsonArray.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name")
                                                    .equalsIgnoreCase(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"))
                                                    ? "WIN" : "LOSE" : "DRAW",
                            jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"),
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("match_date"))));
                    pastResultsMap.put(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"), matchResults);
                }else{
                    matchResults = pastResultsMap.get(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"));
                    matchResults.add(new MatchResult(true,
                            jsonArray.getJSONObject(i).getJSONObject("match_outcome").getString("outcome").equalsIgnoreCase("WIN") ?
                                    jsonArray.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name")
                                            .equalsIgnoreCase(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"))
                                            ? "WIN" : "LOSE" : "DRAW",
                            jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"),
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("match_date"))));
                    pastResultsMap.put(jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"), matchResults);
                }

                matchResults = new ArrayList<>();
                if(pastResultsMap.get(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name")) == null){
                    matchResults.add(new MatchResult(true,
                            jsonArray.getJSONObject(i).getJSONObject("match_outcome").getString("outcome").equalsIgnoreCase("WIN") ?
                                    jsonArray.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name")
                                            .equalsIgnoreCase(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"))
                                            ? "WIN" : "LOSE" : "DRAW",
                            jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"),
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("match_date"))));
                    pastResultsMap.put(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"), matchResults);
                }else{
                    matchResults = pastResultsMap.get(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"));
                    matchResults.add(new MatchResult(true,
                            jsonArray.getJSONObject(i).getJSONObject("match_outcome").getString("outcome").equalsIgnoreCase("WIN") ?
                                    jsonArray.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name")
                                            .equalsIgnoreCase(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"))
                                            ? "WIN" : "LOSE" : "DRAW",
                            jsonArray.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"),
                            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("match_date"))));
                    pastResultsMap.put(jsonArray.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"), matchResults);
                }
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

    }
}
