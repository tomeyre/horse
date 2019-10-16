package com.example.horseracing.asyncTasks.horse;

import android.os.AsyncTask;

import com.example.horseracing.activities.horse.RaceActivity;
import com.example.horseracing.data.horse.Horse;
import com.example.horseracing.data.horse.Jockey;
import com.example.horseracing.data.horse.RaceParticipant;
import com.example.horseracing.data.horse.Trainer;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RaceParticipantTask extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/horse-racing/race/";

    private Integer id;
    private ArrayList<RaceParticipant> raceParticipants;
    private RaceActivity activity;


    public RaceParticipantTask(Integer id, RaceActivity activity){
        this.id = id;
        raceParticipants = new ArrayList<>();
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + id);
            System.out.println(url + id);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("rides");

            for(int i = 0; i < jsonArray.length(); i++){
                raceParticipants.add(new RaceParticipant(buildHorse(jsonArray.getJSONObject(i), jsonObject),
                        buildJockey(jsonArray.getJSONObject(i).getJSONObject("jockey")),
                        buildTrainer(jsonArray.getJSONObject(i).getJSONObject("trainer"))));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private Horse buildHorse(JSONObject input, JSONObject raceObject){
        try {
            Horse horse = new Horse(
                    input.getJSONObject("horse").has("name") ? input.getJSONObject("horse").getString("name") : "",
                    input.getJSONObject("horse").has("age") ? input.getJSONObject("horse").getInt("age") : 0,
                    input.getJSONObject("horse").getJSONObject("sex").has("type") ? input.getJSONObject("horse").getJSONObject("sex").getString("type") : "",
                    input.getJSONObject("horse").has("last_ran_days") ? input.getJSONObject("horse").getInt("last_ran_days") : 1000,
                    input.has("official_rating") ? input.getInt("official_rating") : 0,
                    input.getJSONObject("horse").getJSONObject("horse_reference").has("id") ? input.getJSONObject("horse").getJSONObject("horse_reference").getInt("id") : 0,
                    input.getJSONObject("owner").has("name") ? input.getJSONObject("owner").getString("name") : "",
                    input.getJSONObject("trainer").has("name") ? input.getJSONObject("trainer").getString("name") : "",
                    input.getJSONObject("jockey").has("name") ? input.getJSONObject("jockey").getString("name") : "",
                    input.has("finish_position") ? input.getInt("finish_position") : 0,
                    input.getJSONObject("betting").has("current_odds") ? input.getJSONObject("betting").getString("current_odds") : "",
                    input.has("handicap") ? input.getString("handicap") : "",
                    raceObject.getJSONObject("race_summary").has("race_class") ? raceObject.getJSONObject("race_summary").getInt("race_class") : 0,
                    raceObject.getJSONObject("race_summary").has("going") ? raceObject.getJSONObject("race_summary").getString("going") : "",
                    input.has("ride_status") ? input.getString("ride_status").equalsIgnoreCase("NONRUNNER") ? false : true : true);
            return horse;
        }catch (Exception e){
            return null;
        }
    }

    private Jockey buildJockey(JSONObject input){
        try {
            Jockey jockey = new Jockey(
                    input.getString("name"),
                    input.getJSONObject("person_reference").getInt("id")
                    );
            return jockey;
        }catch (Exception e){
            return null;
        }

    }

    private Trainer buildTrainer(JSONObject input){
        try{
            Trainer trainer = new Trainer(
                    input.getString("name"),
                    input.getJSONObject("business_reference").getInt("id")
            );
            return trainer;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result){
        activity.checkAllHorseResults(raceParticipants);
    }
}
