package ad.domain.entities.partner;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import ad.domain.entities.Forum;

@Entity
@Table(name = "partner")
public class Partner implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@OrderBy
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private int id;

	@Column(name = "topic", nullable = false)
	private int topic;
	private boolean enabled;

	@ManyToOne
	@JoinColumn(name = "client", nullable = false, updatable = false)
	private Forum client;

	@ManyToOne
	@JoinColumn(name = "forum", nullable = false, updatable = false)
	private Forum forum;

	@ManyToMany
	@JoinTable(
	        name = "partner_post",
	        joinColumns = { @JoinColumn(name = "partner") },
	        inverseJoinColumns = {
	                @JoinColumn(name = "raid") })
	private List<PartnerRaid> partnerRaids;
	@OneToMany(mappedBy = "partnerBean")
	private List<PartnerPost> partnerPosts;

	@Transient
	public String getPrTopicUrlString() {
		return "http://" + forum.getUrl() + "/viewtopic.php?id=" + getTopic();
	}

	@Transient
	public String getPrTopicPostUrlString() {
		return "http://" + forum.getUrl() + "/post.php?tid=" + getTopic();
	}

	@Override
	public String toString() {
		return getPrTopicUrlString();
	}

	public int getId() {
		return id;
	}

	public int getTopic() {
		return topic;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Forum getClient() {
		return client;
	}

	public Forum getForum() {
		return forum;
	}

	public List<PartnerRaid> getPartnerRaids() {
		return partnerRaids;
	}

	public List<PartnerPost> getPartnerPosts() {
		return partnerPosts;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTopic(int topic) {
		this.topic = topic;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setClient(Forum client) {
		this.client = client;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setPartnerRaids(List<PartnerRaid> partnerRaids) {
		this.partnerRaids = partnerRaids;
	}

	public void setPartnerPosts(List<PartnerPost> partnerPosts) {
		this.partnerPosts = partnerPosts;
	}
}