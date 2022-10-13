package com.bekzodkeldiyarov.bookshop.repository;

import com.bekzodkeldiyarov.bookshop.model.Book2Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Book2AuthorRepository extends JpaRepository<Book2Author, Integer> {
}
