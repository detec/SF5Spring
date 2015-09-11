package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openbox.sf5.db.DVBStandards;

public class VersionOfTheDVBEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		DVBStandards array[] = org.openbox.sf5.db.DVBStandards.values();
		List<DVBStandards> FECList = Arrays.asList(array);
		HashMap<String, DVBStandards> hm = new HashMap<>();
		FECList.stream().forEach(t -> hm.putIfAbsent(t.toString(), t));

		setValue(hm.get(text));

	}
}
