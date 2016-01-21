package org.openbox.sf5.converters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.openbox.sf5.common.JsonObjectFiller;

// http://stackoverflow.com/questions/2519432/jaxb-unmarshal-timestamp

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {

	private SimpleDateFormat dateFormat = JsonObjectFiller.getJsonDateFormatter();

	@Override
	public String marshal(Timestamp v) throws Exception {
		return dateFormat.format(v);
	}

	@Override
	public Timestamp unmarshal(String v) throws Exception {
		// String might be as nanoseconds
		// Timestamp value = new Timestamp(Long.parseLong(v));
		// value = (value == null) ? (Timestamp) dateFormat.parse(v) :
		// (Timestamp) dateFormat.parse(v);

		Date date = dateFormat.parse(v);
		Timestamp value = new Timestamp(date.getTime());
		return value;
	}

}
