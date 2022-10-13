package com.bekzodkeldiyarov.bookshop.security;

import com.bekzodkeldiyarov.bookshop.exception.NoUserFoundException;
import com.bekzodkeldiyarov.bookshop.exception.UserAlreadyExistsException;
import com.bekzodkeldiyarov.bookshop.model.RegistrationForm;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class AuthController {
    private final BookstoreUserService bookstoreUserService;

    public AuthController(BookstoreUserService bookstoreUserService) {
        this.bookstoreUserService = bookstoreUserService;
    }

    @GetMapping("/signin")
    private String getLoginPage() {
        return "signin";
    }

    @GetMapping("/register")
    private String getRegisterPage(Model model) {
        model.addAttribute("user", new RegistrationForm());
        return "signup";
    }

    @PostMapping("/register")
    private String handleRegisterForm(RegistrationForm registrationForm, Model model) throws UserAlreadyExistsException {
        log.info(RegistrationForm.class.getSimpleName() + " " + registrationForm.toString());
        bookstoreUserService.registerNewUser(registrationForm);
        model.addAttribute("registeredOk", true);
        return "redirect:/signin";
    }

    @PostMapping("/requestContactConfirmation")
    @ResponseBody
    private ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    @PostMapping("/approveContact")
    @ResponseBody
    private ContactConfirmationResponse handleApproveContact(@RequestBody ContactConfirmationPayload payload) {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }


    @PostMapping("/signin")
    @ResponseBody
    private ContactConfirmationResponse handleLogin(@RequestBody ContactConfirmationPayload payload,
                                                    HttpServletResponse response) throws NoUserFoundException {
        ContactConfirmationResponse contactConfirmationResponse = bookstoreUserService.jwtLogin(payload);
        Cookie cookie = new Cookie("token", contactConfirmationResponse.getResult());

        response.addCookie(cookie);
        return contactConfirmationResponse;
    }

    @GetMapping("/my")
    private String getMyBooks() {
        return "cart";
    }

    @GetMapping("/profile")
    private String getProfilePage(Model model) {
        model.addAttribute("currentUser", bookstoreUserService.getCurrentUser());
        return "profile";
    }

}
