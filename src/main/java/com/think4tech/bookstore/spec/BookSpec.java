package com.think4tech.bookstore.spec;

import com.think4tech.bookstore.entity.Admin;
import com.think4tech.bookstore.entity.Book;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BookSpec {

    private BookSpec() {
    }

    public static Specification<Book> filter(BookFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                query.distinct(true);
            }

            if (hasText(filter.getKeyword())) {
                String value = "%" + filter.getKeyword().trim().toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), value),
//                        cb.like(cb.lower(root.get("description")), value),
//                        cb.like(cb.lower(root.get("isbn")), value),
                        cb.like(cb.lower(root.get("language")), value),
                        cb.like(cb.lower(root.get("format")), value),
                        cb.like(cb.lower(root.get("status")), value)
                ));
            }

            // category cannot be applied yet because Book entity does not have category field/relation
            // keep for future extension if your real schema has category
            if (hasText(filter.getCategory())) {
                // Example only if later you add category relation:
                // Join<Book, Category> categoryJoin = root.join("category", JoinType.LEFT);
                // predicates.add(cb.like(cb.lower(categoryJoin.get("name")),
                //         "%" + filter.getCategory().trim().toLowerCase() + "%"));
            }

            if (hasText(filter.getAuthor())) {
                String value = "%" + filter.getAuthor().trim().toLowerCase() + "%";

                Join<Book, Admin> adminJoin = root.join("createdByAdmin", JoinType.LEFT);

                predicates.add(cb.or(
                        cb.like(cb.lower(adminJoin.get("fullName")), value),
                        cb.like(cb.lower(adminJoin.get("email")), value)
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}