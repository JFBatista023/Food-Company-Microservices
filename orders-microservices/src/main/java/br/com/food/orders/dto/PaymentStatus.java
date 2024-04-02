package br.com.food.orders.dto;

public enum PaymentStatus {
    CREATED,
    CONFIRMED,
    CONFIRMED_WITHOUT_INTEGRATION,
    CANCELED;
}
