package com.bridgelabz.bookstore.utils;

import java.util.Random;

public class RandomUtility {

	private RandomUtility() {
		super();
	}

	private static Random rnd = new Random();

	public static String getRandomNumber() {
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

}
