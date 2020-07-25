package com.bridgelabz.bookstore.config;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApplicationConfiguration {
	private static MessageSourceAccessor messageSourceAccessor;

	@PostConstruct
	private static void initMessageSourceAccessor() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages");
		messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
	}

	public static MessageSourceAccessor getMessageAccessor() {
		return messageSourceAccessor;
	}

}