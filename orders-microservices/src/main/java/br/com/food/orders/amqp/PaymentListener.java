package br.com.food.orders.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.food.orders.dto.PaymentDTO;

@Component
public class PaymentListener {

    @RabbitListener(queues = "payments.created")
    public void receiveMessage(PaymentDTO payment) {
        String message = """
                Payment ID: %s
                Number: %s
                Value $: %s
                Status: %s
                """.formatted(payment.getId(), payment.getNumber(), payment.getValue(), payment.getStatus());

        System.out.println(message);
    }
}
