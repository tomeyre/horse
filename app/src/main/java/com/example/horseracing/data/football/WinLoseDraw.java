package com.example.horseracing.data.football;

public class WinLoseDraw {

    private Integer win;
    private Integer lose;
    private Integer draw;

    public WinLoseDraw(Integer win, Integer lose, Integer draw) {
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String print(){
        StringBuilder sb = new StringBuilder();
        sb.append("win: " + win + "\n");
        sb.append("lose: " + lose + "\n");
        sb.append("draw: " + draw + "\n");
        return sb.toString();
    }
}
