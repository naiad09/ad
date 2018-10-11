package ad.domain.entities.task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import ad.domain.entities.BaseEntity;
import ad.domain.entities.Forum;
import ad.domain.entities.PrPost;
import ad.domain.entities.PrRaid;

@Entity
@Table(name = "task")
public class Task extends BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "client", nullable = false, updatable = false)
	private Forum client;

	@Enumerated(EnumType.STRING)
	@Column(name = "paid_status", nullable = false, length = 8)
	private PaidStatus paidStatus;

	@Column(name = "paid", nullable = false)
	private int paid;

	@Column(name = "gift", nullable = false)
	private int gift;

	@Column(name = "per_day", nullable = false)
	private int perDay;

	@Generated(GenerationTime.ALWAYS)
	@Column(name = "create_date", nullable = false, insertable = false, updatable = false)
	private LocalDateTime createDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "run_status", nullable = false, length = 4)
	private RunStatus runStatus;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "tasks")
	private Set<PrRaid> raids = new HashSet<>();

	public Task() {
	}

	public Task(Forum client) {
		this.client = client;
		paid = 50;
		runStatus = RunStatus.ACT;
		paidStatus = PaidStatus.FREE;
		perDay = 50;
	}

	public long getDone() {
		List<PrPost> posts = getPosts();
		long count = posts.size();
		return count;
	}

	public long getToday() {
		List<PrPost> posts = getPosts();
		LocalDate today = LocalDate.now();
		return posts.stream().filter(p -> today.equals(p.getDate().toLocalDate())).count();
	}

	public long getPercentageDone() {
		long per = getDone() * 100 / getTotal();
		return per > 100 ? 100 : per;
	}

	public int getTotal() {
		return getPaid() + getGift();
	}

	public long getRest() {
		long rest = getTotal() - getDone();
		return rest < 0 ? 0 : rest;
	}

	public List<PrPost> getPosts() {
		Optional<Task> nextDate = client.getTasks().stream().filter((t) -> {
			return !Objects.equals(t.id, id);
		}).min((t1, t2) -> {
			return t1.createDate.compareTo(t2.createDate);
		});
		List<PrPost> posts = getClient().getPosts().stream().filter((p) -> {
			LocalDateTime date = p.getDate();
			return !date.toLocalDate().isBefore(createDate.toLocalDate());
		}).filter((p) -> {
			return !nextDate.isPresent() || p.getDate().isBefore(nextDate.get().getCreateDate());
		}).collect(Collectors.toList());
		return posts;
	}

	@Override
	public String toString() {
		return "Task [client=" + client + ", paidStatus=" + paidStatus + ", runStatus=" + runStatus + "]";
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Forum getClient() {
		return client;
	}

	public void setClient(Forum client) {
		this.client = client;
	}

	public PaidStatus getPaidStatus() {
		return paidStatus;
	}

	public void setPaidStatus(PaidStatus paidStatus) {
		this.paidStatus = paidStatus;
	}

	public int getPaid() {
		return paid;
	}

	public void setPaid(int paid) {
		this.paid = paid;
	}

	public int getGift() {
		return gift;
	}

	public void setGift(int gift) {
		this.gift = gift;
	}

	public int getPerDay() {
		return perDay;
	}

	public void setPerDay(int perDay) {
		this.perDay = perDay;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public RunStatus getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(RunStatus runStatus) {
		this.runStatus = runStatus;
	}

	public Set<PrRaid> getRaids() {
		return raids;
	}

	public void setRaids(Set<PrRaid> raids) {
		this.raids = raids;
	}

	public boolean isReady() {
		return client.isReady()
		        && getToday() < getPerDay()
		        && getDone() < getTotal()
		        && getRunStatus() == RunStatus.ACT;
	}

}