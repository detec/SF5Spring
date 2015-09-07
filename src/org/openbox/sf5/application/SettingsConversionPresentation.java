package org.openbox.sf5.application;

import org.openbox.sf5.db.CarrierFrequency;
import org.openbox.sf5.db.Polarization;
import org.openbox.sf5.db.Satellites;
import org.openbox.sf5.db.Settings;
import org.openbox.sf5.db.SettingsConversion;
import org.openbox.sf5.db.Transponders;
import org.openbox.sf5.db.TypesOfFEC;

// http://stackoverflow.com/questions/21988598/how-to-get-selected-tablecell-in-javafx-tableview
// If you need data from multiple sources in single table, it is better to
// make a new class that aggregates all the data and use that as a TableView
// source.
public class SettingsConversionPresentation extends SettingsConversion {

	/**
	 *
	 */
	private static final long serialVersionUID = 7708887469121053073L;

	private Polarization Polarization;

	private transient boolean editable;

	private transient boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public SettingsConversionPresentation(Settings object) {
		// TODO Auto-generated constructor stub
		super.setparent_id(object);

		// Note shouldn't be null
		super.setNote("");
	}

	// we have to manually construct presentation class from super
	public SettingsConversionPresentation(SettingsConversion superItem) {
		super(superItem);
		Carrier = superItem.getTransponder().getCarrier();
		FEC = superItem.getTransponder().getFEC();
		Polarization = superItem.getTransponder().getPolarization();
		Satellite = superItem.getTransponder().getSatellite();
		Speed = superItem.getTransponder().getSpeed();
	}

	public Polarization getPolarization() {
		Transponders currentTransponder = super.getTransponder();
		Polarization currentPolarization = null;

		if (currentTransponder != null) {
			currentPolarization = currentTransponder.getPolarization();
		}
		return currentPolarization;
	}

	public void setPolarization(Polarization Polarization) {
		this.Polarization = Polarization;
	}

	private CarrierFrequency Carrier;

	public CarrierFrequency getCarrier() {
		Transponders currentTransponder = super.getTransponder();
		CarrierFrequency currentCarrier = null;
		if (currentTransponder != null) {
			currentCarrier = currentTransponder.getCarrier();
		}
		return currentCarrier;
	}

	public void setCarrier(CarrierFrequency Carrier) {
		this.Carrier = Carrier;
	}

	private long Speed;

	public long getSpeed() {
		Transponders currentTransponder = super.getTransponder();
		long currentSpeed = 0;
		if (currentTransponder != null) {
			currentSpeed = currentTransponder.getSpeed();
		}
		return currentSpeed;
	}

	public void setSpeed(long Speed) {
		this.Speed = Speed;
	}

	private Satellites Satellite;

	public Satellites getSatellite() {
		Transponders currentTransponder = super.getTransponder();
		Satellites currentSatellite = null;
		if (currentTransponder != null) {
			currentSatellite = currentTransponder.getSatellite();
		}
		return currentSatellite;
	}

	public void setSatellite(Satellites Satellite) {
		this.Satellite = Satellite;
	}

	private TypesOfFEC FEC;

	public TypesOfFEC getFEC() {
		Transponders currentTransponder = super.getTransponder();
		TypesOfFEC currentFEC = null;
		if (currentTransponder != null) {
			currentFEC = currentTransponder.getFEC();
		}
		return currentFEC;
	}

	public void setFEC(TypesOfFEC FEC) {
		this.FEC = FEC;
	}

	public SettingsConversionPresentation(
			SettingsConversionPresentation original, Settings parent) {
		Carrier = original.Carrier;
		FEC = original.FEC;
		Polarization = original.Polarization;
		Satellite = original.Satellite;
		Speed = original.Speed;
		this.setTransponder(original.getTransponder());
		this.setparent_id(parent);
		this.setNote(original.getNote());

	}

	// Spring needs default constructor
	public SettingsConversionPresentation() {

	}

}
