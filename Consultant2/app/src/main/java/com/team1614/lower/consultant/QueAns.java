package com.team1614.lower.consultant;


public class QueAns {
    private int id;
    private String que;
    private String ans;
    private Double rate;
    private String favouriate;

    public QueAns() {
    }

    public QueAns(int id, String que, String ans, Double rate, String favouriate) {
        this.id = id;
        this.que = que;
        this.ans = ans;
        this.rate = rate;
        this.favouriate = favouriate;
    }

    public void setQue(String que) {
        this.que = que;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setFavourate(String favouriate) {
        this.favouriate = favouriate;
    }

    public String getQue() {
        return que;
    }

    public String getAns() {
        return ans;
    }

    public int getId() {
        return id;
    }

    public Double getRate() {
        return rate;
    }

    public String getFavourate() {
        return favouriate;
    }
}


