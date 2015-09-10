package org.openbox.sf5.converters;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.openbox.sf5.db.TypesOfFEC;

public class FECEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		TypesOfFEC array[] = org.openbox.sf5.db.TypesOfFEC.values();
		List<TypesOfFEC> FECList = Arrays.asList(array);
		HashMap<String, TypesOfFEC> hm = new HashMap<>();
		FECList.stream().forEach(t -> hm.putIfAbsent(t.toString(), t));

		setValue(hm.get(text));
		// ObjectsController contr = new ObjectsController();
		// Long Lid = Long.parseLong(text);
		// Settings trans = (Settings) contr.select(Settings.class,
		// Lid.longValue());
		//
		// setValue(trans);

	}
}
