package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.exception.SearchWordIsNullException;
import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.model.BookPageDto;
import com.bekzodkeldiyarov.bookshop.model.SearchWordDto;
import com.bekzodkeldiyarov.bookshop.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class MainPageController {

    private final BookService bookService;

    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0, 10).getContent();
    }

    @ModelAttribute("searchResults")
    public List<Book> searchResult() {
        return new ArrayList<>();
    }

    @GetMapping("")
    public String getMainPage() {
        log.info("Main page was requested to render...");
        return "index";
    }

    @GetMapping("/genres")
    public String getGenresPage() {
        log.info("getGenresPage() called");
        return "genres/index";
    }

    @GetMapping("/books/recommended")
    @ResponseBody
    public BookPageDto getPageOfBooks(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List<Book> books = bookService.getPageOfRecommendedBooks(offset, limit).getContent();
        return new BookPageDto(books.size(), books);
    }

    @GetMapping({"/search", "/search/{searchWord}"})
    public String getSearchPage(@PathVariable(value = "searchWord", required = false) SearchWordDto searchWord, Model model) throws SearchWordIsNullException {
        if (searchWord != null) {
            model.addAttribute("searchWordDto", searchWord);
            model.addAttribute("searchResults", bookService.getPageOfSearchResultBooks(searchWord.getWord(), 0, 20).getContent());
            return "search/index";
        } else {
            throw new SearchWordIsNullException("Cannot search in null");
        }
    }

    @GetMapping("/search/page/{searchWord}")
    @ResponseBody
    public BookPageDto getSearchPageBooks(@RequestParam Integer offset,
                                          @RequestParam Integer limit,
                                          @PathVariable SearchWordDto searchWord) {
        List<Book> books = bookService.getPageOfSearchResultBooks(searchWord.getWord(), offset, limit).getContent();
        return new BookPageDto(books.size(), books);
    }
}
