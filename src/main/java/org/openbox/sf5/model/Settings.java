package org.openbox.sf5.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;
import org.openbox.sf5.converters.TimestampAdapter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Settings", indexes = { @Index(columnList = "\"user\"", name = "\"user\"") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = 7055744176770843683L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "Name", unique = false, nullable = false, length = 50)
	@NotEmpty
	@JsonProperty("name")
	@XmlID
	private String Name;

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		// return Name;
		// we must convert object to id
		return String.valueOf(id);
	}

	@Column(name = "TheLastEntry", unique = false, nullable = true)
	@JsonProperty("lastEntry")
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Kiev")
	@NotNull
	private Timestamp TheLastEntry;

	public Timestamp getTheLastEntry() {
		return TheLastEntry;
	}

	public void setTheLastEntry(Timestamp TheLastEntry) {
		this.TheLastEntry = TheLastEntry;
	}

	@ManyToOne
	@JoinColumn(name = "\"user\"", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_User"))
	@NotNull
	@JsonProperty("user")
	@Valid
	private Users User;

	public Users getUser() {
		return User;
	}

	public void setUser(Users User) {
		this.User = User;
	}

	@OneToMany(mappedBy = "parent_id", fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@OrderColumn(name = "LineNumber")
	@JsonProperty("conversion")
	@JsonManagedReference
	private List<SettingsConversion> Conversion = new ArrayList<>();

	public List<SettingsConversion> getConversion() {
		return Conversion;
	}

	public void setConversion(List<SettingsConversion> Conversion) {
		this.Conversion = Conversion;
	}

	@OneToMany(mappedBy = "parent_id", fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@OrderColumn(name = "LineNumber")
	@JsonProperty("satellites")
	private List<SettingsSatellites> Satellites;

	public List<SettingsSatellites> getSatellites() {
		return Satellites;
	}

	public void setSatellites(List<SettingsSatellites> Satellites) {
		this.Satellites = Satellites;
	}

	public Settings(String Name, Timestamp lastEntry, Users User, List<SettingsConversion> Conversion,
			List<SettingsSatellites> Satellites) {

		this.Name = Name;
		TheLastEntry = lastEntry;
		this.User = User;
		this.Conversion = Conversion;
		this.Satellites = Satellites;

	}

	public Settings() {
	}

}