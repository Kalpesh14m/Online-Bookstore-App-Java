package com.bridgelabz.bookstore.utils;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bridgelabz.bookstore.model.Mail;
import com.bridgelabz.bookstore.model.User;

@Component
public class MailTempletService {

	@Autowired
	private RabbitMQSender rabbitMQSender;

	@Autowired
	private Mail mail;

	private String templateMSG = "";

	public void getTemplate(User request, String token, String path) throws IOException {

		if (templateMSG.equals("")) {
			templateMSG = Template.readContentFromTemplet(path);
		}

		templateMSG = templateMSG.replaceAll(Pattern.quote("$%name%"), request.getName());
		templateMSG = templateMSG.replaceAll(Pattern.quote("$%token%"), token);

		mail.setTo(request.getEmail());
		mail.setFrom(EmailService.SENDER_EMAIL_ID);
		mail.setSubject(request.getName());
		mail.setContent(templateMSG);
		mail.setSentDate(Date.from(DateUtility.today().atZone(ZoneId.systemDefault()).toInstant()));

		rabbitMQSender.send(mail);

		templateMSG = "";
	}

}