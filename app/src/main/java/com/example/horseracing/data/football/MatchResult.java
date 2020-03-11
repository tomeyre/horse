package com.example.horseracing.data.football;

import java.util.Date;

public class MatchResult {

    private Boolean home;
    private String result;
    private String against;
    private Date matchDate;

    public MatchResult(Boolean home, String result, String against, Date matchDate) {
        this.home = home;
        this.result = result;
        this.against = against;
        this.matchDate = matchDate;
    }

    public Boolean getHome() {
        return home;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAgainst() {
        return against;
    }

    public void setAgainst(String against) {
        this.against = against;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }
}
