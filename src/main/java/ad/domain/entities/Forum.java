package ad.domain.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import ad.domain.entities.task.Task;

@Entity
@Table(name = "forum", uniqueConstraints = { @UniqueConstraint(columnNames = { "url" }) })
public class Forum extends BaseEntity implements Serializable {
	public static final String HTTP_PREFIX = "http://";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Integer id;

	@OneToOne(mappedBy = "forum", cascade = CascadeType.ALL, orphanRemoval = true)
	private Account account;

	@Column(name = "url", unique = true, nullable = false, length = 64, updatable = false)
	private String url;

	@Column(name = "forumName")
	private String forumName;

	@Column(name = "topic", nullable = false)
	private int topic;

	@Column(name = "topicTitle", nullable = true)
	private String topicTitle;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "update_date")
	private LocalDateTime updateDate;

	@Column(name = "visitors", nullable = false)
	private int visitors;

	// @OneToOne(mappedBy = "forum", cascade = { CascadeType.ALL })
	// private ForumError error;
	//
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "forum", cascade = { CascadeType.ALL })
	private List<PrPost> posts = new ArrayList<>();

	@Column(name = "isClient")
	private boolean isClient;

	@Column(name = "code", length = 65535)
	private String code;

	@OneToMany(
	        fetch = FetchType.EAGER,
	        mappedBy = "client",
	        orphanRemoval = true,
	        cascade = {
	                CascadeType.ALL })
	private List<Task> tasks = new ArrayList<>();

	@OneToOne(mappedBy = "forum", cascade = CascadeType.ALL)
	private BannerTask banner;

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = {
	// CascadeType.ALL })
	// private List<Partner> partners = new ArrayList<>();

	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "client", cascade = {
	// CascadeType.ALL })
	// private List<Partner> partners = new ArrayList<>();

	public Forum() {
	}

	public Forum(String url) {
		this.url = url;
	}

	@Transient
	public String getPrTopicUrlString() {
		return HTTP_PREFIX + getUrl() + "/viewtopic.php?id=" + getTopic();
	}

	@Transient
	public String getPrTopicPostUrlString() {
		String path = "/post.php?tid=" + getTopic();
		return HTTP_PREFIX + getUrl() + path;
	}

	@Transient
	public String getIndexUrl() {
		return HTTP_PREFIX + getUrl();
	}

	@Transient
	public String getLoginUrl() {
		return HTTP_PREFIX + getUrl() + "/login.php";
	}

	@Transient
	public String getPostLink(int postNum) {
		return HTTP_PREFIX + getUrl() + "/viewtopic.php?pid=" + postNum + "#p" + postNum;
	}

	@Override
	public String toString() {
		return "Forum [id=" + id + ", url=" + url + ", topic=" + topic + "]";
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public int getTopic() {
		return topic;
	}

	public void setTopic(int topic) {
		this.topic = topic;
		if (topic == 0) {
			setCode(null);
		}
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(LocalDateTime updateDate) {
		this.updateDate = updateDate;
	}

	public int getVisitors() {
		return visitors;
	}

	public void setVisitors(int visitors) {
		this.visitors = visitors;
	}

	public List<PrPost> getPosts() {
		return posts;
	}

	public void setPosts(List<PrPost> posts) {
		this.posts = posts;
	}

	public boolean isClient() {
		return isClient;
	}

	public void setClient(boolean isClient) {
		this.isClient = isClient;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	// public List<Partner> getPartners() {
	// return partners;
	// }
	//
	// public void setPartners(List<Partner> partners) {
	// this.partners = partners;
	// }

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		if (topicTitle != null && !topicTitle.isEmpty()) {
			this.topicTitle = topicTitle;
		}
	}

	public String getLogin() {
		return account != null ? account.getLogin() : null;
	}

	public void setLogin(String login) {
		if (account == null) {
			account = new Account();
		}
		account.setLogin(login);
	}

	public String getPassword() {
		return account != null ? account.getPassword() : null;
	}

	public void setPassword(String password) {
		account.setPassword(password);
	}

	public BannerTask getBanner() {
		return banner;
	}

	public void setBanner(BannerTask banner) {
		this.banner = banner;
	}

	//
	// public ForumError getError() {
	// return error;
	// }

	// public void setError(ForumError error) {
	// this.error = error;
	// if (error != null) {
	// error.setForum(this);
	// }
	// }
	//
	// @Transient
	// public int getTodayPostsCount() {
	// List<PrPost> posts = getPosts().stream().filter((p) -> {
	// LocalDate now = LocalDate.now();
	// LocalDateTime date = p.getDate();
	// LocalDate localDate = date.toLocalDate();
	// boolean equals = now.equals(localDate);
	// return equals;
	// }).distinct().collect(Collectors.toList());
	// int size = posts.size();
	// return size;
	// }

	public boolean isReady() {
		return getUpdateDate().toLocalDate().plusDays(7).isAfter(LocalDate.now())
		        && isAllSet();
	}

	public boolean isAllSet() {
		return topic != 0
		        && code != null && !code.isEmpty()
		        && getLogin() != null
		        && getPassword() != null;
	}

	public String getSunteticName() {
		return forumName != null ? forumName : url;
	}

}