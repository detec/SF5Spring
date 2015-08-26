package org.openbox.sf5.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.common.TableFiller;
import org.openbox.sf5.service.ObjectsController;
import org.openbox.sf5.service.ObjectsListService;

@Entity
@Table(name = "Users")
public class Users implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private long id;

	public long getId() {

		return this.id;
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
	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.DELETE })
	@OrderColumn(name = "LineNumber")
	public List<Usersauthorities> authorities;

	public List<Usersauthorities> getauthorities() {
		return authorities;
	}

	public void setauthorities(List<Usersauthorities> authorities) {
		this.authorities = authorities;
	}

	public Users(String username, String Password, boolean enabled,
			List<Usersauthorities> authorities) {

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
		if (otherUsers.username.equals(username)
				&& otherUsers.Password.equals(Password)
				&& otherUsers.enabled == enabled
				&& otherUsers.authorities.equals(authorities)) {
			return true;
		} else {
			return false;
		}

	}

	public void initialize() {

		Criterion criterea = Restrictions.eq("username", "admin");
		List<Users> adminsList = (List<Users>) ObjectsListService
				.ObjectsCriterionList(Users.class, criterea);
		if (adminsList.isEmpty()) {
			List<Usersauthorities> list = new ArrayList<>();

			Users admin = new Users("admin", "1", true, list);
			list.add(new Usersauthorities("admin", "ROLE_ADMIN", admin));
			admin.setauthorities(list);

			ObjectsController contr = new ObjectsController();
			contr.saveOrUpdate(admin);

			// insert default values into database.
			new TableFiller();
		}

	}
}