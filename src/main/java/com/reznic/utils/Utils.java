package com.reznic.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created on 19.06.2020.
 */
public class Utils {
	private static final String PATTERN = "HH:mm:ss.SSS";

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String getTimeString() {
		return getTimeString(LocalDateTime.now());
	}

	public static String getTimeString(LocalDateTime time) {
		return time.format(DateTimeFormatter.ofPattern(PATTERN));
	}
}
