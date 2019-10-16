package com.example.horseracing.data.horse;

import java.util.Calendar;
import java.util.Date;

public class DateOfSelectedRace {

    private Integer year;
    private Integer month;
    private Integer day;
    private Date date = new Date();

    private static final DateOfSelectedRace ourInstance = new DateOfSelectedRace();

    public static DateOfSelectedRace getInstance() {
        return ourInstance;
    }

    private DateOfSelectedRace() {
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        date = calendar.getTime();
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month);
        date = calendar.getTime();
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        date = calendar.getTime();
        this.day = day;
    }

    public Date getDate(){
        return date;
    }
}
