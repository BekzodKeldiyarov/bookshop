package com.bekzodkeldiyarov.bookshop.runner;

import com.bekzodkeldiyarov.bookshop.exception.UserAlreadyExistsException;
import com.bekzodkeldiyarov.bookshop.model.Author;
import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.model.Book2Author;
import com.bekzodkeldiyarov.bookshop.model.RegistrationForm;
import com.bekzodkeldiyarov.bookshop.service.AuthorService;
import com.bekzodkeldiyarov.bookshop.service.Book2AuthorService;
import com.bekzodkeldiyarov.bookshop.service.BookService;
import com.bekzodkeldiyarov.bookshop.security.BookstoreUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class DataBootstrap implements CommandLineRunner {
    private final BookService bookService;
    private final AuthorService authorService;
    private final Book2AuthorService book2AuthorService;
    private final BookstoreUserService bookstoreUserService;


    public DataBootstrap(BookService bookService, AuthorService authorService, Book2AuthorService book2AuthorService, BookstoreUserService bookstoreUserService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.book2AuthorService = book2AuthorService;
        this.bookstoreUserService = bookstoreUserService;
    }

    public void bootstrapBooksAndAuthors() {
        Author dostoevskiy = Author.builder().firstName("Fedor").lastName("Dostoevski").build();
        Book idiot = Book.builder().title("Idiot").price(12.0).priceOld(11.0).build();

        Book2Author book2Author = new Book2Author();
        book2Author.setAuthor(dostoevskiy);
        book2Author.setBook(idiot);
        book2Author.setSortIndex(0);

        authorService.save(dostoevskiy);
        bookService.save(idiot);
        book2AuthorService.save(book2Author);
    }

    public void loadNewUsers() throws UserAlreadyExistsException {
        bookstoreUserService.registerNewUser(RegistrationForm.builder().email("bekzod@gmail.com").name("bekzodkeldiyarov").password("bekzod").phone("122222222").build());
    }

    @Override
    public void run(String... args) throws Exception {
        bootstrapBooksAndAuthors();
        loadNewUsers();
        log.info("Data bootstrapped...");

    }
}
