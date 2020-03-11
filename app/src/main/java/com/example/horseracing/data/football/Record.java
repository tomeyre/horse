package com.example.horseracing.data.football;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

    private String against;
    private String match_type;
    private Date date;
    private String outcome;
    private String winner;

    public Record(String against, String match_type, Date date, String outcome, String winner) {
        this.against = against;
        this.match_type = match_type;
        this.date = date;
        this.outcome = outcome;
        this.winner = winner;
    }

    protected Record(Parcel in) {
        against = in.readString();
        match_type = in.readString();
        outcome = in.readString();
        winner = in.readString();
    }

//    public static final Creator<Record> CREATOR = new Creator<Record>() {
//        @Override
//        public Record createFromParcel(Parcel in) {
//            return new Record(in);
//        }
//
//        @Override
//        public Record[] newArray(int size) {
//            return new Record[size];
//        }
//    };

    public String getAgainst() {
        return against;
    }

    public void setAgainst(String against) {
        this.against = against;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(against);
//        parcel.writeString(match_type);
//        parcel.writeString(outcome);
//        parcel.writeString(winner);
//    }
}
