package com.bekzodkeldiyarov.bookshop.model;

import java.util.List;

public class BookPageDto {
    private Integer count;
    private List<Book> books;

    public BookPageDto(Integer count, List<Book> books) {
        this.count = count;
        this.books = books;
    }

    public BookPageDto() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
