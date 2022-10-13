package com.bekzodkeldiyarov.bookshop.model;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    private String name;
    private String phone;
    private String email;
    private String password;
}
