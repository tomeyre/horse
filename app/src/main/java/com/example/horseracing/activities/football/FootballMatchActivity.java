package com.example.horseracing.activities.football;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.activities.MainActivity;
import com.example.horseracing.asyncTasks.football.FootballMatchTask;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.Record;
import com.example.horseracing.data.football.Team;

import java.util.ArrayList;

public class FootballMatchActivity extends AppCompatActivity {

    private Match match;
    private TextView teamA;
    private TextView teamB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        match = intent.getParcelableExtra("match");
        setContentView(R.layout.activity_football_match);

        teamA = findViewById(R.id.teamA);
        teamB = findViewById(R.id.teamB);

        new FootballMatchTask(match, this).execute();

    }

    public void updateMatchView(Match match){
        StringBuilder a = new StringBuilder();
        StringBuilder b = new StringBuilder();
        a.append(match.getTeamA().getName() + "\n" +
                "--------------------------------\n" );
        a.append(getWins(match.getTeamA()));
        teamA.setText(a.toString());
        a.append(match.getTeamB().getName() + "\n" +
                "--------------------------------\n" );
        a.append(getWins(match.getTeamB()));
        teamB.setText(b.toString());

    }

    private String getWins(Team team){
        Integer win = 0;
        Integer lose = 0;
        Integer draw = 0;
        StringBuilder sb = new StringBuilder();

        for(Record record : team.getRecord()){
            if(record.getOutcome().equalsIgnoreCase("win") &&
                    record.getWinner().equalsIgnoreCase(team.getName())){
                win++;
            }else if(record.getOutcome().equalsIgnoreCase("lose") &&
                    record.getWinner().equalsIgnoreCase(team.getName())){
                lose++;
            }else{
                draw++;
            }
        }
        return sb.append(win + "\n" + lose + "\n" + draw + "\n").toString();
    }
}
