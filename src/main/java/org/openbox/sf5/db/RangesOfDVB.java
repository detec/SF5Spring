package org.openbox.sf5.db;

public enum RangesOfDVB {

	Ku("Ku"), C("C");

	private RangesOfDVB(String s) {
		value = s;
	}

	private final String value;

	@Override
	public String toString() {
		return value;
	}

}