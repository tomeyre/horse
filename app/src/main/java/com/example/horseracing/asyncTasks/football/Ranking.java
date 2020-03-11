package com.example.horseracing.asyncTasks.football;

import android.os.AsyncTask;

import com.example.horseracing.data.football.MatchResult;
import com.example.horseracing.data.football.PastResults;
import com.example.horseracing.data.football.Rank;
import com.example.horseracing.data.football.Rankings;
import com.example.horseracing.data.football.Team;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranking extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/football/league-tables?competition=1";

    private Rankings rankings = Rankings.getInstance();
    private HashMap<String, Rank> ranks = new HashMap<>();

    @Override
    protected String doInBackground(String... strings) {
        try{
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url);
            System.out.println(url);

            if(json == null){return null;}

            JSONArray jsonArray = new JSONObject(json).getJSONArray("tables").getJSONObject(0).getJSONArray("row");;

            for(int i = 1; i < jsonArray.length(); i++){
                JSONArray temp = jsonArray.getJSONObject(i).getJSONArray("cell");
                ranks.put(temp.getJSONObject(1).getString("value"),new Rank(
                        temp.getJSONObject(0).getInt("value"),
                        temp.getJSONObject(1).getString("value"),
                        temp.getJSONObject(2).getInt("value"),
                        temp.getJSONObject(3).getInt("value"),
                        temp.getJSONObject(4).getInt("value"),
                        temp.getJSONObject(5).getInt("value"),
                        temp.getJSONObject(6).getInt("value"),
                        temp.getJSONObject(7).getInt("value"),
                        temp.getJSONObject(8).getInt("value"),
                        temp.getJSONObject(9).getInt("value")

                ));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        rankings.setRanks(ranks);
        return null;
    }
}
