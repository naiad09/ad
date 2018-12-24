package exec;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import ad.domain.dao.util.ForumDao;
import ad.domain.dao.util.GenericDAO;
import ad.domain.dao.util.HibernateUtil;
import ad.domain.dao.util.PrPostDao;
import ad.domain.dao.util.TaskDao;
import ad.domain.entities.Forum;
import ad.domain.entities.PrPost;
import ad.domain.entities.lastpost.ForumLastPost;
import ad.domain.entities.lastpost.ForumLastPostPK;
import ad.domain.entities.task.Task;
import http.exceptions.AuthFailedException;
import http.exceptions.NotFound404Exception;
import http.pages.LoginPage;
import http.pages.TopicPage;

public class PrPostScheduler {

	private static ForumDao forumDao = new ForumDao();
	private static TaskDao taskDao = new TaskDao();
	private static PrPostDao prPostDao = new PrPostDao();
	// private static List<Forum> allForums =
	// Arrays.asList(forumDao.get(2319));// forumDao.getAllShuffled();
	private static List<Forum> allForums = forumDao.getAllShuffled();
	private static GenericDAO<ForumLastPost> forumLastPostDao = new GenericDAO<>(ForumLastPost.class);

	private static Map<String, Long> forumLastPosts = new HashMap<>();

	private static LinkedList<PrPostTask> postQueue = new LinkedList<>();
	private List<PrAgent> agents;

	public PrPostScheduler() {
		agents = taskDao.getAll().stream()
		        .filter(Task::isReady)
		        .map(PrAgent::new)
		        .collect(Collectors.toList());

		agents.removeIf(a -> !a.login());

		agentsAddPosts();
	}

	private void run() {
		while (!postQueue.isEmpty() || agentsAddPosts()) {
			long currentTimeMillis = System.currentTimeMillis();
			Optional<PrPostTask> findFirst = postQueue.stream().filter(post -> {
				Long lastTime = forumLastPosts.get(post.client.getUrl());
				return lastTime == null || lastTime + 10000 < currentTimeMillis;
			}).findFirst();
			if (findFirst.isPresent()) {
				PrPostTask prPostTask = findFirst.get();
				postQueue.remove(prPostTask);
				prPostTask.execute();
			}
		}
	}

	private boolean agentsAddPosts() {
		agents.stream()
		        .flatMap(agent -> agent.getForumFor().stream()
		                .map(forum -> new PrPostTask(agent.client, forum, agent.topicPage, null)))
		        .forEach(postQueue::add);
		return !postQueue.isEmpty();
	}

	public static class PrPostTask {
		private Forum client, forum;
		private TopicPage clientTopicPage, codePage;
		private String backLink;

		public PrPostTask(Forum postHere, Forum code, TopicPage clientTopicPage, String backLink) {
			client = postHere;
			forum = code;
			this.clientTopicPage = clientTopicPage;
			this.backLink = backLink;
		}

		public void execute() {
			if (backLink == null) {
				if (!preCheck()) {
					return;
				}
			}

			String codeToPost = client.isClient() || backLink == null
			        ? forum.getCode()
			        : backLink + "\n\n" + forum.getCode();

			int clientPostId = clientTopicPage.post(codeToPost);
			String clientBackUrl = client.getPostLink(clientPostId);

			PrPost clientPrPost = new PrPost(client, clientBackUrl);
			prPostDao.saveOrUpdate(clientPrPost);

			forumLastPosts.put(client.getUrl(), System.currentTimeMillis());
			if (backLink == null) {
				PrPostTask prPostTask = new PrPostTask(forum, client, codePage, clientBackUrl);
				postQueue.addFirst(prPostTask);
			} else {
				saveForumLastPost(forum, client);
			}
		}

		private boolean preCheck() {
			LoginPage forumLoginPage = new LoginPage(forum.getUrl());
			try {
				forumLoginPage.login(forum.getAccount());
			}
			catch (AuthFailedException e1) {
				forum.setAccount(null);
				forumDao.saveOrUpdate(forum);
				postQueue.removeIf(p -> p.forum == forum);
				return false;
			}
			catch (RuntimeException e) {
				postQueue.removeIf(p -> p.forum == forum);
				return false;
			}

			try {
				codePage = forumLoginPage.getTopicPage(forum.getTopic());
			}
			catch (NotFound404Exception e) {
				forum.setTopic(0);
				forumDao.saveOrUpdate(forum);
				postQueue.removeIf(p -> p.forum == forum);
				return false;
			}

			if (clientTopicPage.isPresentedOnLastPage(forum)
			        || codePage.isPresentedOnLastPage(client)) {
				saveForumLastPost(forum, client);
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return " Post code of " + forum + " to " + client;
		}
	}

	public static class PrAgent {
		private final Task task;
		private TopicPage topicPage;
		private Forum client;

		public PrAgent(Task task) {
			this.task = task;
			client = task.getClient();
		}

		public List<Forum> getForumFor() {
			LocalDateTime now = LocalDateTime.now();
			return allForums.stream().filter(Forum::isReady)
			        .filter(forum -> forum.getId() != client.getId())
			        .filter(forum->forum.getVisitors()>4)
			        .filter(forum -> {
				        LocalDateTime lastPrDate = getLastPrDate(forum, client);
				        if (lastPrDate == null) {
					        return true;
				        } else {
					        long hours = Duration.between(now, lastPrDate).abs().toHours();
					        return hours > 4 && hours > 500 / forum.getVisitors();
				        }
			        })
			        .limit(Math.max((long) ((task.getPerDay() - task.getToday()) * 1.1), 0))
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

		public boolean login() {
			LoginPage clientLoginPage = new LoginPage(client.getUrl());
			clientLoginPage.login(client.getAccount());
			try {
				topicPage = clientLoginPage.getTopicPage(client.getTopic());
				return true;
			}
			catch (NotFound404Exception e) {
				end();
				return false;
			}
		}

		public void end() {
			client.setTopic(0);
			forumDao.saveOrUpdate(client);
			// TODO
		}

	}

	public static void main(String[] args) {
		try {
			new PrPostScheduler().run();
		}
		finally {
			HibernateUtil.shutdown();
		}
	}

	private static void saveForumLastPost(Forum forum, Forum client) {
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
}
