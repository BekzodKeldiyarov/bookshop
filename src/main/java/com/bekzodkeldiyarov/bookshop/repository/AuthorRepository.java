package com.bekzodkeldiyarov.bookshop.repository;

import com.bekzodkeldiyarov.bookshop.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    List<Author> findAll();
}
