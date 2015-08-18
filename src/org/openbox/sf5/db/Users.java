package org.openbox.sf5.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openbox.sf5.service.ObjectsController;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	// @SequenceGenerator(name = "my_entity_seq_gen", sequenceName =
	// "catalog_seq")
	private long id;

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "Name", unique = false, nullable = false, length = 30)
	private String Name;

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getName() {
		return Name;
	}

	@Column(name = "Role", unique = false, nullable = false, length = 20)
	private String Role;

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	@Override
	public String toString() {
		return Name;
	}

	@Column(name = "Login", unique = false, nullable = false, length = 12)
	private String Login;

	public String getLogin() {
		return Login;
	}

	public void setLogin(String Login) {
		this.Login = Login;
	}

	@Column(name = "Password", unique = false, nullable = false, length = 15)
	private String Password;

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	@Column(name = "Active", unique = false, nullable = false)
	private boolean Active;

	public boolean getActive() {
		return Active;
	}

	public void setActive(boolean Active) {
		this.Active = Active;
	}

	public Users(String Name, String Login, String Password, boolean Active,
			String Role) {

		this.Name = Name;
		this.Login = Login;
		this.Password = Password;
		this.Active = Active;
		this.Role = Role;

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
		if (otherUsers.Name.equals(Name) && otherUsers.Login.equals(Login)
				&& otherUsers.Password.equals(Password)
				&& otherUsers.Active == Active && otherUsers.Role.equals(Role)) {
			return true;
		} else {
			return false;
		}

	}

	public void initialize() {
		System.out
				.println("DbUtil.initialize() *************************************** ");

		Users admin = new Users("Andrew Frolov", "admin", "1", true,
				"ROLE_ADMIN");
		ObjectsController contr = new ObjectsController();
		contr.saveOrUpdate(admin);

	}
}