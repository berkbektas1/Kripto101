package com.example.kripto101.Models;

public class CardSwipeModel {

    String word;
    String descriptions;

    public CardSwipeModel(String word, String descriptions) {
        this.word = word;
        this.descriptions = descriptions;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
