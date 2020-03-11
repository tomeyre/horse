package com.example.horseracing.data.football;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {

    private Integer id;
    private Date date;
//    private String competition;
    private String winner;
    private String outcome;
    private Team teamA;
    private Team teamB;

    public Match(Integer id, Date date, Team teamA, Team teamB) {
        this.id = id;
        this.date = date;
//        this.competition = competition;
        this.teamA = teamA;
        this.teamB = teamB;
    }

//    protected Match(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readInt();
//        }
//        competition = in.readString();
//        winner = in.readString();
//        outcome = in.readString();
//        teamA = in.readTypedObject(Team.CREATOR);
//        teamB = in.readTypedObject(Team.CREATOR);
//    }
//
//
//    public static final Parcelable.Creator<Match> CREATOR = new Parcelable.Creator<Match>() {
//        @Override
//        public Match createFromParcel(Parcel in) {
//            return new Match(in);
//        }
//
//        @Override
//        public Match[] newArray(int size) {
//            return new Match[size];
//        }
//    };

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

//    public String getCompetition() {
//        return competition;
//    }
//
//    public void setCompetition(String competition) {
//        this.competition = competition;
//    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeParcelable(teamA,i);
//        parcel.writeParcelable(teamB,i);
//    }
}
