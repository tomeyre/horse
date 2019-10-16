package com.example.horseracing.activities.horse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.horseracing.Adapters.horse.RaceCourseAdapter;
import com.example.horseracing.R;
import com.example.horseracing.data.horse.EventCorrectness;
import com.example.horseracing.data.horse.RaceCourse;
import com.example.horseracing.data.horse.RaceStats;

import java.util.ArrayList;

public class RaceCourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private EventCorrectness eventCorrectness = EventCorrectness.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList<RaceCourse> races = (ArrayList<RaceCourse>) intent.getSerializableExtra("races");
        setContentView(R.layout.activity_race_course);
        eventCorrectness.reset();

        recyclerView = findViewById(R.id.race_course_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RaceCourseAdapter(races, this);
        mAdapter.setHasStableIds(true);
        recyclerView.setAdapter(mAdapter);

    }
}
