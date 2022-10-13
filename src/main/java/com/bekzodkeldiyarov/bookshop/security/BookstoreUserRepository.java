package com.bekzodkeldiyarov.bookshop.security;

import com.bekzodkeldiyarov.bookshop.security.BookstoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookstoreUserRepository extends JpaRepository<BookstoreUser, Integer> {
    BookstoreUser findByName(String name);
    BookstoreUser findByEmail(String email);
}
