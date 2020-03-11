package com.example.horseracing.data.football;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class Team implements Serializable {

    private Integer id;
    private String name;
    private Integer score;
    private ArrayList<Record> record;

    public Team(Integer id, String name){
        this.id = id;
        this.name = name;
    }

//    protected Team(Parcel in) {
//        if (in.readByte() == 0) {
//            id = null;
//        } else {
//            id = in.readInt();
//        }
//        name = in.readString();
//        if (in.readByte() == 0) {
//            score = null;
//        } else {
//            score = in.readInt();
//        }
//        if (in.readByte() == 0) {
//            in.readTypedList(record, Record.CREATOR);
//        }
//    }

//    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
//        @Override
//        public Team createFromParcel(Parcel in) {
//            return new Team(in);
//        }
//
//        @Override
//        public Team[] newArray(int size) {
//            return new Team[size];
//        }
//    };

    public ArrayList<Record> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<Record> record) {
        this.record = record;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }

//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        if (id == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeInt(id);
//        }
//        parcel.writeString(name);
//        if (score == null) {
//            parcel.writeByte((byte) 0);
//        } else {
//            parcel.writeByte((byte) 1);
//            parcel.writeInt(score);
//            //parcel.writeTypedList(record);
//        }
//    }
}
