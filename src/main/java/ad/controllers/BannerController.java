package ad.controllers;

import java.util.List;

import ad.Application;
import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Account;
import ad.domain.entities.BannerTask;
import ad.domain.entities.Forum;
import ad.frames.EditBannerForm;
import ad.frames.TableInformationPanel;
import http.pages.LoginPage;
import http.pages.SignaturePage;

public class BannerController extends AbstractController<BannerTask> {

	public BannerController() {
		super(BannerTask.class);
	}

	public void newEntity(Forum forum) {
		EditBannerForm form = new EditBannerForm(forum);
		mainFrame().openFrame(form);
	}

	@Override
	public void save(BannerTask entity) {
		dao.saveOrUpdate(entity);
		entity.getForum().setBanner(entity);
		Application.forumController().edit(entity.getForum());
	}

	@Override
	public void delete(BannerTask entity) {
		entity.getForum().setBanner(null);
		super.delete(entity);
	}

	@Override
	public void edit(BannerTask entity) {
		EditBannerForm form = new EditBannerForm(entity);
		mainFrame().openFrame(form);
	}

	@Override
	public void displayAll(List<BannerTask> entities) {
		mainFrame().openFrame(TableInformationPanel.displayBannersList(entities));
	}

	public void updateSignatures(String signature) {
		new ForumDao().getAllClients().forEach(client -> {
			try {
				Account account = client.getAccount();

				LoginPage loginPage = new LoginPage(client.getUrl());
				loginPage.login(account.getLogin(), account.getPassword());

				SignaturePage signaturePage = loginPage.getIndexPage().getSignaturePage();
				signaturePage.setSignature(signature);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
