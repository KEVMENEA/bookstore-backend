package com.think4tech.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRequest {
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String status;
}
