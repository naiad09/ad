package ad.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import ad.Application;
import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Account;
import ad.domain.entities.Forum;
import ad.domain.entities.task.RunStatus;
import ad.domain.entities.task.Task;
import ad.frames.EditTaskForm;
import ad.frames.TableInformationPanel;
import http.pages.LoginPage;
import http.pages.TopicPage;

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

	public String buildReport() {
		String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"));
		StringBuilder code = new StringBuilder("[align=center]\r\n"
		        + "[size=14][b]" + date + "[/b][/size][/align]\r\n"
		        + "[table]\n\n");

		List<Task> all = dao.getAll()
		        .stream()
		        .filter(t -> t.getRunStatus() == RunStatus.ACT)
		        .filter(t -> t.getToday() > 10)
		        .collect(Collectors.toList());
		for (int i = 0; i < all.size(); i++) {
			Task task = all.get(i);
			code.append("[tr][td rowspan=2 width=7%][img]"
			        + ((i % 2 == 0)
			                ? "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/microsoft/153/maple-leaf_1f341.png"
			                : "https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/microsoft/153/fallen-leaf_1f342.png")
			        + "[/img][/td][td width=1% rowspan=2][/td]\r\n" +
			        "[td colspan=4][size=12][b][url=http://" + task.getClient().getUrl() + "]"
			        + task.getClient().getForumName() + "[/url][/b][/size][/td][/tr]\r\n" +
			        "[tr][td][i]Всего[/i]: " + task.getTotal()
			        + "[/td][td][i]Сегодня[/i]: " + task.getToday()
			        + "[/td][td][i]Осталось[/i]: " + task.getRest()
			        + "[/td][td]" + task.getPercentageDone()
			        + "%[/td][/tr]\n\n");
		}

		code.append("[/table]");

		return code.toString();
	}

	public void postPRReport(String string) {
		ForumDao forumDao = new ForumDao();
		Forum white = forumDao.get(16);

		Account account = white.getAccount();

		LoginPage loginPage = new LoginPage(white.getUrl());
		loginPage.login(account.getLogin(), account.getPassword());

		TopicPage topicPage = loginPage.getTopicPage(3929);
		topicPage.post(string);
	}
}
