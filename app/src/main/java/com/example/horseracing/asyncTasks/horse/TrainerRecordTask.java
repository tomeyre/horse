package com.example.horseracing.asyncTasks.horse;

import android.os.AsyncTask;

import com.example.horseracing.activities.horse.RaceActivity;
import com.example.horseracing.data.horse.DateOfSelectedRace;
import com.example.horseracing.data.horse.Record;
import com.example.horseracing.util.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrainerRecordTask extends AsyncTask<String, String, String> {

    private Integer id;
    private RaceActivity activity;
    private ArrayList<Record> records;
    private String url = "https://www.sportinglife.com/api/horse-racing/trainer/";
    private DateOfSelectedRace dateOfSelectedRace = DateOfSelectedRace.getInstance();

    public TrainerRecordTask(Integer id, RaceActivity activity){
        this.id = id;
        this.activity = activity;
        records = new ArrayList<>();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            if (id == 0) {
                return null;
            }
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + id);
            System.out.println(url + id);

            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = jsonObject.getJSONArray("run_previous_results");

            for(int i = 0; i < jsonArray.length(); i++) {
                Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                        (jsonArray.getJSONObject(i).has("date") ? jsonArray.getJSONObject(i).getString("date") : "2900-01-01") + " " +
                                (jsonArray.getJSONObject(i).has("time") ? jsonArray.getJSONObject(i).getString("time") : "00:00:00"));
                Calendar raceDate = Calendar.getInstance();
                raceDate.setTime(temp);
                Calendar dateSelected = Calendar.getInstance();
                dateSelected.setTime(temp);
                dateSelected.set(Calendar.YEAR, dateOfSelectedRace.getYear());
                dateSelected.set(Calendar.MONTH, dateOfSelectedRace.getMonth());
                dateSelected.set(Calendar.DAY_OF_MONTH, dateOfSelectedRace.getDay());
//                if (raceDate.compareTo(dateSelected) < 0) {
                    records.add(new Record(
                            temp,
                            jsonArray.getJSONObject(i).getJSONObject("horse_summary").has("name") ? jsonArray.getJSONObject(i).getJSONObject("horse_summary").getString("name") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("course_name") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("course_name") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("distance") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("distance") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("run_type") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("run_type") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("weight") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("weight") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("position") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getInt("position") : 0,
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("runner_count") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getInt("runner_count") : 0,
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("bha") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getInt("bha") : 0,
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("race_class") &&
                                    !jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("race_class").equalsIgnoreCase("") ? Integer.parseInt(jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("race_class")) : 0,
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("going") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("going") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("odds") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getString("odds") : "",
                            jsonArray.getJSONObject(i).getJSONObject("horse_entry").has("race_id") ? jsonArray.getJSONObject(i).getJSONObject("horse_entry").getInt("race_id") : 0
                    ));
//                    if(records.size() == 6)break;
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        activity.addTrainerRecord(records, id);
    }
}
