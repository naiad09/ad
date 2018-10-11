package exec;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import ad.domain.dao.util.ForumDao;
import ad.domain.dao.util.GenericDAO;
import ad.domain.dao.util.HibernateUtil;
import ad.domain.entities.Forum;
import ad.domain.entities.PrPost;
import ad.domain.entities.lastpost.ForumLastPost;
import ad.domain.entities.lastpost.ForumLastPostPK;
import http.exceptions.NotFound404Exception;
import http.pages.LoginPage;
import http.pages.TopicPage;

public class PrPostExecutor {
	private final ForumDao forumDao = new ForumDao();
	private final GenericDAO<PrPost> prPostDao = new GenericDAO<>(PrPost.class);
	private final GenericDAO<ForumLastPost> forumLastPostDao = new GenericDAO<>(ForumLastPost.class);

	private List<Forum> allForums;

	private void run() {
		allForums = forumDao.getAll();

		Forum client = forumDao.get(16);
		Forum forum = getForumFor(client).get(0);

		post(client, forum);
	}

	public void post(Forum client, Forum forum) {
		LoginPage clientLoginPage = new LoginPage(client.getUrl());
		clientLoginPage.login(client.getAccount());

		LoginPage forumLoginPage = new LoginPage(forum.getUrl());
		forumLoginPage.login(forum.getAccount());

		TopicPage clientTopicPage = clientLoginPage.getTopicPage(client.getTopic());
		TopicPage forumTopicPage;
		try {
			forumTopicPage = forumLoginPage.getTopicPage(forum.getTopic());
		}
		catch (NotFound404Exception e) {
			forum.setTopic(0);
			forumDao.saveOrUpdate(forum);
			return;
		}

		if (clientTopicPage.isPresentedOnLastPage(forum) || forumTopicPage.isPresentedOnLastPage(client)) {
			saveForumLastPost(forum, client);
			return;
		}
		int clientPostId = clientTopicPage.post(forum.getCode());
		String clientBackUrl = client.getPostLink(clientPostId);

		PrPost clientPrPost = new PrPost(client, clientBackUrl);
		prPostDao.saveOrUpdate(clientPrPost);

		int forumPostId = forum.isClient()
		        ? forumTopicPage.post(client.getCode())
		        : forumTopicPage.post(clientBackUrl, client.getCode());
		String forumBackUrl = forum.getPostLink(forumPostId);
		PrPost forumPost = new PrPost(forum, forumBackUrl);
		prPostDao.saveOrUpdate(forumPost);

		saveForumLastPost(forum, client);
		System.err.println("Successfully posted " + forumBackUrl);
	}

	public List<Forum> getForumFor(Forum client) {
		LocalDateTime now = LocalDateTime.now();
		return allForums.stream().filter(Forum::isReady)
		        .sorted((o1, o2) -> {
			        return o2.getVisitors() - o1.getVisitors();
		        })
		        .filter(forum -> forum.getId() != client.getId())
		        .filter(forum -> {
			        LocalDateTime lastPrDate = getLastPrDate(forum, client);
			        if (lastPrDate == null) {
				        return true;
			        } else {
				        long hours = Duration.between(now, lastPrDate).abs().toHours();
				        return hours > 4 && hours > 500 / forum.getVisitors();
			        }
		        })
		        .limit(5)
		        .collect(Collectors.toList());
	}

	private LocalDateTime getLastPrDate(Forum forum, Forum client) {
		Integer forumId = forum.getId();
		Integer clientId = client.getId();
		ForumLastPostPK pk = forumId.intValue() > clientId.intValue() ? new ForumLastPostPK(forumId, clientId)
		        : new ForumLastPostPK(clientId, forumId);
		ForumLastPost post = forumLastPostDao.get(pk);
		return post == null ? null : post.getDate();
	}

	private void saveForumLastPost(Forum forum, Forum client) {
		Integer forumId = forum.getId();
		Integer clientId = client.getId();
		ForumLastPostPK pk = forumId.intValue() > clientId.intValue() ? new ForumLastPostPK(forumId, clientId)
		        : new ForumLastPostPK(clientId, forumId);
		ForumLastPost lastPost = forumLastPostDao.get(pk);
		if (lastPost != null) {
			lastPost.setDate(LocalDateTime.now());
		} else {
			lastPost = new ForumLastPost(pk, LocalDateTime.now());
		}

		ForumLastPost lastPost2 = lastPost;
		forumLastPostDao.saveOrUpdate(lastPost2);
	}

	public static void main(String[] args) {
		try {
			new PrPostExecutor().run();
		}
		finally {
			HibernateUtil.shutdown();
		}
	}
}
