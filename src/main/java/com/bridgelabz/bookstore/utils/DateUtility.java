package com.bridgelabz.bookstore.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateUtility {
	private DateUtility() {
	}

	public static LocalDateTime today() {
		return LocalDateTime.now(ZoneId.systemDefault());
	}

}
