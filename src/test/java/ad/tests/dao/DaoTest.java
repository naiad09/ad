package ad.tests.dao;

import java.time.LocalDateTime;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import ad.domain.dao.util.ForumDao;
import ad.domain.entities.Forum;
import ad.tests.AbstractTest;

public class DaoTest extends AbstractTest {
	private ForumDao forumDao = new ForumDao();
	private Integer id;

	@Test
	public void daoTest() {
		System.out.println("Maven + Hibernate + Oracle");

		Forum newForum = new Forum("test string");
		forumDao.saveOrUpdate(newForum);

		id = newForum.getId();
		System.out.println(id);

		Forum retrievedForum = forumDao.get(id);
		assertEquals(retrievedForum.getUpdateDate(), LocalDateTime.now().withNano(0));
	}

	@AfterClass(alwaysRun = true)
	public void delete() {
		if (id != null) {
			Forum forum = forumDao.get(id);
			forumDao.delete(forum);
		}

	}
}
