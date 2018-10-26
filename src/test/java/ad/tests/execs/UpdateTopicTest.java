package ad.tests.execs;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Forum;
import ad.tests.AbstractTest;
import http.pages.IndexPage;
import http.pages.IndexPage.ForumHtmlScan;
import http.pages.LoginPage;
import http.pages.TopicPage;

public class UpdateTopicTest extends AbstractTest {

	private List<Forum> forums;
	private static ForumDao forumDao;

	@BeforeClass
	public void load() {
		forumDao = new ForumDao();
		forums = forumDao.getAll().stream()
		        .filter(f -> !f.isReady())
		        .collect(Collectors.toList());
	}

	@DataProvider
	public Object[] forums() {
		return forums.toArray();
	}

	@Test(dataProvider = "forums")
	public void testTopicUpdate(Forum forum) {
		try {
			if (update(forum)) {

			forumDao.saveOrUpdate(forum);
			System.out.println("Saved forum " + forum);
				assertTrue(forum.isAllSet());
			} else {
				throw new SkipException("Forum "+forum.getUrl()+"is deleted");
			}
		}
		catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public static boolean update(Forum forum) {
		IndexPage indexPage = new IndexPage(forum.getUrl());
		try {
			indexPage.openPage();
		}
		catch (Exception e) {
			forumDao.delete(forum);
			return false;
		}

		int visitors = indexPage.getVisitors();
		if (visitors < 4) {
			if (!forum.isClient() || forum.getPosts().isEmpty()) {
				forumDao.delete(forum);
				return false;
			}
		}
		forum.setVisitors(visitors);

		ForumHtmlScan scan = indexPage.getTopicId();

		if (forum.getAccount() == null) {
			forum.setAccount(indexPage.getAccount());
		}

		LoginPage loginPage = new LoginPage(forum.getUrl());
		loginPage.login(forum.getAccount());

		int topicNum = scan.getTopicNum();
		TopicPage topicPage = loginPage.getTopicPage(topicNum);
		String code = topicPage.getCode();

		forum.setTopic(topicNum);
		forum.setTopicTitle(scan.getTopicTitle());
		forum.setCode(code);
		return true;
	}
}
