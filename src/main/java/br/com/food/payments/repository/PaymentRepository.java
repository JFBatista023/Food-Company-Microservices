package br.com.food.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.food.payments.domain.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
