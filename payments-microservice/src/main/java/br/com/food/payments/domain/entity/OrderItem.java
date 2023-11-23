package br.com.food.payments.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Long id;
    private Integer quantity;
    private String description;
}
