package com.bekzodkeldiyarov.bookshop.repository;

import com.bekzodkeldiyarov.bookshop.model.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookFileRepository extends JpaRepository<BookFile, Integer> {
    BookFile getBookFileByHash(String hash);
}
