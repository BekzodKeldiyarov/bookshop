package com.bekzodkeldiyarov.bookshop.model;

public class SearchWordDto {
    private String word;

    public SearchWordDto() {
    }

    public SearchWordDto(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
