package com.example.safood.Model;

public class Article {
    String title;
    String Content;
    String image;

    public Article() {

    }

    public Article(String title, String content, String image) {
        this.title = title;
        Content = content;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
