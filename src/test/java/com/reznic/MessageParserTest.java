package com.reznic;

import com.reznic.data.Message;
import com.reznic.parser.MessageParser;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 19.06.2020.
 */
public class MessageParserTest {

	@Test
	public void parse() {
		Message parse = MessageParser.parse("A|100|C");

		Assert.assertEquals(parse.getId(), "A");
		Assert.assertEquals(parse.getProcessTime().longValue(), 100L);
		Assert.assertEquals(parse.getPayload(), "C");

		parse = MessageParser.parse("|200|");

		Assert.assertFalse(parse.hasId());
		Assert.assertEquals(parse.getProcessTime().longValue(), 200L);
		Assert.assertEquals(parse.getPayload(), "");
	}
}
