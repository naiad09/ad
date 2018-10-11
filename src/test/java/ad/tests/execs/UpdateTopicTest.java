package ad.tests.execs;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Forum;
import ad.tests.AbstractTest;
import http.pages.IndexPage;
import http.pages.IndexPage.ForumHtmlScan;
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

	@DataProvider(parallel = true)
	public Object[] forums() {
		return forums.toArray();
	}

	@Test(dataProvider = "forums")
	public void testTopicUpdate(Forum forum) {
		update(forum);

		forumDao.saveOrUpdate(forum);
		System.out.println("Saved forum " + forum);
		assertTrue(forum.isReady());
	}

	public static void update(Forum forum) {
		IndexPage indexPage = new IndexPage(forum.getUrl());
		try {
			indexPage.openPage();
		}
		catch (Exception e) {
			forumDao.delete(forum);
			return;
		}

		int visitors = indexPage.getVisitors();
		if (visitors < 4) {
			if (!forum.isClient() || forum.getPosts().isEmpty()) {
				forumDao.delete(forum);
			}
		}
		forum.setVisitors(visitors);

		ForumHtmlScan scan = indexPage.getTopicId();

		int topicNum = scan.getTopicNum();
		TopicPage topicPage = new TopicPage(forum.getUrl(), topicNum);
		String code = topicPage.getCode();

		forum.setTopic(topicNum);
		forum.setTopicTitle(scan.getTopicTitle());
		forum.setCode(code);

		if (forum.getAccount() == null) {
			forum.setAccount(indexPage.getAccount());
		}
	}
}
