package org.openbox.sf5.converters;

import java.util.ArrayList;
import java.util.List;

public class TransponderChoiceListWrapper {

	private List<TransponderChoice> tclist = new ArrayList<TransponderChoice>();

	public List<TransponderChoice> getTclist() {
		return tclist;
	}

	public void setTclist(List<TransponderChoice> tclist) {
		this.tclist = tclist;
	}

}
