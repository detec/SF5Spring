package org.openbox.sf5.json.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CustomObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = -3672344428949816140L;

	public CustomObjectMapper(){
		// SerializationFeature for changing how JSON is written

		// to enable standard indentation ("pretty-printing"):
		this.enable(SerializationFeature.INDENT_OUTPUT);
		// to allow serialization of "empty" POJOs (no properties to serialize)
		// (without this setting, an exception is thrown in those cases)
		this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		// to write java.util.Date, Calendar as number (timestamp):
		this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// DeserializationFeature for changing how JSON is read as POJOs:

		// to prevent exception when encountering unknown property:
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// to allow coercion of JSON empty String ("") to null Object value:
		this.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
	}


}
