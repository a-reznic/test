package com.reznic.config;

import com.reznic.producer.FileProducer;
import com.reznic.service.MessageServiceImpl;

import java.util.concurrent.ExecutorService;

/**
 * Created on 19.06.2020.
 */
public class Config {
	private int    threadCount = 5;
	private String fileName;

	public Config(String fileName) {
		this.fileName = fileName;
	}

	public Config(int threadCount, String fileName) {
		this.threadCount = threadCount;
		this.fileName = fileName;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public FileProducer getProducer(ExecutorService executorService) {
		return new FileProducer(getFileName(), new MessageServiceImpl(executorService));
	}
}
