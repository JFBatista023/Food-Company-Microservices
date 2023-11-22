package br.com.food.orders.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.food.orders.domain.enums.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemDTO> items = new ArrayList<>();

}
