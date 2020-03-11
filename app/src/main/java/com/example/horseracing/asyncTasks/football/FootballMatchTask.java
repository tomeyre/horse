package com.example.horseracing.asyncTasks.football;

import android.os.AsyncTask;

import com.example.horseracing.activities.football.FootballMatchActivity;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.Record;
import com.example.horseracing.data.football.Team;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FootballMatchTask extends AsyncTask<String, String, String> {

    private FootballMatchActivity activity;
    private String url = "https://www.sportinglife.com/api/football/form?match_id=";
    private ArrayList<Record> a = new ArrayList<>();
    private ArrayList<Record> b = new ArrayList<>();
    private Match match;

    public FootballMatchTask(Match match, FootballMatchActivity activity) {
        this.match = match;
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + match.getId());
            System.out.println(url + match.getId());

            JSONArray jsonArrayA = new JSONObject(json).getJSONObject("team_a").getJSONArray("matches");
            JSONArray jsonArrayB = new JSONObject(json).getJSONObject("team_b").getJSONArray("matches");

            for (int i = 0; i < jsonArrayA.length(); i++) {
                a.add(new Record(
                        jsonArrayA.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name").equalsIgnoreCase(match.getTeamA().getName()) ? jsonArrayA.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name") : jsonArrayA.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name"),
                        jsonArrayA.getJSONObject(i).getString("match_type"),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArrayA.getJSONObject(i).getString("match_date")),
                        jsonArrayA.getJSONObject(i).getJSONObject("match_outcome").has("outcome") ? jsonArrayA.getJSONObject(i).getJSONObject("match_outcome").getString("outcome") : "",
                        jsonArrayA.getJSONObject(i).getJSONObject("match_outcome").has("winner") ? jsonArrayA.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name") : ""
                ));
            }
            match.getTeamA().setRecord(a);

            for (int i = 0; i < jsonArrayB.length(); i++) {
                b.add(new Record(jsonArrayB.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name").equalsIgnoreCase(match.getTeamB().getName()) ? jsonArrayB.getJSONObject(i).getJSONObject("team_score_a").getJSONObject("team").getString("name") : jsonArrayB.getJSONObject(i).getJSONObject("team_score_b").getJSONObject("team").getString("name"),
                        jsonArrayB.getJSONObject(i).getString("match_type"),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(jsonArrayB.getJSONObject(i).getString("match_date")),
                        jsonArrayB.getJSONObject(i).getJSONObject("match_outcome").has("outcome") ? jsonArrayB.getJSONObject(i).getJSONObject("match_outcome").getString("outcome") : "",
                        jsonArrayB.getJSONObject(i).getJSONObject("match_outcome").has("winner") ? jsonArrayB.getJSONObject(i).getJSONObject("match_outcome").getJSONObject("winner").getString("name") : ""
                ));
            }
            match.getTeamB().setRecord(b);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        activity.updateMatchView(match);
    }
}
