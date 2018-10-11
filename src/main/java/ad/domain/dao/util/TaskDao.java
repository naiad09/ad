package ad.domain.dao.util;

import ad.domain.entities.task.Task;

public class TaskDao extends GenericDAO<Task> {

	public TaskDao() {
		super(Task.class);
	}
}
