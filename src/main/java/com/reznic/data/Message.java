package com.reznic.data;

/**
 * Created on 19.06.2020.
 */
public class Message {

	private String id;
	private Long   processTime;
	private Object payload;
	private String originalMessage;

	public Message(String originalMessage) {
		this.originalMessage = originalMessage;
	}

	public Message(String id, Long processTime, Object payload, String originalMessage) {
		this.id = id;
		this.processTime = processTime;
		this.payload = payload;
		this.originalMessage = originalMessage;
	}

	public boolean hasId() {
		return id != null && !id.isEmpty();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Long processTime) {
		this.processTime = processTime;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getOriginalMessage() {
		return originalMessage;
	}

	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Message message = (Message) o;

		return originalMessage.equals(message.originalMessage);
	}

	@Override
	public int hashCode() {
		return originalMessage.hashCode();
	}

	@Override
	public String toString() {
		return "Message{" +
		  "id='" + id + '\'' +
		  ", processTime=" + processTime +
		  ", payload=" + payload +
		  '}';
	}
}
