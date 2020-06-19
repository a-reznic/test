package com.reznic.parser;

import com.reznic.data.Message;

/**
 * Created on 19.06.2020.
 */
public class MessageParser {
	public static Message parse(String inputData) {
		if (inputData == null || inputData.isEmpty()) {
			throw new RuntimeException(String.format("invalid message format[%s]", inputData));
		}
		Message message = new Message(inputData);

		String[] split = inputData.split("\\|", 3);

		message.setId(split[0]);
		message.setProcessTime(Long.parseLong(split[1]));
		message.setPayload(split[2]);

		return message;
	}
}
