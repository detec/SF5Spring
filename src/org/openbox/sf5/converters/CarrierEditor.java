package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openbox.sf5.db.CarrierFrequency;

public class CarrierEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		CarrierFrequency array[] = org.openbox.sf5.db.CarrierFrequency.values();
		List<CarrierFrequency> FECList = Arrays.asList(array);
		HashMap<String, CarrierFrequency> hm = new HashMap<>();
		FECList.stream().forEach(t -> hm.putIfAbsent(t.toString(), t));

		setValue(hm.get(text));

	}
}
