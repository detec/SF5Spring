package org.openbox.sf5.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Users extends AbstractDbEntity implements Serializable {

	private static final long serialVersionUID = -6789497093756301793L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "username", unique = false, nullable = false, length = 50)
	@XmlID
	private String username;

	@Column(name = "Password", unique = false, nullable = false, length = 15)
	@JsonProperty("password")
	private String Password;

	@Column(name = "enabled", unique = false, nullable = false)
	private boolean enabled;

	@OneToMany(mappedBy = "parent_id", fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	@OrderColumn(name = "LineNumber")
	@JsonManagedReference
	@Valid
	@NotNull
	@Size(min = 1)
	private List<Usersauthorities> authorities;

	public Users(String username, String Password, boolean enabled, List<Usersauthorities> authorities) {

		this.username = username;
		this.Password = Password;
		this.enabled = enabled;
		this.authorities = authorities;

	}

	public Users() {
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		Users other = (Users) obj;
		return Objects.equals(id, other.id);
	}

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

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	public boolean getenabled() {
		return enabled;
	}

	public void setenabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<Usersauthorities> getauthorities() {
		return authorities;
	}

	public void setauthorities(List<Usersauthorities> authorities) {
		this.authorities = authorities;
	}

}