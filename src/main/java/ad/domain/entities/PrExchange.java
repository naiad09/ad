package ad.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pr_exchange")
public class PrExchange implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_post", nullable = false, updatable = false)
	private PrPost prPostByClientPost;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forum_post", nullable = false, updatable = false)
	private PrPost prPostByForumPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "raid", nullable = false, updatable = false)
	private PrRaid prRaid;

	public PrExchange(PrPost prPostByClientPost, PrPost prPostByForumPost, PrRaid prRaid) {
		this.prPostByClientPost = prPostByClientPost;
		this.prPostByForumPost = prPostByForumPost;
		this.prRaid = prRaid;
	}

	public Integer getId() {
		return id;
	}

	public PrPost getPrPostByClientPost() {
		return prPostByClientPost;
	}

	public PrPost getPrPostByForumPost() {
		return prPostByForumPost;
	}

	public PrRaid getPrRaid() {
		return prRaid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPrPostByClientPost(PrPost prPostByClientPost) {
		this.prPostByClientPost = prPostByClientPost;
	}

	public void setPrPostByForumPost(PrPost prPostByForumPost) {
		this.prPostByForumPost = prPostByForumPost;
	}

	public void setPrRaid(PrRaid prRaid) {
		this.prRaid = prRaid;
	}

	public PrExchange() {
	}
}