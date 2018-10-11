package ad.domain.entities.partner;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ad.domain.entities.PrPost;

@Entity
@Table(name = "partner_post")
public class PartnerPost implements Serializable {
	@Id
	@Column(name = "pr_post")
	private int prPost;

	@ManyToOne
	@JoinColumn(name = "partner")
	private Partner partnerBean;

	@ManyToOne
	@JoinColumn(name = "raid")
	private PartnerRaid partnerRaid;

	public void setPrPostBean(PrPost prPost) {
		setPrPost(prPost.getId());
	}

	public int getPrPost() {
		return prPost;
	}

	public Partner getPartnerBean() {
		return partnerBean;
	}

	public PartnerRaid getPartnerRaid() {
		return partnerRaid;
	}

	public void setPrPost(int prPost) {
		this.prPost = prPost;
	}

	public void setPartnerBean(Partner partnerBean) {
		this.partnerBean = partnerBean;
	}

	public void setPartnerRaid(PartnerRaid partnerRaid) {
		this.partnerRaid = partnerRaid;
	}
}