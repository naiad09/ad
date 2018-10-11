package ad.tests.execs;

import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Account;
import ad.domain.entities.Forum;
import ad.tests.AbstractTest;

public class TestUpdate extends AbstractTest {

	private List<Forum> forums;
	private static ForumDao forumDao;

	@BeforeClass
	public void load() {
		forumDao = new ForumDao();
		forums = forumDao.getAll().stream()
		        .filter(f -> f.isReady())
		        // .limit(1)
		        .collect(Collectors.toList());
	}

	@DataProvider(parallel = true)
	public Object[] forums() {
		return forums.toArray();
	}

	@Test(dataProvider = "forums")
	public void testUpdate(Forum forum) {
		int topic = forum.getTopic();
		String topicTitle = forum.getTopicTitle();
		String code = forum.getCode();
		Account account = forum.getAccount();

		UpdateTopicTest.update(forum);

		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(topic, forum.getTopic(), "Topic");
		softAssert.assertEquals(topicTitle, forum.getTopicTitle(), "Topic title");
		softAssert.assertEquals(code, forum.getCode(), "Code");
		softAssert.assertEquals(account.getLogin(), forum.getAccount().getLogin(), "Login");
		try {
			softAssert.assertAll();
		}
		catch (AssertionError e) {
			forumDao.saveOrUpdate(forum);
		}
	}

}
