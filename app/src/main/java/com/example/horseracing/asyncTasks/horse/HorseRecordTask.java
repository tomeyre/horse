package com.example.horseracing.asyncTasks.horse;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.example.horseracing.activities.horse.ChancesActivity;
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

public class HorseRecordTask extends AsyncTask<String, String, String> {

    private static final String url = "https://www.sportinglife.com/api/horse-racing/horse/";

    private Integer id;
    private ArrayList<Record> records;
    private AppCompatActivity activity;
    private DateOfSelectedRace dateOfSelectedRace = DateOfSelectedRace.getInstance();
    private Date dateOfRace;
    private Integer raceId;


    public HorseRecordTask(Integer id, AppCompatActivity activity, Date dateOfRace, Integer raceId){
        this.id = id;
        records = new ArrayList<>();
        this.activity = activity;
        this.dateOfRace = dateOfRace;
        this.raceId = raceId;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            if(id == 0){return null;}
            HttpGet httpGet = new HttpGet();
            String json = httpGet.getJSONFromUrl(url + id);
            System.out.println(url + id);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("previous_results");

            for(int i = 0; i < jsonArray.length(); i++) {
                Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
                        (jsonArray.getJSONObject(i).has("date") ? jsonArray.getJSONObject(i).getString("date") : "2900-01-01") + " " +
                                (jsonArray.getJSONObject(i).has("time") ? jsonArray.getJSONObject(i).getString("time") : "00:00:00"));
                Calendar raceDate = Calendar.getInstance();
                raceDate.setTime(temp);
                Calendar dateSelected = Calendar.getInstance();
                dateSelected.setTime(temp);
                if(dateOfRace == null) {
                    dateSelected.set(Calendar.YEAR, dateOfSelectedRace.getYear());
                    dateSelected.set(Calendar.MONTH, dateOfSelectedRace.getMonth());
                    dateSelected.set(Calendar.DAY_OF_MONTH, dateOfSelectedRace.getDay());
                }else{
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateOfRace);
                    dateSelected.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                    dateSelected.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                    dateSelected.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
                }
                if (raceDate.compareTo(dateSelected) < 0) {
                    records.add(new Record(temp,
                            null,
                            jsonArray.getJSONObject(i).has("course_name") ? jsonArray.getJSONObject(i).getString("course_name") : "",
                            jsonArray.getJSONObject(i).has("distance") ? jsonArray.getJSONObject(i).getString("distance") : "",
                            jsonArray.getJSONObject(i).has("run_type") ? jsonArray.getJSONObject(i).getString("run_type") : "",
                            jsonArray.getJSONObject(i).has("weight") ? jsonArray.getJSONObject(i).getString("weight") : "",
                            jsonArray.getJSONObject(i).has("position") ? jsonArray.getJSONObject(i).getInt("position") : 0,
                            jsonArray.getJSONObject(i).has("runner_count") ? jsonArray.getJSONObject(i).getInt("runner_count") : 0,
                            jsonArray.getJSONObject(i).has("bha") ? jsonArray.getJSONObject(i).getInt("bha") : 0,
                            jsonArray.getJSONObject(i).has("race_class") &&
                                    !jsonArray.getJSONObject(i).getString("race_class").equalsIgnoreCase("") ? Integer.parseInt(jsonArray.getJSONObject(i).getString("race_class")) : 0,
                            jsonArray.getJSONObject(i).has("going") ? jsonArray.getJSONObject(i).getString("going") : "",
                            jsonArray.getJSONObject(i).has("odds") ? jsonArray.getJSONObject(i).getString("odds") : "",
                            jsonArray.getJSONObject(i).has("race_id") ? jsonArray.getJSONObject(i).getInt("race_id") : 0
                    ));
//                    if(records.size() == 6)break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result){
        if(activity instanceof  RaceActivity) {
            ((RaceActivity) activity).addHorseRecord(records, id);
        }
        if(activity instanceof ChancesActivity) {
            ((ChancesActivity) activity).addHorseRecord(records, id, dateOfRace, raceId);
        }
    }
}
