package com.example.horseracing.activities.football;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.horseracing.R;
import com.example.horseracing.asyncTasks.football.FootballMatchTask;
import com.example.horseracing.asyncTasks.football.Ranking;
import com.example.horseracing.data.football.Match;
import com.example.horseracing.data.football.MatchResult;
import com.example.horseracing.data.football.PastResults;
import com.example.horseracing.data.football.Rankings;
import com.example.horseracing.data.football.Record;
import com.example.horseracing.data.football.Team;
import com.example.horseracing.data.football.WinLoseDraw;

import java.util.ArrayList;
import java.util.Date;

public class FootballMatchActivity extends AppCompatActivity {

    private Match match;
    private TextView teamA;
    private TextView teamB;
    private PastResults pastResults = PastResults.getInstance();
    private Rankings rankings = Rankings.getInstance();
    private Date dateOfLastMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        extras.setClassLoader(Match.class.getClassLoader());
        match = (Match) extras.getSerializable("match");
        setContentView(R.layout.activity_football_match);

        teamA = findViewById(R.id.teamA);
        teamB = findViewById(R.id.teamB);

        new FootballMatchTask(match, this).execute();

    }

    public void updateMatchView(Match match){
//        StringBuilder a = new StringBuilder();
//        StringBuilder b = new StringBuilder();
//        a.append(match.getTeamA().getName() + "\n" +
//                "--------------------------------\n" );
//        a.append(getPastWins(match.getTeamA(), match.getTeamB()));
//        teamA.setText(a.toString());
//        b.append(match.getTeamB().getName() + "\n" +
//                "--------------------------------\n" );
//        b.append(getPastWins(match.getTeamB(), match.getTeamA()));
//        teamB.setText(b.toString());

        calculateWinner(match);

    }

    private void calculateWinner(Match match){
        int teamAScore = 0;
        int teamBScore = 0;

        if(rankings.getRanks().get(match.getTeamA().getName()).getWins() > rankings.getRanks().get(match.getTeamB().getName()).getWins()){
            teamAScore++;
        }else if(rankings.getRanks().get(match.getTeamA().getName()).getWins() < rankings.getRanks().get(match.getTeamB().getName()).getWins()){
            teamBScore++;
        }else{
            teamAScore++;
            teamBScore++;
        }

        if(rankings.getRanks().get(match.getTeamA().getName()).getPlayed() > rankings.getRanks().get(match.getTeamB().getName()).getPlayed()){
            teamAScore++;
        }else if(rankings.getRanks().get(match.getTeamA().getName()).getPlayed() < rankings.getRanks().get(match.getTeamB().getName()).getPlayed()){
            teamBScore++;
        }else{
            teamAScore++;
            teamBScore++;
        }

        if(rankings.getRanks().get(match.getTeamA().getName()).getPosition() < rankings.getRanks().get(match.getTeamB().getName()).getPosition()){
            teamAScore++;
        }else if(rankings.getRanks().get(match.getTeamA().getName()).getPosition() > rankings.getRanks().get(match.getTeamB().getName()).getPosition()){
            teamBScore++;
        }else{
            teamAScore++;
            teamBScore++;
        }

        String lastResult = getLastResult(match.getTeamA(), match.getTeamB());
        if(lastResult.equalsIgnoreCase("AW")){
            teamAScore++;
        }else if (lastResult.equalsIgnoreCase("BW")){
            teamBScore++;
        }else{
            teamAScore++;
            teamBScore++;
        }

        WinLoseDraw a = getPastResults(match.getTeamA(),match.getTeamB());
        WinLoseDraw b = getPastResults(match.getTeamB(),match.getTeamA());

        teamAScore += a.getWin();
        teamBScore += b.getWin();

        teamA.setText(match.getTeamA().getName() + " / " + teamAScore + "\n" + rankings.getRanks().get(match.getTeamA().getName()).print()
                + "\n" + dateOfLastMatch + "\n" + a.print());
        teamB.setText(match.getTeamB().getName() + " / " + teamBScore + "\n" + rankings.getRanks().get(match.getTeamB().getName()).print()
                + "\n" + dateOfLastMatch + "\n" + b.print());
    }

    private String getLastResult(Team a, Team b){
        String response = "";
        ArrayList<MatchResult> matchResults = pastResults.getPastResults().get(a.getName());
        for(MatchResult matchResult : matchResults){
            if(matchResult.getAgainst().equalsIgnoreCase(b.getName())){
                if(dateOfLastMatch == null) {
                    dateOfLastMatch = matchResult.getMatchDate();
                    if(matchResult.getResult().equalsIgnoreCase("win")){
                        response = "AW";
                    }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                        response = "BW";
                    }else{
                        response = "AB";
                    }
                }else if(dateOfLastMatch.getTime() < matchResult.getMatchDate().getTime()){
                    dateOfLastMatch = matchResult.getMatchDate();
                    if(matchResult.getResult().equalsIgnoreCase("win")){
                        response = "AW";
                    }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                        response = "BW";
                    }else{
                        response = "AB";
                    }
                }
            }

        }
        return response;
    }

    private WinLoseDraw getPastResults(Team a, Team b){
        Integer winAgainstTeam = 0;
        Integer loseAgainstTeam = 0;
        Integer drawAgainstTeam = 0;
        Date dateOfLastMatch = null;
        StringBuilder sb = new StringBuilder();
        ArrayList<MatchResult> matchResults = pastResults.getPastResults().get(a.getName());
        for(MatchResult matchResult : matchResults){
            if(matchResult.getAgainst().equalsIgnoreCase(b.getName())){
                if(matchResult.getResult().equalsIgnoreCase("win")){
                    winAgainstTeam++;
                }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                    loseAgainstTeam++;
                }else{
                    drawAgainstTeam++;
                }
            }

        }
        return new WinLoseDraw(winAgainstTeam,loseAgainstTeam,drawAgainstTeam);
    }

    private String getPastWins(Team team, Team against){
        Integer win = 0;
        Integer lose = 0;
        Integer draw = 0;
        Integer winAgainstTeam = 0;
        Integer loseAgainstTeam = 0;
        Integer drawAgainstTeam = 0;
        Integer mostRecentWinAgainstTeam = 0;
        Integer mostRecentLoseAgainstTeam = 0;
        Integer mostRecentDrawAgainstTeam = 0;
        Date dateOfLastMatch = null;
        StringBuilder sb = new StringBuilder();
        ArrayList<MatchResult> matchResults = pastResults.getPastResults().get(team.getName());
        for(MatchResult matchResult : matchResults){
            if(matchResult.getResult().equalsIgnoreCase("win")){
                win++;
            }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                lose++;
            }else{
                draw++;
            }
            if(matchResult.getAgainst().equalsIgnoreCase(against.getName())){
                if(matchResult.getResult().equalsIgnoreCase("win")){
                    winAgainstTeam++;
                }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                    loseAgainstTeam++;
                }else{
                    drawAgainstTeam++;
                }
                if(dateOfLastMatch == null) {
                    dateOfLastMatch = matchResult.getMatchDate();
                    if(matchResult.getResult().equalsIgnoreCase("win")){
                        mostRecentWinAgainstTeam++;
                    }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                        mostRecentLoseAgainstTeam++;
                    }else{
                        mostRecentDrawAgainstTeam++;
                    }
                }else if(dateOfLastMatch.getTime() < matchResult.getMatchDate().getTime()){
                    dateOfLastMatch = matchResult.getMatchDate();
                    mostRecentWinAgainstTeam = 0;
                    mostRecentLoseAgainstTeam = 0;
                    mostRecentDrawAgainstTeam = 0;
                    if(matchResult.getResult().equalsIgnoreCase("win")){
                        mostRecentWinAgainstTeam++;
                    }else if(matchResult.getResult().equalsIgnoreCase("lose")){
                        mostRecentLoseAgainstTeam++;
                    }else{
                        mostRecentDrawAgainstTeam++;
                    }
                }
            }

        }
        if(dateOfLastMatch == null) dateOfLastMatch = new Date();
        return sb.append("WIN: " + win + "\nLOSE: " + lose + "\nDRAW: " + draw + "\n WIN AGAINST: " + winAgainstTeam +
                "\nLOSE AGAINST: " + loseAgainstTeam + "\nDRAW AGAINST: " + drawAgainstTeam + "\n DATE OF LAST MATCH:" +
                dateOfLastMatch.toString() + "\nMOST RECENT WIN AGAINST: " + mostRecentWinAgainstTeam +
                "\nMOST RECENT LOSE AGAINST: " + mostRecentLoseAgainstTeam + "\nMOST RECENT DRAW AGAINST: " +
                mostRecentDrawAgainstTeam + "\n RANKING: " + rankings.getRanks().get(team.getName()).getPosition() +
                rankings.getRanks().get(team.getName()).print()).toString();
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
        return sb.append("WIN: " + win + "\nLOSE: " + lose + "\nDRAW: " + draw + "\n").toString();
    }
}
