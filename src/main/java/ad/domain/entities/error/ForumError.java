package ad.domain.entities.error;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import ad.domain.entities.Forum;

@Entity
@Table(name = "`error`")
public class ForumError implements Serializable {
	@Id
	@Column(name = "forum")
	@PrimaryKeyJoinColumn(name = "forum")
	private int id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "forum", nullable = false, updatable = false)
	private Forum forum;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "date", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;

	@Column(name = "message", length = 128, updatable = false)
	private String message;

	@Enumerated(EnumType.STRING)
	@Column(name = "type", updatable = false)
	private ForumErrorType type;

	public ForumError(Forum forum, ForumErrorType type) {
		this.forum = forum;
		this.type = type;
	}

	public ForumError(Forum forum, String message, ForumErrorType type) {
		this.forum = forum;
		this.message = message;
		this.type = type;
	}

	@Override
	public String toString() {
		return "ForumError [forum=" + forum + ", type=" + type + "]";
	}

	public int getId() {
		return id;
	}

	public Forum getForum() {
		return forum;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public ForumErrorType getType() {
		return type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(ForumErrorType type) {
		this.type = type;
	}

	public ForumError() {
	}
}