package com.satyam.SpringEcom.model.dto;

public record OrderItemRequest(
        Long productId,
        int quantity) {

}
