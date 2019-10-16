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
import com.example.horseracing.data.football.Match;

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

        recyclerView = findViewById(R.id.my_recycler_view);
        datePicker = findViewById(R.id.datePicker);
        changeDateButton = findViewById(R.id.changeDate);
        changeDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        updateData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

    }

    private void updateData(Integer year, Integer month, Integer day){
        new FootballMatchesByDayTask(year,month,day, this).execute();
    }

    public void updateMatches(ArrayList<Match> matches){
        // specify an adapter (see also next example)
        mAdapter = new FootballMatchesByDayAdapter(matches, this);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);
    }

}
