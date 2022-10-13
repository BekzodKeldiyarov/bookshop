package com.bekzodkeldiyarov.bookshop.service;

import com.bekzodkeldiyarov.bookshop.model.Author;
import com.bekzodkeldiyarov.bookshop.repository.AuthorRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = authorRepository.findAll();
        Map<String, List<Author>> authorMap = new HashMap<>();
        for (Author author : authors) {
            String character = String.valueOf(author.getFirstName().charAt(0)).toLowerCase();
            if (authorMap.containsKey(character)) {
                authorMap.get(character).add(author);
            } else {
                authorMap.put(character, new ArrayList<>());
                authorMap.get(character).add(author);
            }
        }
        return authorMap;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }
}
