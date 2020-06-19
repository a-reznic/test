package com.reznic.service;

import com.reznic.consumer.CONSUMER_STATE;
import com.reznic.consumer.MessageConsumer;
import com.reznic.consumer.TaskResult;
import com.reznic.data.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created on 19.06.2020.
 */
public class MessageServiceImpl implements MessageService, TaskResult {
	private ExecutorService executorService;
	private Map<String, ConcurrentLinkedQueue<MessageConsumer>> map = new ConcurrentHashMap<>();

	public MessageServiceImpl(ExecutorService executorService) {
		this.executorService = executorService;
	}

	@Override
	public void addTask(Message message) {
		ConcurrentLinkedQueue<MessageConsumer> list = new ConcurrentLinkedQueue<>();

		String id = message.getId();
		if (map.containsKey(id)) {
			list = map.get(id);
		}

		MessageConsumer task = new MessageConsumer(message, this);
		list.add(task);
		map.put(id, list);

		selectNextTask(id);
	}

	/**
	 * call then thread is finish work
	 *
	 * @param messageConsumer object
	 */
	@Override
	public void onTaskComplete(MessageConsumer messageConsumer) {
		selectNextTask(messageConsumer.getMessage().getId());
	}

	/**
	 * select next task from queue by id<br>
	 * if queue is empty try to find other task
	 *
	 * @param id message id
	 */
	private synchronized void selectNextTask(String id) {
		ConcurrentLinkedQueue<MessageConsumer> messageConsumers = map.get(id);

		for (MessageConsumer messageConsumer : messageConsumers) {
			switch (messageConsumer.getInternalState()) {
				case INIT:
					submitTask(messageConsumer);
					return;
				case STARTED:
					return;
				case FINISHED:
					messageConsumers.remove(messageConsumer);
					break;
			}
		}
	}

	private void submitTask(MessageConsumer task) {
		//		log.info("starting" + task);
		task.setInternalState(CONSUMER_STATE.STARTED);
		executorService.submit(task);
	}
}
