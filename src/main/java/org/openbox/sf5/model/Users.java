package org.openbox.sf5.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "Users")
public class Users extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = -6789497093756301793L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "t_gen")
	@SequenceGenerator(name = "t_gen", sequenceName = "T_SEQ")
	private long id;

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "username", unique = false, nullable = false, length = 50)
	private String username;

	public void setusername(String username) {
		this.username = username;
	}

	public String getusername() {
		return username;
	}

	@Override
	public String toString() {
		return username;
	}

	@Column(name = "Password", unique = false, nullable = false, length = 15)
	private String Password;

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	@Column(name = "enabled", unique = false, nullable = false)
	private boolean enabled;

	public boolean getenabled() {
		return enabled;
	}

	public void setenabled(boolean enabled) {
		this.enabled = enabled;
	}

	@OneToMany(mappedBy = "parent_id", fetch = FetchType.EAGER, orphanRemoval = true)
	// @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@Cascade({ CascadeType.ALL })
	@OrderColumn(name = "LineNumber")
	public List<Usersauthorities> authorities;

	public List<Usersauthorities> getauthorities() {
		return authorities;
	}

	public void setauthorities(List<Usersauthorities> authorities) {
		this.authorities = authorities;
	}

	public Users(String username, String Password, boolean enabled, List<Usersauthorities> authorities) {

		this.username = username;
		this.Password = Password;
		this.enabled = enabled;
		this.authorities = authorities;

	}

	public Users() {
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}

		if (!(other instanceof Users)) {
			return false;
		}
		Users otherUsers = (Users) other;
		if (otherUsers.username.equals(username) && otherUsers.Password.equals(Password)
				&& otherUsers.enabled == enabled && otherUsers.authorities.equals(authorities)) {
			return true;
		} else {
			return false;
		}

	}

}