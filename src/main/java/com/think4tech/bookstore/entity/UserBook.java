package com.think4tech.bookstore.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "user_books",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_book_user_book", columnNames = {"user_id", "book_id"})
        }
)
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_book_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "source", length = 30, nullable = false)
    private String source; // FREE, PURCHASE, ADMIN_GIFT

    @Column(name = "granted_at", nullable = false)
    private LocalDateTime grantedAt;

    @Column(name = "access_status", length = 20, nullable = false)
    private String accessStatus; // ACTIVE, REVOKED
}