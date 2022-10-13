package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.exception.BookNotFoundException;
import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.service.BookService;
import com.bekzodkeldiyarov.bookshop.service.ResourceStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequestMapping("/books")
@Slf4j
public class BooksController {

    private final BookService bookService;
    private final ResourceStorage storage;


    public BooksController(BookService bookService, ResourceStorage storage) {
        this.bookService = bookService;
        this.storage = storage;
    }

    @GetMapping("/{slug}")
    public String getBookPage(@PathVariable("slug") String slug, Model model) throws BookNotFoundException {
        Book bookToReturn = bookService.getBookBySlug(slug);
        if (bookToReturn != null) {
            model.addAttribute("book", bookToReturn);
            return "books/slug";
        } else {
            log.error("Cannot find book with slug: " + slug);
            throw new BookNotFoundException("Book with slug " + slug + " cannot be found");
        }
    }


    @PostMapping("/{slug}/img/save")
    public String saveNewBook(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug) throws IOException {
        String savePath = storage.saveNewImage(file, slug);
        Book bookToUpdate = bookService.getBookBySlug(slug);
        bookToUpdate.setImage(savePath);
        bookService.save(bookToUpdate);

        return ("redirect:/books/" + slug);
    }

    @GetMapping("/download/{hash}")
    public ResponseEntity<ByteArrayResource> bookFile(@PathVariable String hash) throws IOException {

        Path path = storage.getBookFilePath(hash);
        log.error("path of file is " + path);

        MediaType mediaType = storage.getBookFileMime(hash);
        log.error("mime of then file if " + mediaType);

        byte[] data = storage.getBookFileByteArray(hash);
        log.error("book file len is " + data.length);

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString()).contentType(mediaType).contentLength(data.length)

                .body(new ByteArrayResource(data));

    }

}
