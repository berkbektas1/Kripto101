package com.example.kripto101.Models;

public class WordsModel {
    private String word;
    private String level;

    public WordsModel(){

    }

    public WordsModel(String word, String level) {
        this.word = word;
        this.level = level;
    }

    public String getWord() {
        return word;
    }

    public String getLevel() {
        return level;
    }
}
