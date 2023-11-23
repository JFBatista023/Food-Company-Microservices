package br.com.food.orders.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.food.orders.domain.entity.Order;
import br.com.food.orders.domain.enums.Status;
import br.com.food.orders.dto.OrderDTO;
import br.com.food.orders.dto.StatusDTO;
import br.com.food.orders.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.COMPLETED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order completedOrder = orderRepository.save(order);

        return modelMapper.map(completedOrder, OrderDTO.class);
    }

    public OrderDTO updateStatus(Long id, StatusDTO dto) {

        Order order = orderRepository.findByIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        orderRepository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void approvePaymentOrder(Long id) {

        Order order = orderRepository.findByIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        orderRepository.updateStatus(Status.PAID, order);
    }
}
