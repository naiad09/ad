package ad.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account implements Serializable {
	@Id
	private int id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "forum", nullable = false, updatable = false)
	private Forum forum;

	@Column(name = "login", nullable = false, length = 32)
	private String login;

	@Column(name = "password", nullable = false, length = 32)
	private String password;

	public Account(String login, String password) {
		this.login = login;
		this.password = password;
	}

	@Override
	public String toString() {
		return "Account [login=" + login + ", password=" + password + "]";
	}

	public int getId() {
		return id;
	}

	public Forum getForum() {
		return forum;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account() {
	}
}