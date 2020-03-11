package com.example.horseracing.activities.football;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.horseracing.Adapters.football.FootballMatchesByDayAdapter;
import com.example.horseracing.R;
import com.example.horseracing.asyncTasks.football.FootballMatchesByDayTask;
import com.example.horseracing.asyncTasks.football.SixMonthsOfResults;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.PastResults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FootballMatchesByDayActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DatePicker datePicker;
    private Button changeDateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_football_matches_by_day);

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        final Calendar prevCalandar = GregorianCalendar.getInstance();
        prevCalandar.setTime(date);

        recyclerView = findViewById(R.id.my_recycler_view);
        datePicker = findViewById(R.id.datePicker);
        changeDateButton = findViewById(R.id.changeDate);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), prevCalandar);
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        for(int i = 0; i < 48; i++) {
            int prevYear = year;
            int prevMonth = month;
            int prevDay = 1;
            getPastSixMonthsResults(year, month, day, prevYear, prevMonth, prevDay);
            prevMonth--;
            if(prevMonth < 0){
                prevMonth = 11;
                prevYear--;
            }
            year = prevYear;
            month = prevMonth;
            prevCalandar.set(Calendar.MONTH, month);
            day = prevCalandar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        updateData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                prevCalandar);

    }

    private void getPastSixMonthsResults(Integer year, Integer month, Integer day, Integer prevYear, Integer prevMonth, Integer prevDay){
        new SixMonthsOfResults(year,month,day, prevYear, prevMonth, prevDay).execute();
    }

    private void updateData(Integer year, Integer month, Integer day, Calendar calendar){
        int nextDay = 0;
        int nextMonth = month;
        int nextYear = year;
        nextDay += day;
        nextDay++;
        calendar.set(Calendar.MONTH, month);
        if(nextDay > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){
            nextMonth++;
            nextDay = 0;
            if(nextMonth > 11){
                nextMonth = 0;
                nextYear++;
            }
        }
        new FootballMatchesByDayTask(nextYear,nextMonth,nextDay,year,month,day, this).execute();
    }

    public void updateMatches(ArrayList<Match> matches){
        // specify an adapter (see also next example)
        mAdapter = new FootballMatchesByDayAdapter(matches, this);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
    }

}
