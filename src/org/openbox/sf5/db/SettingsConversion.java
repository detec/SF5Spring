package org.openbox.sf5.db;

import java.io.Serializable;
import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SettingsConversion")
public class SettingsConversion implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -399944579251735871L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name = "my_entity_seq_gen", sequenceName =
	// "catalog_seq")
	private long id;

	public long getId() {

		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id", unique = false, nullable = false)
	private Settings parent_id;

	private long LineNumber;

	public SettingsConversion(Settings parent_id) {

		this.parent_id = parent_id;
		// this.LineNumber = LineNumber;

	}

	@ManyToOne
	@JoinColumn(name = "Transponder", unique = false, nullable = false)
	private Transponders Transponder;

	public Transponders getTransponder() {
		return this.Transponder;
	}

	public void setTransponder(Transponders Transponder) {
		this.Transponder = Transponder;
	}

	@Column(name = "Satindex", unique = false, nullable = true, precision = 1)
	private long Satindex;

	public long getSatindex() {
		return this.Satindex;
	}

	public void setSatindex(long Satindex) {
		this.Satindex = Satindex;
	}

	@Column(name = "Tpindex", unique = false, nullable = true, precision = 1)
	private long Tpindex;

	public long getTpindex() {
		return this.Tpindex;
	}

	public void setTpindex(long Tpindex) {
		this.Tpindex = Tpindex;
	}

	@Column(name = "Note", unique = false, nullable = true, length = 120)
	private String Note;

	public String getNote() {
		return this.Note;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	@Column(name = "TheLineOfIntersection", unique = false, nullable = true, precision = 2)
	private long TheLineOfIntersection;

	public long getTheLineOfIntersection() {
		return this.TheLineOfIntersection;
	}

	public void setTheLineOfIntersection(long TheLineOfIntersection) {
		this.TheLineOfIntersection = TheLineOfIntersection;
	}

	public Settings getparent_id() {
		return this.parent_id;
	}

	public void setparent_id(Settings parent_id) {
		this.parent_id = parent_id;
	}

	public long getLineNumber() {
		return this.LineNumber;
	}

	public void setLineNumber(long LineNumber) {
		this.LineNumber = LineNumber;
	}

	public SettingsConversion(Settings parent_id, Transponders Transponder,
			long Satindex, long Tpindex, String Note, long TheLineOfIntersection) {

		this.parent_id = parent_id;
		// this.LineNumber = LineNumber;
		this.Transponder = Transponder;
		this.Satindex = Satindex;
		this.Tpindex = Tpindex;
		this.Note = Note;
		this.TheLineOfIntersection = TheLineOfIntersection;

	}

	public SettingsConversion() {
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}

		if (!(other instanceof SettingsConversion)) {
			return false;
		}
		SettingsConversion otherSettingsConversion = (SettingsConversion) other;
		if (otherSettingsConversion.parent_id.equals(this.parent_id)
				&& otherSettingsConversion.Transponder.equals(this.Transponder)
				&& otherSettingsConversion.Satindex == this.Satindex
				&& otherSettingsConversion.Tpindex == this.Tpindex
				&& otherSettingsConversion.Note.equals(this.Note)
				&& otherSettingsConversion.TheLineOfIntersection == this.TheLineOfIntersection) {
			return true;
		} else {
			return false;
		}

	}

	protected void setObjectFieldsFrom(SettingsConversion origObj)
			throws IllegalAccessException {
		Field fields[];
		Class curClass = origObj.getClass();

		if (!curClass.isAssignableFrom(this.getClass())) {
			throw new IllegalArgumentException(
					"New object must be the same class or a subclass of original");
		}

		// Spin through all fields of the class & all its superclasses
		do {
			fields = curClass.getDeclaredFields();

			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getName().equals("serialVersionUID")) {
					continue;
				}
				fields[i].set(this, fields[i].get(origObj));
			}
			curClass = curClass.getSuperclass();
		} while (curClass != null);
	}

	public SettingsConversion(SettingsConversion origObj) {
		try {
			setObjectFieldsFrom(origObj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}