package ad.controllers;

import java.util.List;
import java.util.stream.Collectors;

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
		mainFrame().openFrame(TableInformationPanel.displayBannersListWithCode(entities));
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

	public String buildSignature() {
		List<BannerTask> banners = dao.getAll();

		StringBuilder code = new StringBuilder("[align=center]");
		List<BannerTask> small = banners.stream()
		        .filter(b -> !b.isBig())
		        .collect(Collectors.toList());

		int cutSize = small.size() > 6 ? small.size() / 2 : -1;

		for (int i = 0; i < small.size(); i++) {
			BannerTask b = small.get(i);
			if (i == cutSize) {
				code.append("\n");
			}
			code.append("[url=http://" + b.getForum().getUrl() + "][img]" + b.getImage() + "[/img][/url] ");
		}

		banners.stream()
		        .filter(b -> b.isBig())
		        .forEach(b -> code.append("\n[url=http://" + b.getForum().getUrl()
		                + "][img]" + b.getImage() + "[/img][/url]"));

		code.append("[/align]");

		return code.toString();
	}
}
