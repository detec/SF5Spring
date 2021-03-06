package org.openbox.sf5.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SettingsSatellites")
@XmlRootElement(name = "SatellitesTable")
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingsSatellites extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = -2945693668519991789L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_SettingSatellites"))
	@JsonBackReference
	private Settings parent_id;

	@JsonProperty("LineNumber")
	private long LineNumber;

	public SettingsSatellites(Settings parent_id, long LineNumber) {

		this.parent_id = parent_id;
		this.LineNumber = LineNumber;

	}

	@ManyToOne
	@JoinColumn(name = "Satellite", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_Satellite"))
	@JsonProperty("Satellite")
	private Satellites Satellite;

	public Satellites getSatellite() {
		return Satellite;
	}

	public void setSatellite(Satellites Satellite) {
		this.Satellite = Satellite;
	}

	public Settings getparent_id() {
		return parent_id;
	}

	public void setparent_id(Settings parent_id) {
		this.parent_id = parent_id;
	}

	public long getLineNumber() {
		return LineNumber;
	}

	public void setLineNumber(long LineNumber) {
		this.LineNumber = LineNumber;
	}

	public SettingsSatellites(Settings parent_id, long LineNumber, Satellites Satellite) {

		this.parent_id = parent_id;
		this.LineNumber = LineNumber;
		this.Satellite = Satellite;

	}

	public SettingsSatellites() {
	}

}