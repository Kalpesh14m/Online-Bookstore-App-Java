package com.bridgelabz.bookstore.utils;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.Mail;

@Component
public class RabbitMQReceiver {

	@Autowired
	private EmailService email;

	@RabbitListener(queues = "${rmq.rube.queue}")
	public void recievedMessage(Mail mail) {
		email.sendSimpleMessage(mail);
	}
}