package com.reznic;

import com.reznic.config.Config;
import com.reznic.producer.Producer;
import com.reznic.utils.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on 19.06.2020.
 */
public class Main {
	private static Logger log = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		Config config = initConfig(args);

		log.info(String.format("%s - STARTING - Consumers: %s; File: %s", Utils.getTimeString(), config.getThreadCount(), config.getFileName()));

		ExecutorService executorService = Executors.newFixedThreadPool(config.getThreadCount());
		Producer producer = config.getProducer(executorService);

		try {
			producer.start();
		} catch (Exception e) {
			e.printStackTrace();
			log.severe(e.getLocalizedMessage());
		}
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			Utils.sleep(5);
		}
		log.info(String.format("%s - END", Utils.getTimeString()));
	}

	private static Config initConfig(String[] args) {
		String fileName = "in.txt";
		int threadCount = 5;

		if (args.length > 1) {
			String threads = args[0];
			fileName = args[1];
			try {
				threadCount = Integer.parseInt(threads);
				if (threadCount < 1) {
					threadCount = 1;
				}
			} catch (Exception e) {
				log.log(Level.SEVERE, "Invalid thread count value - using default value = 5", e);
			}
		}

		Config config = new Config(fileName);
		config.setThreadCount(threadCount);
		return config;
	}

}
