package ad.domain.entities.partner;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ad.domain.entities.Forum;

@Entity
@Table(name = "partner_raid")
public class PartnerRaid implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private int id;

	@Column(name = "start_date", insertable = false, updatable = false)
	private LocalDateTime startDate;

	@ManyToMany(mappedBy = "partnerRaids")
	private List<Partner> partners;

	@OneToMany(mappedBy = "partnerRaid")
	private List<PartnerPost> partnerPosts;

	@ManyToOne
	@JoinColumn(name = "client")
	private Forum client;

	private String code;

	public int getId() {
		return id;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public List<Partner> getPartners() {
		return partners;
	}

	public List<PartnerPost> getPartnerPosts() {
		return partnerPosts;
	}

	public Forum getClient() {
		return client;
	}

	public String getCode() {
		return code;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public void setPartners(List<Partner> partners) {
		this.partners = partners;
	}

	public void setPartnerPosts(List<PartnerPost> partnerPosts) {
		this.partnerPosts = partnerPosts;
	}

	public void setClient(Forum client) {
		this.client = client;
	}

	public void setCode(String code) {
		this.code = code;
	}
}