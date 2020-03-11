package com.example.horseracing.asyncTasks.horse;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.horseracing.activities.horse.ChancesActivity;
import com.example.horseracing.activities.horse.RaceDayEventsActivity;
import com.example.horseracing.data.horse.RaceCourse;
import com.example.horseracing.data.horse.RaceEvent;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RaceEventAndCoursesTask extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/horse-racing/racing/racecards/";

    private Integer year;
    private Integer month;
    private Integer day;
    private ArrayList<RaceEvent> races;
    private RaceDayEventsActivity raceDayEventsActivity;
    private ChancesActivity chancesActivity;


    public RaceEventAndCoursesTask(Integer year, Integer month, Integer day, AppCompatActivity activity){
        this.year = year;
        this.month = month + 1;
        this.day = day;
        races = new ArrayList<>();
        if(activity instanceof RaceDayEventsActivity) this.raceDayEventsActivity = (RaceDayEventsActivity) activity;
        if(activity instanceof ChancesActivity) this.chancesActivity = (ChancesActivity) activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            HttpGet httpGet = new HttpGet();
            System.out.println(url + year + "-" + month + "-" + day);
            String json = httpGet.getJSONFromUrl(url + year + "-" + month + "-" + day);

            if(json == null){return null;}

            JSONArray jsonArray = new JSONArray(json);

            for(int i = 0; i < jsonArray.length(); i++){
                if(jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getJSONObject("course").getJSONObject("country").getString("long_name").equalsIgnoreCase("england")) {
                    races.add(new RaceEvent(jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getJSONObject("course").getString("name"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getJSONObject("meeting_reference").getInt("id"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getString("status"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getJSONObject("course").getJSONObject("course_reference").getInt("id"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getJSONObject("course").getJSONObject("country").getString("long_name"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").has("going") ? jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getString("going") : "unknown",
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getString("surface_summary"),
                            jsonArray.getJSONObject(i).getJSONObject("meeting_summary").has("weather") ? jsonArray.getJSONObject(i).getJSONObject("meeting_summary").getString("weather") : null,
                            buildRaceList(jsonArray.getJSONObject(i).getJSONArray("races"))));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<RaceCourse> buildRaceList(JSONArray input){
        ArrayList races = new ArrayList();
        try {
            for (int i = 0; i < input.length(); i++){
                races.add(new RaceCourse(input.getJSONObject(i).getString("course_name"),
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                                input.getJSONObject(i).getString("date") + " " +
                                input.getJSONObject(i).getString("time") + ":00"),
                        input.getJSONObject(i).getString("race_class"),
                        input.getJSONObject(i).getString("distance"),
                        input.getJSONObject(i).getInt("ride_count"),
                        input.getJSONObject(i).has("going") ? input.getJSONObject(i).getString("going") : "unkown",
                        input.getJSONObject(i).getBoolean("has_handicap"),
                        input.getJSONObject(i).getJSONObject("race_summary_reference").getInt("id")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return races;
    }

    @Override
    protected void onPostExecute(String result){
        if(raceDayEventsActivity != null) {
            raceDayEventsActivity.updateRaceCards(races);
        }else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY,0);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            chancesActivity.updateList(cal.getTime(), races);
        }
    }
}
