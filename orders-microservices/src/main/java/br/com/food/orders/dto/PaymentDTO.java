package br.com.food.orders.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentDTO {
        private Long id;
        private BigDecimal value;
        private String name;
        private String number;
        private String expirationDate;
        private String code;
        private PaymentStatus status;
        private Long orderId;
        private Long paymentMethodId;
}
