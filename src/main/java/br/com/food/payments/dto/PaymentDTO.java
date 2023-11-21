package br.com.food.payments.dto;

import java.math.BigDecimal;

import br.com.food.payments.domain.enums.Status;

public record PaymentDTO(
        Long id,
        BigDecimal value,
        String name,
        String number,
        String expirationDate,
        String code,
        Status status,
        Long orderId,
        Long paymentMethodId) {

}
