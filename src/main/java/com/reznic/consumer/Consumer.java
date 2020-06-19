package com.reznic.consumer;

import com.reznic.data.Message;

/**
 * Created on 19.06.2020.
 */
interface Consumer extends Runnable {
	void doWork(Message message);
}
