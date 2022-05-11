package com.example.kripto101.Models;

import android.media.Image;

public class WordsModel {
    private String name, author, description, image;

    public WordsModel(){

    }

    public WordsModel(String name, String author, String description, String image) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
