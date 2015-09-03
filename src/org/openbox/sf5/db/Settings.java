package org.openbox.sf5.db;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Settings")
@Component
public class Settings implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 7055744176770843683L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name = "my_entity_seq_gen", sequenceName =
	// "catalog_seq")
	private long id;

	@Column(name = "Name", unique = false, nullable = false, length = 50)
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
		return Name;
	}

	@Column(name = "PropsFile", unique = false, nullable = true, length = 300)
	private String PropsFile;

	public String getPropsFile() {
		return PropsFile;
	}

	public void setPropsFile(String PropsFile) {
		this.PropsFile = PropsFile;
	}

	@Column(name = "TheLastEntry", unique = false, nullable = true)
	private Timestamp TheLastEntry;

	public Timestamp getTheLastEntry() {
		return TheLastEntry;
	}

	public void setTheLastEntry(Timestamp TheLastEntry) {
		this.TheLastEntry = TheLastEntry;
	}

	@ManyToOne
	@JoinColumn(name = "User", unique = false, nullable = false)
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
	private List<SettingsConversion> Conversion;

	public List<SettingsConversion> getConversion() {
		return Conversion;
	}

	public void setConversion(List<SettingsConversion> Conversion) {
		this.Conversion = Conversion;
	}

	@OneToMany(mappedBy = "parent_id", fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@OrderColumn(name = "LineNumber")
	private List<SettingsSatellites> Satellites;

	public List<SettingsSatellites> getSatellites() {
		return Satellites;
	}

	public void setSatellites(List<SettingsSatellites> Satellites) {
		this.Satellites = Satellites;
	}

	public Settings(String Name, String PropsFile, Timestamp lastEntry,
			Users User, List<SettingsConversion> Conversion,
			List<SettingsSatellites> Satellites) {

		this.Name = Name;
		this.PropsFile = PropsFile;
		TheLastEntry = lastEntry;
		this.User = User;
		this.Conversion = Conversion;
		this.Satellites = Satellites;

	}

	public Settings() {
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}

		if (!(other instanceof Settings)) {
			return false;
		}
		Settings otherSettings = (Settings) other;
		if (otherSettings.Name.equals(Name)
				&& otherSettings.PropsFile.equals(PropsFile)
				&& otherSettings.TheLastEntry == TheLastEntry
				&& otherSettings.User.equals(User)
				&& otherSettings.Conversion.equals(Conversion)
				&& otherSettings.Satellites.equals(Satellites)) {
			return true;
		} else {
			return false;
		}

	}

}