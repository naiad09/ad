package ad.domain.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Table(name = "pr_post")
public class PrPost extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private int id;

	@ManyToOne
	@JoinColumn(name = "forum", nullable = true, updatable = false)
	private Forum forum;

	@Column(name = "url", nullable = false, updatable = false)
	private String url;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "date", nullable = false, insertable = false, updatable = false)
	private LocalDateTime date;

	public PrPost() {
	}

	public PrPost(Forum forum, String url) {
		this.forum = forum;
		this.url = url;
		date = LocalDateTime.now();
		forum.getPosts().add(this);
	}

	@Override
	public String toString() {
		return "PrPost#" + id + " [forum=" + forum + ", url=" + url + ", date=" + date + "]";
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

}