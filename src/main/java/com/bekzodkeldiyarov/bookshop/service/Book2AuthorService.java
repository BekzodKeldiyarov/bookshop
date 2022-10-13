package com.bekzodkeldiyarov.bookshop.service;

import com.bekzodkeldiyarov.bookshop.model.Book2Author;
import com.bekzodkeldiyarov.bookshop.repository.Book2AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class Book2AuthorService {
    private final Book2AuthorRepository book2AuthorRepository;

    public Book2AuthorService(Book2AuthorRepository book2AuthorRepository) {
        this.book2AuthorRepository = book2AuthorRepository;
    }

    public Book2Author save(Book2Author book2Author){
        return book2AuthorRepository.save(book2Author);
    }
}
