package com.reznic.producer;

import com.reznic.data.Message;

/**
 * Created on 19.06.2020.
 */
public interface Producer {
	void add(Message message);

	void start() throws Exception;
}
