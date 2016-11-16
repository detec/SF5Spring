package org.openbox.sf5.model;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Transponders")
@XmlRootElement
public class Transponders extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = -3945460836260580586L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Frequency", unique = false, nullable = false, precision = 5)
	@Min(value = 2000)
	@JsonProperty("frequency")
	private long Frequency;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@NotNull
	@JsonProperty("polarization")
	private Polarization Polarization;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = true)
	@JsonProperty("fec")
	private TypesOfFEC FEC;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@NotNull
	@JsonProperty("carrier")
	private CarrierFrequency Carrier;

	@Column(name = "speed", unique = false, nullable = false, precision = 5)
	@Min(value = 1000)
	@JsonProperty("speed")
	private long Speed;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@NotNull
	@JsonProperty("versionOfTheDVB")
	private DVBStandards VersionOfTheDVB;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@NotNull
	@JsonProperty("rangeOfDVB")
	private RangesOfDVB RangeOfDVB;

	@ManyToOne
	@JoinColumn(name = "Satellite", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_SatelliteTransponders"))
	@NotNull
	@JsonProperty("satellite")
	private Satellites Satellite;

	public Transponders(long Frequency, Polarization Polarization, TypesOfFEC FEC, CarrierFrequency Carrier, long Speed,
			DVBStandards VersionOfTheDVB, RangesOfDVB RangeOfDVB, Satellites Satellite) {

		this.Frequency = Frequency;
		this.Polarization = Polarization;
		this.FEC = FEC;
		this.Carrier = Carrier;
		this.Speed = Speed;
		this.VersionOfTheDVB = VersionOfTheDVB;
		this.RangeOfDVB = RangeOfDVB;
		this.Satellite = Satellite;

	}

	public Transponders() {
	}

	public Transponders(Transponders origObj) {
		try {
			setObjectFieldsFrom(origObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getFrequency() {
		return Frequency;
	}

	public void setFrequency(long Frequency) {
		this.Frequency = Frequency;
	}

	@Override
	public String toString() {
		return String.valueOf(Frequency);
	}

	public Polarization getPolarization() {
		return Polarization;
	}

	public void setPolarization(Polarization Polarization) {
		this.Polarization = Polarization;
	}

	public TypesOfFEC getFEC() {
		return FEC;
	}

	public void setFEC(TypesOfFEC FEC) {
		this.FEC = FEC;
	}

	public CarrierFrequency getCarrier() {
		return Carrier;
	}

	public void setCarrier(CarrierFrequency Carrier) {
		this.Carrier = Carrier;
	}

	public long getSpeed() {
		return Speed;
	}

	public void setSpeed(long Speed) {
		this.Speed = Speed;
	}

	public DVBStandards getVersionOfTheDVB() {
		return VersionOfTheDVB;
	}

	public void setVersionOfTheDVB(DVBStandards VersionOfTheDVB) {
		this.VersionOfTheDVB = VersionOfTheDVB;
	}

	public RangesOfDVB getRangeOfDVB() {
		return RangeOfDVB;
	}

	public void setRangeOfDVB(RangesOfDVB RangeOfDVB) {
		this.RangeOfDVB = RangeOfDVB;
	}

	public Satellites getSatellite() {
		return Satellite;
	}

	public void setSatellite(Satellites Satellite) {
		this.Satellite = Satellite;
	}

	private void setObjectFieldsFrom(Transponders origObj) throws IllegalAccessException {
		Field[] fields;
		Class<?> curClass = origObj.getClass();

		if (!curClass.isAssignableFrom(getClass())) {
			throw new IllegalArgumentException("New object must be the same class or a subclass of original");
		}

		// Spin through all fields of the class & all its superclasses
		do {
			fields = curClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				if ("serialVersionUID".equals(fields[i].getName())) {
					continue;
				}
				fields[i].set(this, fields[i].get(origObj));
			}
			curClass = curClass.getSuperclass();
		} while (curClass != null);
	}

}