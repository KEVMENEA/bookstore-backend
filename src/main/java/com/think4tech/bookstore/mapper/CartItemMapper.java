package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.CartItemResponse;
import com.think4tech.bookstore.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    @Mapping(target = "imageUrl", source = "book.coverImageUrl")
    @Mapping(target = "price", expression = "java(item.getUnitPrice() != null ? item.getUnitPrice().doubleValue() : 0.0)")
    @Mapping(target = "totalPrice", expression = "java(calculateTotalPrice(item))")
    @Mapping(target = "author", expression = "java(mapAuthors(item))")
    CartItemResponse toResponse(CartItem item);

    default Double calculateTotalPrice(CartItem item) {
        if (item == null || item.getUnitPrice() == null || item.getQuantity() == null) {
            return 0.0;
        }

        return item.getUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()))
                .doubleValue();
    }

    default String mapAuthors(CartItem item) {
        if (item == null || item.getBook() == null || item.getBook().getAuthors() == null || item.getBook().getAuthors().isEmpty()) {
            return "Unknown Author";
        }
        return item.getBook().getAuthors().stream()
                .map(author -> author.getName())
                .reduce((a, b) -> a + ", " + b)
                .orElse("Unknown Author");
    }
}
