package com.example.vm510lf.psyapp;

public class Article {
    private String title;
    private String imageURL;

    public Article(String title, String imageURL){
        this.title = title;
        this.imageURL = imageURL;
    }
    public String getTitle() {
        return title;
    }
    public String getImageURL() {
        return imageURL;
    }
}
