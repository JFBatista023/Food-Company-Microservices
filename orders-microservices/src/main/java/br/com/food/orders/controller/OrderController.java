package br.com.food.orders.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.food.orders.dto.OrderDTO;
import br.com.food.orders.dto.StatusDTO;
import br.com.food.orders.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public List<OrderDTO> list() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> detail(@PathVariable @NotNull Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);

        return ResponseEntity.ok(orderDTO);
    }

    @GetMapping("/port")
    public String returnPort(@Value("${local.server.port}") String port) {
        return String.format("Request answered by port instance %s", port);
    }

    @PostMapping()
    public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
        OrderDTO order = orderService.createOrder(dto);

        URI path = uriBuilder.path("/orders/{id}").buildAndExpand(order.getId()).toUri();

        return ResponseEntity.created(path).body(order);

    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateStatus(@PathVariable Long id, @RequestBody StatusDTO status) {
        OrderDTO dto = orderService.updateStatus(id, status);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}/paid")
    public ResponseEntity<Void> updateStatusToPaid(@PathVariable @NotNull Long id) {
        orderService.approvePaymentOrder(id);

        return ResponseEntity.ok().build();

    }
}
