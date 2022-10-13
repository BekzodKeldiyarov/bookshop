package com.bekzodkeldiyarov.bookshop.service;


import com.bekzodkeldiyarov.bookshop.exception.WrongParameterException;
import com.bekzodkeldiyarov.bookshop.model.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book save(Book book);

    List<Book> getBooksByAuthorName(String name) throws WrongParameterException;

    List<Book> getBooksByTitle(String title) throws WrongParameterException;

    List<Book> getBooksByPriceBetween(Double min, Double max);

    List<Book> getBooksByPrice(Double price) throws WrongParameterException;

    List<Book> getBestsellers() throws WrongParameterException;

    List<Book> getBooksWithMaxDiscount();

    Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit);

    Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit);

    Book getBookBySlug(String slug);
}
