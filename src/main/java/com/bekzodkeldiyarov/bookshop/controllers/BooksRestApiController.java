package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.exception.WrongParameterException;
import com.bekzodkeldiyarov.bookshop.model.ApiResponse;
import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.service.BookService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "rest api for books")
@Slf4j
public class BooksRestApiController {
    private BookService bookService;

    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    public ResponseEntity<ApiResponse<Book>> getBookByAuthorName(@RequestParam("name") String name) throws WrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByAuthorName(name);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setLocalDateTime(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam("title") String title) throws WrongParameterException {
        return ResponseEntity.ok(bookService.getBooksByTitle(title));
    }

    @GetMapping("/books/by-price-between")
    public ResponseEntity<List<Book>> getBooksByPriceBetween(@RequestParam("min") Double min, @RequestParam("max") Double max) {
        return ResponseEntity.ok(bookService.getBooksByPriceBetween(min, max));
    }

    @GetMapping("/books/by-price")
    public ResponseEntity<List<Book>> getBooksByPrice(@RequestParam("price") Double price) throws WrongParameterException {
        return ResponseEntity.ok(bookService.getBooksByPrice(price));
    }

    @GetMapping("/books/bestsellers")
    public ResponseEntity<List<Book>> getBooksByBestseller() throws WrongParameterException {
        return ResponseEntity.ok(bookService.getBestsellers());
    }

    @GetMapping("/books/with-max-discount")
    public ResponseEntity<List<Book>> getBooksWithMaxDiscount() {
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("/books/by-slug")
    public ResponseEntity<ApiResponse<Book>> getBookBySlug(@RequestParam("slug") String slug) throws WrongParameterException {
        Book book = bookService.getBookBySlug(slug);
        List<Book> data = new ArrayList<>();
        data.add(book);
        ApiResponse<Book> response = new ApiResponse<>();
        response.setData(data);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setStatus(HttpStatus.OK);
        response.setLocalDateTime(LocalDateTime.now());
        log.error(book.getId().toString());
        log.error(book.toString());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception e) {
        return new ResponseEntity<>(new ApiResponse<>(HttpStatus.BAD_REQUEST, "Missing one or more Parameters", e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> wrongParameterException(Exception e) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad Parameter value...", e), HttpStatus.BAD_REQUEST);
    }
}
