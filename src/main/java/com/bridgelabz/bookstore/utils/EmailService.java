package com.bridgelabz.bookstore.utils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.model.Mail;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;

	public static final String SENDER_EMAIL_ID = "do-not@reply.com";

	public void sendSimpleMessage(final Mail mail) {
		final JavaMailSenderImpl sender = new JavaMailSenderImpl();
		try {
			final MimeMessage message = sender.createMimeMessage();
			final MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			final InternetAddress from = new InternetAddress(SENDER_EMAIL_ID);
			message.setContent(mail.getContent(), "text/html");
			helper.setTo(mail.getTo());
			helper.setSubject(mail.getSubject());
			helper.setFrom(from);
			helper.setText(mail.getContent(), true);
			emailSender.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}