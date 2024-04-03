package br.com.food.payments.controller;

import br.com.food.payments.dto.PaymentDTO;
import br.com.food.payments.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final RabbitTemplate rabbitTemplate;

    public PaymentController(PaymentService paymentService, RabbitTemplate rabbitTemplate) {
        this.paymentService = paymentService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public Page<PaymentDTO> list(@PageableDefault(size = 10) Pageable pagination) {
        return paymentService.getAllPayments(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> detail(@PathVariable @NotNull Long id) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);

        return ResponseEntity.ok(paymentDTO);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> create(@RequestBody @Valid PaymentDTO paymentDTO, UriComponentsBuilder uBuilder) {
        PaymentDTO payment = paymentService.createPayment(paymentDTO);
        URI path = uBuilder.path("/payments/{id}").buildAndExpand(payment.getId()).toUri();

        rabbitTemplate.convertAndSend("payments.ex", "", payment);

        return ResponseEntity.created(path).body(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable @NotNull Long id,
                                             @RequestBody @Valid PaymentDTO paymentDTO) {
        PaymentDTO paymentUpdated = paymentService.updatePayment(id, paymentDTO);

        return ResponseEntity.ok(paymentUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> delete(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "updateOrder", fallbackMethod = "authorizedPaymentWithPendingIntegration")
    public void confirm(@PathVariable @NotNull Long id) {
        paymentService.confirmPayment(id);
    }

    public void authorizedPaymentWithPendingIntegration(Long id, Exception e) {
        paymentService.updateStatus(id);
    }
}
