package http.pages;

import java.util.HashMap;

import ad.domain.entities.Account;
import http.exceptions.AuthFailedException;

public class LoginPage extends AbstractHttpPage {

	private static final String LOGIN_PHP_ACTION_IN = "/login.php?action=in";

	public LoginPage(String baseUrl) {
		super(baseUrl, LOGIN_PHP_ACTION_IN);
	}

	public void login(Account account) {
		login(account.getLogin(), account.getPassword());
	}

	public void login(String login, String password) {
		HashMap<String, String> hiddenKeys = new HashMap<>();
		hiddenKeys.put("form_sent", "1");
		hiddenKeys.put("req_username", login);
		hiddenKeys.put("req_password", password);
		hiddenKeys.put("login", "");

		String result = callPost(LOGIN_PHP_ACTION_IN, hiddenKeys);
		if (!result.contains("pun-redirect")) {
			throw new AuthFailedException("Auth failed for " + host + " with " + login + "/" + password);
		}
	}

	public IndexPage getIndexPage() {
		return new IndexPage(host);
	}

	public TopicPage getTopicPage(int topicNum) {
		return new TopicPage(host, topicNum);
	}

}
