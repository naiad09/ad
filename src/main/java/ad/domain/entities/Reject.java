package ad.domain.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "reject")
public class Reject implements Serializable {
	@Id
	@Column(name = "url", unique = true, nullable = false, length = 32, updatable = false)
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Reject [url=" + url + "]";
	}

}