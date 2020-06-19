package com.reznic.consumer;

public interface TaskResult {
	void onTaskComplete(MessageConsumer message);
}
