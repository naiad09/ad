package ad.domain.entities.lastpost;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "forum_last_posts")
public class ForumLastPost implements Serializable {

	@EmbeddedId
	private ForumLastPostPK id;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "last_date", nullable = false)
	private LocalDateTime date;

	public Integer getForum() {
		return getId().getForum();
	}

	public Integer getClient() {
		return getId().getClient();
	}

	public ForumLastPostPK getId() {
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setId(ForumLastPostPK id) {
		this.id = id;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public ForumLastPost() {
	}

	@ConstructorProperties({ "id", "date" })
	public ForumLastPost(ForumLastPostPK id, LocalDateTime date) {
		this.id = id;
		this.date = date;
	}
}