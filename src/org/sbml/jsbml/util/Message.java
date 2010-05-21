package org.sbml.jsbml.util;

public class Message {

	private String lang;
	private String message;
	
	
	public Message() {
	}


	@Override
	public String toString() {
		return "Message [lang=" + lang + ", messageContent=" + message
				+ "]";
	}


	public String getLang() {
		return lang;
	}


	public void setLang(String lang) {
		this.lang = lang;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String messageContent) {
		this.message = messageContent;
	}
	
}
