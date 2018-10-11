package exec;

import ad.domain.dao.util.ForumDao;
import ad.domain.dao.util.HibernateUtil;
import ad.domain.entities.Forum;
import http.pages.LoginPage;
import http.pages.TopicPage;

public class Temp {
	public static void main(String[] args) {
		try {
			ForumDao forumDao = new ForumDao();
			Forum forum = forumDao.get(5762);

			LoginPage loginPage = new LoginPage("naiad.mybb.ru");
			loginPage.login("naiad", "rfhfre12");

			TopicPage topicPage = loginPage.getTopicPage(5);
			topicPage.post(forum.getCode());
		}
		finally {
			HibernateUtil.shutdown();
		}
	}
}
