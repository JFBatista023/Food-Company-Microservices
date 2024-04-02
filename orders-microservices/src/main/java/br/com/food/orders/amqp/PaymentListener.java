package br.com.food.orders.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    @RabbitListener(queues = "payments.created")
    public void receiveMessage(Message message) {
        System.out.println(message.toString());
    }
}
