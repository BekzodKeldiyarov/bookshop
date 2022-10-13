package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.model.Book;
import com.bekzodkeldiyarov.bookshop.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
@Slf4j
public class BookCartController {

    private final BookRepository bookRepository;

    public BookCartController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @ModelAttribute("bookCart")
    public List<Book> getBooksCart() {
        return new ArrayList<>();
    }


    @GetMapping("/cart")
    public String getBooksCartPage(@CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            log.info("Cookies are present: " + cartContents);
            model.addAttribute("isCartEmpty", false);

            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<Book> books = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute("bookCart", books);
        }
        return "cart";
    }

    @PostMapping("/changeBookStatus/{slug}")
    public String addBookToCart(@PathVariable String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {
        log.info("Adding to cookie slug " + slug);
        if (cartContents == null || cartContents.equals("")) {
            Cookie cookie = new Cookie("cartContents", slug);
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else if (!cartContents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(cartContents).add(slug);
            Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
            cookie.setPath("/books");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        }
        return "redirect:/books/" + slug;
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}")
    public String removeBookFromCart(@PathVariable String slug, @CookieValue(name = "cartContents", required = false) String cartContents, HttpServletResponse response, Model model) {
        if (cartContents == null || cartContents.equals("")) {
            model.addAttribute("isCartEmpty", true);
        } else {
            cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
            cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() - 1) : cartContents;
            String[] cookieSlugs = cartContents.split("/");
            List<String> values = new ArrayList<>(Arrays.asList(cookieSlugs));
            values.remove(slug);
            if (values.size() > 0) {
                Cookie cookie = new Cookie("cartContents", String.join("/", values));
                cookie.setPath("/books");
                response.addCookie(cookie);
                model.addAttribute("isCartEmpty", false);
            }
            model.addAttribute("isCartEmpty", true);
        }
        return "redirect:/books/cart";
    }
}
