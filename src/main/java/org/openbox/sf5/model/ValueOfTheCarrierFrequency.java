package org.openbox.sf5.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ValueOfTheCarrierFrequency")
public class ValueOfTheCarrierFrequency extends AbstractDbEntity implements Serializable {

	@Override
	public int hashCode() {
		return Objects.hash(this.Polarization, this.TypeOfCarrierFrequency);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ValueOfTheCarrierFrequency other = (ValueOfTheCarrierFrequency) obj;

		return Objects.equals(TypeOfCarrierFrequency, other.TypeOfCarrierFrequency)
				&& Objects.equals(Polarization, other.Polarization);

	}

	private static final long serialVersionUID = -6095308495476745108L;

	@Id
	private CarrierFrequency TypeOfCarrierFrequency;

	public CarrierFrequency getTypeOfCarrierFrequency() {
		return TypeOfCarrierFrequency;
	}

	public void setTypeOfCarrierFrequency(CarrierFrequency TypeOfCarrierFrequency) {
		this.TypeOfCarrierFrequency = TypeOfCarrierFrequency;
	}

	@Id
	private KindsOfPolarization Polarization;

	@Column(name = "LowerThreshold", unique = false, nullable = false, precision = 5)
	private long LowerThreshold;

	public long getLowerThreshold() {
		return LowerThreshold;
	}

	public void setLowerThreshold(long LowerThreshold) {
		this.LowerThreshold = LowerThreshold;
	}

	@Column(name = "UpperThreshold", unique = false, nullable = false, precision = 5)
	private long UpperThreshold;

	public long getUpperThreshold() {
		return UpperThreshold;
	}

	public void setUpperThreshold(long UpperThreshold) {
		this.UpperThreshold = UpperThreshold;
	}

	public ValueOfTheCarrierFrequency(CarrierFrequency TypeOfCarrierFrequency, KindsOfPolarization Polarization,
			long LowerThreshold, long UpperThreshold) {

		this.TypeOfCarrierFrequency = TypeOfCarrierFrequency;
		this.Polarization = Polarization;
		this.LowerThreshold = LowerThreshold;
		this.UpperThreshold = UpperThreshold;

	}

	public ValueOfTheCarrierFrequency() {
	}
}