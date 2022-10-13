package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.model.Author;
import com.bekzodkeldiyarov.bookshop.service.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@Api(description = "author api")
public class AuthorsController {

    private final AuthorService authorService;

    public AuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> getAllAuthors() {
        log.info(authorService.getAuthorsMap().toString());
        return authorService.getAuthorsMap();
    }

    @GetMapping("/authors")
    @ApiOperation("Method for getting all author")
    public String getAuthorsPage() {
        return "/authors/index";
    }
}
