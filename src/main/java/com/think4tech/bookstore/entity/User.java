//package com.think4tech.bookstore.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//@Entity
//@Table(name = "users")
//public class User extends BaseEntity{
//
//    @Column(name = "full_name", nullable = false)
//    private String fullName;
//
//    @Column(name = "email", nullable = false, unique = true)
//    private String email;
//
//    @Column(name = "password_hash", nullable = false)
//    private String passwordHash;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @Column(name = "address")
//    private String address;
//
//    @Column(name = "status", nullable = false)
//    private String status;
//
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
//
//}
