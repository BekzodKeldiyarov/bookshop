package com.bekzodkeldiyarov.bookshop.service;

import com.bekzodkeldiyarov.bookshop.model.Author;

import java.util.List;
import java.util.Map;

public interface AuthorService {
    Map<String, List<Author>> getAuthorsMap();
    Author save(Author author);
}
