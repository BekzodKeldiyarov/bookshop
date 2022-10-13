package com.bekzodkeldiyarov.bookshop.security;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class ContactConfirmationPayload {
    private String contact;
    private String code;
}
