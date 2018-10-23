package org.openbox.sf5.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
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
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "SettingsConversion")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SettingsConversion extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = -399944579251735871L;

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
	@JoinColumn(name = "parent_id", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_SettingConversion"))
	@JsonBackReference
	@XmlIDREF
	private Settings parent_id;

	@JsonProperty("lineNumber")
	private long LineNumber;

	public SettingsConversion(Settings parent_id) {

		this.parent_id = parent_id;

	}

	@ManyToOne
	@JoinColumn(name = "Transponder", unique = false, nullable = false, foreignKey = @ForeignKey(name = "FK_Transponder"))
	@JsonProperty("transponder")
	private Transponders Transponder;

	public Transponders getTransponder() {
		return Transponder;
	}

	public void setTransponder(Transponders Transponder) {
		this.Transponder = Transponder;
	}

	@Column(name = "Satindex", unique = false, nullable = true, precision = 1)
	@JsonProperty("satindex")
	private long Satindex;

	public long getSatindex() {
		return Satindex;
	}

	public void setSatindex(long Satindex) {
		this.Satindex = Satindex;
	}

	@Column(name = "Tpindex", unique = false, nullable = true, precision = 1)
	@JsonProperty("tpindex")
	private long Tpindex;

	public long getTpindex() {
		return Tpindex;
	}

	public void setTpindex(long Tpindex) {
		this.Tpindex = Tpindex;
	}

	@Column(name = "Note", unique = false, nullable = true, length = 120)
	@JsonProperty("note")
	private String Note;

	public String getNote() {
		return Note;
	}

	public void setNote(String Note) {
		this.Note = Note;
	}

	@Column(name = "TheLineOfIntersection", unique = false, nullable = true, precision = 2)
	@JsonProperty("lineOfIntersection")
	private long TheLineOfIntersection;

	public long getLineOfIntersection() {
		return TheLineOfIntersection;
	}

	public void setLineOfIntersection(long TheLineOfIntersection) {
		this.TheLineOfIntersection = TheLineOfIntersection;
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

	public SettingsConversion(Settings parent_id, Transponders Transponder, long Satindex, long Tpindex, String Note,
			long TheLineOfIntersection) {

		this.parent_id = parent_id;
		this.Transponder = Transponder;
		this.Satindex = Satindex;
		this.Tpindex = Tpindex;
		this.Note = Note;
		this.TheLineOfIntersection = TheLineOfIntersection;

	}

	public SettingsConversion() {
	}

	@SuppressWarnings("unchecked")
    protected void setObjectFieldsFrom(SettingsConversion origObj)
            throws IllegalArgumentException, IllegalAccessException {
		Field fields[];
		Class curClass = origObj.getClass();

		if (!curClass.isAssignableFrom(this.getClass())) {
			throw new IllegalArgumentException("New object must be the same class or a subclass of original");
		}

		// filter only SettingsConversion fields
		List<String> SCClassList = new ArrayList<>();

		Field[] thisClassFieldsArray = SettingsConversion.class.getDeclaredFields();

		List<Field> thisClassFiledList = Arrays.asList(thisClassFieldsArray);
		thisClassFiledList.stream().forEach(t -> {
			String fieldname = t.getName();
            if (!"serialVersionUID".equals(fieldname)) {
				SCClassList.add(fieldname);
			}
		});

		// Spin through all fields of the class & all its superclasses
		do {
			fields = thisClassFieldsArray;

			for (int i = 0; i < fields.length; i++) {

				if (SCClassList.contains(fields[i].getName())) {
					// add only checked classes
					// continue;
					fields[i].set(this, fields[i].get(origObj));
				}
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