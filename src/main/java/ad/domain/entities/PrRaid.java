package ad.domain.entities;

import java.io.Serializable;
import java.time.Duration;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ad.domain.entities.task.Task;

@Entity
@Table(name = "pr_raid")
public class PrRaid implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, updatable = false)
	private Integer id;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "start", nullable = false, insertable = false, updatable = false)
	private LocalDateTime start;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
	        name = "task_in_raid",
	        joinColumns = { @JoinColumn(name = "raid") },
	        inverseJoinColumns = {
	                @JoinColumn(name = "task") })
	private List<Task> tasks = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "prRaid", cascade = { CascadeType.ALL })
	private List<PrExchange> prExchanges = new ArrayList<>();

	@Transient
	public LocalDateTime getEnd() {
		int size = prExchanges.size();
		return size == 0 ? start : prExchanges.get(size - 1).getPrPostByClientPost().getDate();
	}

	public Duration getDuration() {
		LocalDateTime end = getEnd();
		return Duration.between(start, end);
	}

	public Integer getId() {
		return id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<PrExchange> getPrExchanges() {
		return prExchanges;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void setPrExchanges(List<PrExchange> prExchanges) {
		this.prExchanges = prExchanges;
	}
}