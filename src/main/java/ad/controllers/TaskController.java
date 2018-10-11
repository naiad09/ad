package ad.controllers;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import ad.Application;
import ad.domain.entities.Forum;
import ad.domain.entities.task.Task;
import ad.frames.EditTaskForm;
import ad.frames.TableInformationPanel;

public class TaskController extends AbstractController<Task> {

	public TaskController() {
		super(Task.class);
	}

	public void newEntity(Forum forum) {
		EditTaskForm form = new EditTaskForm(forum);
		mainFrame().openFrame(form);
	}

	@Override
	public void save(Task entity) {
		dao.saveOrUpdate(entity);
		List<Task> tasks = entity.getClient().getTasks();
		if (!tasks.contains(entity)) {
			tasks.add(entity);
		}
		Application.forumController().edit(entity.getClient());
	}

	@Override
	public void delete(Task entity) {
		entity.getClient().getTasks().remove(entity);
		super.delete(entity);
	}

	@Override
	public void edit(Task entity) {
		EditTaskForm form = new EditTaskForm(entity);
		mainFrame().openFrame(form);
	}

	@Override
	public void displayAll(List<Task> entities) {
		mainFrame().openFrame(TableInformationPanel.displayTasksList(entities));
	}

	public void displayReport() {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
		StringBuilder code = new StringBuilder("[quote][align=center]\r\n"
		        + "[size=14][b]" + date + "[/b][/size][/align]\r\n"
		        + "[table]\n\n");

		List<Task> all = dao.getAll();
		for (int i = 0; i < all.size(); i++) {
			Task task = all.get(i);
			code.append("[tr][td rowspan=2 width=10%][size=45]"
			        + ((i % 2 == 0) ? "🍁" : "🍂")
			        + "[/size][/td][td width=1% rowspan=2][/td]\r\n" +
			        "[td colspan=4][size=12][b][url=http://" + task.getClient().getUrl() + "]"
			        + task.getClient().getForumName() + "[/url][/b][/size][/td][/tr]\r\n" +
			        "[tr][td][i]Всего[/i]: " + task.getTotal()
			        + "[/td][td][i]Сегодня[/i]: " + task.getToday()
			        + "[/td][td][i]Осталось[/i]: " + task.getRest()
			        + "[/td][td]" + task.getPercentageDone()
			        + "%[/td][/tr]\n\n");
		}

		code.append("[/table][/quote]");

		JTextArea textAreaCode = new JTextArea(code.toString());
		textAreaCode.setFont(new Font("Consolas", Font.PLAIN, 12));
		textAreaCode.setBorder(new LineBorder(Color.GRAY));
		mainFrame().openFrame(textAreaCode);
	}
}
