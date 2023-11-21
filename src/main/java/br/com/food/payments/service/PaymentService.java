package br.com.food.payments.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.food.payments.domain.entity.Payment;
import br.com.food.payments.domain.enums.Status;
import br.com.food.payments.dto.PaymentDTO;
import br.com.food.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    private ModelMapper modelMapper;

    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    public Page<PaymentDTO> getAllPayments(Pageable pagination) {
        return paymentRepository.findAll(pagination).map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO createPayment(PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO paymentDTO) {
        Payment payment = modelMapper.map(paymentDTO, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
