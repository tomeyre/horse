package com.example.horseracing.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.horseracing.activities.football.FootballMatchesByDayActivity;
import com.example.horseracing.activities.horse.RaceDayEventsActivity;
import com.example.horseracing.R;

public class MainActivity extends AppCompatActivity {

    private Button horses;
    private Button football;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        horses = findViewById(R.id.horses);
        horses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RaceDayEventsActivity.class);
                startActivity(intent);
            }
        });
        football = findViewById(R.id.football);
        football.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FootballMatchesByDayActivity.class);
                startActivity(intent);
            }
        });
    }

}
