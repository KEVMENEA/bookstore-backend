package com.think4tech.bookstore.spec;

import com.think4tech.bookstore.entity.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public class BookSpec implements Specification<Book> {
    @Nullable
    @Override
    public Predicate toPredicate(Root<Book> Book, @Nullable CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
