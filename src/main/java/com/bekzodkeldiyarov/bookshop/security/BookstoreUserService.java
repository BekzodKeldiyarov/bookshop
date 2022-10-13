package com.bekzodkeldiyarov.bookshop.security;

import com.bekzodkeldiyarov.bookshop.exception.NoUserFoundException;
import com.bekzodkeldiyarov.bookshop.exception.UserAlreadyExistsException;
import com.bekzodkeldiyarov.bookshop.model.*;
import com.bekzodkeldiyarov.bookshop.security.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookstoreUserService {
    private final BookstoreUserRepository bookstoreUserRepository;
    private final AuthenticationManager authenticationManager;
    private final BookstoreUserDetailsService bookstoreUserDetailsService;
    private final JWTUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public BookstoreUserService(BookstoreUserRepository bookstoreUserRepository, AuthenticationManager authenticationManager, BookstoreUserDetailsService bookstoreUserDetailsService, JWTUtil jwtUtil) {
        this.bookstoreUserRepository = bookstoreUserRepository;
        this.authenticationManager = authenticationManager;
        this.bookstoreUserDetailsService = bookstoreUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    public BookstoreUser registerNewUser(RegistrationForm registrationForm) throws UserAlreadyExistsException {
        if (bookstoreUserRepository.findByEmail(registrationForm.getEmail()) == null) {
            BookstoreUser bookstoreUser = new BookstoreUser();
            bookstoreUser.setName(registrationForm.getName());
            bookstoreUser.setEmail(registrationForm.getEmail());
            bookstoreUser.setPhone(registrationForm.getPhone());
            bookstoreUser.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
            bookstoreUserRepository.save(bookstoreUser);
            return bookstoreUser;
        } else {
            throw new UserAlreadyExistsException("Please, enter another email");
        }

    }

    public ContactConfirmationResponse login(ContactConfirmationPayload payload) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        response.setResult("true");
        return response;
    }

    public ContactConfirmationResponse jwtLogin(ContactConfirmationPayload payload) throws NoUserFoundException {
        ContactConfirmationResponse response = new ContactConfirmationResponse();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getContact(), payload.getCode()));
            BookstoreUserDetails bookstoreUserDetails = (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(payload.getContact());
            log.info("Loaded by username in service is " + bookstoreUserDetails.getBookstoreUser());
            String token = jwtUtil.generateToken(bookstoreUserDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            response.setResult(token);
            return response;
        } catch (Exception e) {
            response.setResult("false");
            throw new NoUserFoundException("No user found with data provided");
        }
    }

    public BookstoreUser getCurrentUser() {
        BookstoreUserDetails bookstoreUserDetails = (BookstoreUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookstoreUserDetails.getBookstoreUser();
    }

}
