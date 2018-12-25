package ad.controllers;

import java.util.List;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Forum;
import ad.frames.EditForumForm;
import ad.frames.TableInformationPanel;

public class ForumController extends AbstractController<Forum> {

	private boolean isClients;

	public ForumController() {
		this(false);
	}

	public ForumController(boolean isClients) {
		super(new ForumDao());
		this.isClients = isClients;
	}

	@Override
	public void displayAll() {
		ForumDao forumDao = ((ForumDao) dao);
		List<Forum> all = isClients ? forumDao.getAllClients() : forumDao.getAll();
		displayAll(all);
	}

	@Override
	public void displayAll(List<Forum> entities) {
		TableInformationPanel displayForumsList = TableInformationPanel
		        .displayForumsList((isClients ? "Clients" : "Forums"), entities);
		if (!isClients) {
			displayForumsList.addButton("New forum", e -> newEntity());
		}
		mainFrame().openFrame(displayForumsList);
	}

	@Override
	public void edit(Forum entity) {
		EditForumForm form = new EditForumForm(entity);
		mainFrame().openFrame(form);
	}

	public void newEntity() {
		EditForumForm form = new EditForumForm();
		mainFrame().openFrame(form);
	}

}
