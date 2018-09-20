package com.example.vm510lf.psyapp;

public class Question {
    private String theme;
    private String detail;
    private String id;
    private int index;

    public Question(String theme, String detail){
        this.detail = detail;
        this.theme = theme;
    }

    public Question(String theme, int index) {
        this.theme = theme;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDetail() {
        return detail;
    }

    public String getTheme() {
        return theme;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
