package com.bridgelabz.bookstore.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.Mail;

@Component
public class RabbitMQSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${rmq.rube.exchange}")
	private String exchange;

	@Value("${rmq.rube.routingkey}")
	private String routingKey;

	public void send(Mail mail) {
		rabbitTemplate.convertAndSend(exchange, routingKey, mail);
	}
}
