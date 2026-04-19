package com.think4tech.bookstore.auth;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;

}