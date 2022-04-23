package com.example.kripto101.Models;

import android.media.Image;

public class WordsModel {
    private String word;
    private String description;
    private int imageWords;

    public WordsModel(){

    }

    public WordsModel(String word, String description, int imageWords) {
        this.word = word;
        this.description = description;
        this.imageWords = imageWords;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public int getImageWords() {
        return imageWords;
    }
}
