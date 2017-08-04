package org.openbox.sf5.converters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * TimestampAdapter
 *
 * @author Andrii Duplyk
 *
 */
public class TimestampAdapter extends XmlAdapter<String, Timestamp> {

    public static final String ISO_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

	private SimpleDateFormat dateFormat = getJsonDateFormatter();

	/**
	 * Marshalling
	 */
	@Override
	public String marshal(Timestamp v) throws Exception {
		return dateFormat.format(v);
	}

	/**
	 * Unmarshalling
	 */
	@Override
	public Timestamp unmarshal(String v) throws Exception {
		// String might be as nanoseconds
		Date date = dateFormat.parse(v);
		return new Timestamp(date.getTime());

	}

	private static SimpleDateFormat getJsonDateFormatter() {
        SimpleDateFormat formatter = new SimpleDateFormat(ISO_DATE_PATTERN);
		formatter.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
		return formatter;
	}

}
