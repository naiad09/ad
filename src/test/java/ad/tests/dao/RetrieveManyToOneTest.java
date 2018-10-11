package ad.tests.dao;

import java.util.List;

import org.testng.annotations.Test;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Forum;
import ad.domain.entities.PrPost;
import ad.tests.AbstractTest;

public class RetrieveManyToOneTest extends AbstractTest {
	private ForumDao forumDao = new ForumDao();

	@Test
	public void daoTest() {
		Forum forum = forumDao.get(1);
		List<PrPost> posts = forum.getPosts();

		assertFalse(posts.isEmpty());
	}
}
