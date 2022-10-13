package com.bekzodkeldiyarov.bookshop.service;

import com.bekzodkeldiyarov.bookshop.exception.WrongParameterException;
import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    public final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getBooksByAuthorName(String name) throws WrongParameterException {
        if (name == null || name.length() <= 1) {
            throw new WrongParameterException("Wrong parameter for name passed...");
        } else {
            List<Book> data = bookRepository.findBooksByAuthorNameContaining(name);
            if (data.size() > 0) {
                return data;
            } else {
                throw new WrongParameterException("No data found for parameter passed...");
            }
        }
    }

    @Override
    public List<Book> getBooksByTitle(String title) throws WrongParameterException {
        if (title == null || title.length() <= 1) {
            throw new WrongParameterException("Wrong parameter for name passed...");
        } else {
            List<Book> data = bookRepository.findBookByTitleContaining(title);
            if (data.size() > 0) {
                return data;
            } else {
                throw new WrongParameterException("No data found for parameter passed...");
            }
        }
    }

    @Override
    public List<Book> getBooksByPriceBetween(Double min, Double max) {
        return bookRepository.findBooksByPriceOldBetween(min, max);
    }

    @Override
    public List<Book> getBooksByPrice(Double price) throws WrongParameterException {
        if (price == null || price < 0) {
            throw new WrongParameterException("Wrong parameter for name passed...");
        } else {
            List<Book> data = bookRepository.findBooksByPriceOldIs(price);
            if (data.size() > 0) {
                return data;
            } else {
                throw new WrongParameterException("No data found for parameter passed...");
            }
        }

    }

    @Override
    public List<Book> getBestsellers() throws WrongParameterException {
        List<Book> data = bookRepository.getBestsellers();
        if (data.size() > 0) {
            return data;
        } else {
            throw new WrongParameterException("No data found for bestsellers...");
        }
    }

    @Override
    public List<Book> getBooksWithMaxDiscount() {
        return bookRepository.getBooksWithMaxDiscount();
    }

    @Override
    public Page<Book> getPageOfRecommendedBooks(Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findAll(nextPage);
    }

    @Override
    public Page<Book> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.findBookByTitleContaining(searchWord, nextPage);
    }

    @Override
    public Book getBookBySlug(String slug) {
        log.info("called method getBooksBySlug: " + slug);
        return bookRepository.findBookBySlug(slug);
    }
}
