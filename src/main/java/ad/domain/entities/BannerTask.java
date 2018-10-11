package ad.domain.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ad.domain.entities.task.PaidStatus;
import ad.domain.entities.task.RunStatus;

@Entity
@Table(name = "banner")
public class BannerTask extends BaseEntity {

	@Id
	private int id;

	@MapsId
	@OneToOne
	@JoinColumn(name = "forum", nullable = false, updatable = false)
	private Forum forum;

	@Column(name = "image", nullable = false, length = 150)
	private String image;

	@Column(name = "isBig")
	private boolean isBig;

	@Column(name = "till_date")
	private LocalDate tillDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "run_status", nullable = false, length = 4)
	private RunStatus runStatus;

	@Enumerated(EnumType.STRING)
	@Column(name = "paid_status", nullable = false, length = 8)
	private PaidStatus paidStatus;

	@Override
	public Integer getId() {
		return id;
	}

	public Forum getForum() {
		return forum;
	}

	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isBig() {
		return isBig;
	}

	public void setBig(boolean isBig) {
		this.isBig = isBig;
	}

	public LocalDate getTillDate() {
		return tillDate;
	}

	public void setTillDate(LocalDate tillDate) {
		this.tillDate = tillDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RunStatus getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(RunStatus runStatus) {
		this.runStatus = runStatus;
	}

	public PaidStatus getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(PaidStatus paidStatus) {
		this.paidStatus = paidStatus;
	}

}
