package com.bekzodkeldiyarov.bookshop.controllers;

import com.bekzodkeldiyarov.bookshop.exception.BookNotFoundException;
import com.bekzodkeldiyarov.bookshop.exception.NoUserFoundException;
import com.bekzodkeldiyarov.bookshop.exception.SearchWordIsNullException;
import com.bekzodkeldiyarov.bookshop.security.ContactConfirmationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {
    @ExceptionHandler
    public String handleSearchWordIsNullException(SearchWordIsNullException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("searchWordError", e);
        return "redirect:/";
    }

    @ExceptionHandler
    public String handleBookNotFoundException(BookNotFoundException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e);
        return "redirect:/";
    }

    @ExceptionHandler
    @ResponseBody
    public ContactConfirmationResponse handleNoUserFoundException(NoUserFoundException e, RedirectAttributes redirectAttributes) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("false");
        redirectAttributes.addFlashAttribute("test", "test message here");
        redirectAttributes.addFlashAttribute("error", e);
        return response;
    }
}
