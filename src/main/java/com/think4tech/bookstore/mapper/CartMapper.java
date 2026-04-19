package com.think4tech.bookstore.mapper;

import com.think4tech.bookstore.dto.CartItemResponse;
import com.think4tech.bookstore.dto.CartResponse;
import com.think4tech.bookstore.entity.Cart;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public abstract class CartMapper {

    @Autowired
    protected CartItemMapper cartItemMapper;

    public CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(cartItemMapper::toResponse)
                .toList();

        double totalAmount = items.stream()
                .mapToDouble(CartItemResponse::getTotalPrice)
                .sum();

        return  new CartResponse(items, totalAmount);
    }
}