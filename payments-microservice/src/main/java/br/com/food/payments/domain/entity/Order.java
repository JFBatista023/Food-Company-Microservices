package br.com.food.payments.domain.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private List<OrderItem> items;
}
