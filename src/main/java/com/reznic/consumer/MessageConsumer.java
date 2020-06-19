package com.reznic.consumer;

import com.reznic.data.Message;
import com.reznic.utils.Utils;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.reznic.consumer.CONSUMER_STATE.INIT;

/**
 * Created on 19.06.2020.
 */
public class MessageConsumer implements Consumer {
	private Message message;
	private TaskResult result;
	private CONSUMER_STATE internalState = INIT;

	private Logger log = Logger.getLogger(getClass().getName());

	public MessageConsumer(Message message, TaskResult result) {
		this.message = message;
		this.result = result;
	}

	public Message getMessage() {
		return message;
	}

	@Override
	public void run() {
		consume(message);
	}

	private void consume(Message message) {
		LocalDateTime startTime = LocalDateTime.now();
		doWork(message);
		LocalDateTime finishTime = LocalDateTime.now();
		printInfo(message, startTime, finishTime);
		internalState = CONSUMER_STATE.FINISHED;
		result.onTaskComplete(this);
	}

	@Override
	public void doWork(Message message) {
		try {
			Thread.sleep(message.getProcessTime());
		} catch (InterruptedException e) {
			log.log(Level.SEVERE, "ext", e);
		}
	}

	private void printInfo(Message message, LocalDateTime startTime, LocalDateTime finishTime) {
		String format = String.format("%s  Thread: %s[%s];  Start: %s;  End: %s",
		  String.format("%-20s", message.getOriginalMessage()),
		  Thread.currentThread().getId(),
		  Thread.currentThread().getName(),
		  Utils.getTimeString(startTime),
		  Utils.getTimeString(finishTime));
		//				log.info(format);
		System.out.println(format);
	}

	public CONSUMER_STATE getInternalState() {
		return internalState;
	}

	public void setInternalState(CONSUMER_STATE internalState) {
		this.internalState = internalState;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MessageConsumer that = (MessageConsumer) o;

		return message.equals(that.message);
	}

	@Override
	public int hashCode() {
		return message.hashCode();
	}

	@Override
	public String toString() {
		return "MessageConsumer{" +
		  "id=" + message.getId() +
		  ", state=" + internalState +
		  ", payload=" + message.getPayload() +
		  '}';
	}
}
