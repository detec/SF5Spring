package org.openbox.sf5.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Usersauthorities")
public class Usersauthorities implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 108703010218830663L;

	@Id
	private long id;

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id", unique = false, nullable = false)
	private Users parent_id;

	public Users getparent_id() {
		return parent_id;
	}

	public void setparent_id(Users parent_id) {
		this.parent_id = parent_id;
	}

	@Column(name = "username", unique = false, nullable = false)
	private String username;

	private long LineNumber;

	public Usersauthorities(String username) {

		this.username = username;

	}

	@Column(name = "authority", unique = false, nullable = true, length = 50)
	private String authority;

	public String getauthority() {
		return authority;
	}

	public void setauthority(String authority) {
		this.authority = authority;
	}

	public String getusername() {
		return username;
	}

	public void setparent_id(String username) {
		this.username = username;
	}

	public long getLineNumber() {
		return LineNumber;
	}

	public void setLineNumber(long LineNumber) {
		this.LineNumber = LineNumber;
	}

	public Usersauthorities(String username, String authority, Users parent_id,
			long pLine) {

		this.username = username;
		this.authority = authority;
		this.parent_id = parent_id;
		this.LineNumber = pLine;

	}

	public Usersauthorities(Users parent_id) {

		this.parent_id = parent_id;

	}

	public Usersauthorities() {
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}

		if (!(other instanceof Usersauthorities)) {
			return false;
		}
		Usersauthorities otherUsersauthorities = (Usersauthorities) other;
		if (otherUsersauthorities.username.equals(username)
				&& otherUsersauthorities.authority.equals(authority)) {
			return true;
		} else {
			return false;
		}

	}

}