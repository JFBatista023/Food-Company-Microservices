package br.com.food.payments.dto;

import java.math.BigDecimal;
import java.util.List;

import br.com.food.payments.domain.entity.OrderItem;
import br.com.food.payments.domain.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {
        private Long id;
        private BigDecimal value;
        private String name;
        private String number;
        private String expirationDate;
        private String code;
        private Status status;
        private Long orderId;
        private Long paymentMethodId;
        private List<OrderItem> items;
}
