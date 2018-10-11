package ad.domain.entities.lastpost;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ForumLastPostPK implements Serializable {

	@Column(name = "client")
	private Integer client;

	@Column(name = "forum")
	private Integer forum;

	public ForumLastPostPK() {
	}

	public ForumLastPostPK(Integer client, Integer forum) {
		this.client = client;
		this.forum = forum;
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public Integer getForum() {
		return forum;
	}

	public void setForum(Integer forum) {
		this.forum = forum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((forum == null) ? 0 : forum.hashCode());
		return result;
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
		ForumLastPostPK other = (ForumLastPostPK) obj;
		if (client == null) {
			if (other.client != null) {
				return false;
			}
		} else if (!client.equals(other.client)) {
			return false;
		}
		if (forum == null) {
			if (other.forum != null) {
				return false;
			}
		} else if (!forum.equals(other.forum)) {
			return false;
		}
		return true;
	}

}