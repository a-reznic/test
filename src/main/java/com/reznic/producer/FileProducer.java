package com.reznic.producer;

import com.reznic.data.Message;
import com.reznic.parser.MessageParser;
import com.reznic.service.MessageService;
import com.reznic.utils.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created on 19.06.2020.
 */
public class FileProducer implements Producer {
	private String fileName;
	private MessageService service;

	public FileProducer(String fileName, MessageService service) {
		this.fileName = fileName;
		this.service = service;
	}

	@Override
	public void add(Message message) {
		if (message.hasId()) {
			service.addTask(message);
		} else {
			Utils.sleep(message.getProcessTime());
		}
	}

	@Override
	public void start() throws Exception {
		Path path = Paths.get(fileName);
		File file = path.toFile();

		if (file.exists()) {
			if (file.canRead()) {
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					String line;
					while ((line = br.readLine()) != null) {
						add(MessageParser.parse(line));
					}
				}
			}
		} else {
			throw new FileNotFoundException("File [" + file.getAbsolutePath() + "] not found");
		}
	}
}
