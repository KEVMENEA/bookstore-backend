package com.think4tech.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "books",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_book_slug", columnNames = "slug")
        })
@AttributeOverride(name = "id", column = @Column(name = "book_id"))
public class Book extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "language")
    private String language;

    @Column(name = "cover_image_url")
    private String coverImageUrl;

    @Column(name = "digital_file_url")
    private String digitalFileUrl;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_free", nullable = false)
    private Boolean isFree;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Column(name = "format")
    private String format;

    @Column(name = "status")
    private String status;

    @Column(name = "average_rating")
    private Double averageRating;

    @Column(name = "total_reviews")
    private Integer totalReviews;

    @ManyToOne
    @JoinColumn(name = "created_by_admin_id")
    private Admin createdByAdmin;

}
