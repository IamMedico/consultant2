package com.team1614.lower.consultant;


public class Album {
    private String question;
    private String answer;
    private Double rate;
    private String favouriate;
    private int id;


    public Album() {
    }

    public Album(int id, String que, String ans, Double rate, String favouriate) {
        this.id = id;
        this.question = que;
        this.answer = ans;
        this.rate = rate;
        this.favouriate = favouriate;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String ques) {
        this.question = ques;
    }

    public String getAnswer() {
        return answer;
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

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
