package com.satyam.SpringEcom.model.dto;

import java.math.BigDecimal;

public record OrderItemResponse(String productName, int quantity, int totalPrice) {

}
