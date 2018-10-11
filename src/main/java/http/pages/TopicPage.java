package http.pages;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.NumericEntityEscaper;

import ad.domain.entities.Forum;
import http.Decode;
import http.exceptions.NotFound404Exception;
import http.exceptions.PostActionFailed;

public class TopicPage extends AbstractHttpPage {

	private static final String VIEWTOPIC_PHP_ID = "/viewtopic.php?id=";

	private static final String POST_PHP_TID = "/post.php?tid=";

	private final int topicNum;

	public TopicPage(String baseUrl, int topicNum) {
		super(baseUrl, VIEWTOPIC_PHP_ID + topicNum);
		this.topicNum = topicNum;

		String openPage = openPage();
		if (!openPage.contains("textarea id=\"main-reply\"")) {
			throw new NotFound404Exception("Topic is ended");
		}
	}

	public int post(String backUrl, String text) {
		return post(backUrl + "\n\n" + text);
	}

	public int post(String text) {
		HashMap<String, String> hiddenKeys = openPageAndDecode();

		hiddenKeys.put("form_sent", "1");
		hiddenKeys.put("form_user", getCurrentLogin());

		text = new AggregateTranslator(
		        NumericEntityEscaper.between(0x00c0, 0x040f),
		        NumericEntityEscaper.between(0x0450, 0xffff)).translate(text);
		hiddenKeys.put("req_message", text);

		String postUrl = POST_PHP_TID + topicNum;
		String result = callPost(postUrl, hiddenKeys);

		if (!result.contains("pun-redirect")) {
			throw new PostActionFailed("Failed to post on " + host + postUrl);
		}

		try {
			Matcher matcher = Pattern.compile("viewtopic\\.php\\?pid=(\\d+)#").matcher(result);
			matcher.find();
			return Integer.valueOf(matcher.group(1));
		}
		catch (NumberFormatException e) {
			throw new PostActionFailed("Cannot find post id in redirect: " + host + postUrl);
		}
	}

	private String getCurrentLogin() {
		String openPage = openPage();
		Matcher matcher = Pattern.compile("var UserLogin = '(.*?)';").matcher(openPage);
		matcher.find();
		return matcher.group(1);
	}

	private HashMap<String, String> openPageAndDecode() {
		String response = openPage();
		Decode decode = new Decode();
		return decode.decode(response);
	}

	public String openPage(int page) {
		return callGet(VIEWTOPIC_PHP_ID + topicNum + "&p=" + page);
	}

	public HashSet<String> findNewForums() {
		HashSet<String> links = new HashSet<>();

		int lastPage = getLastPage();
		for (int i = 1; i <= lastPage; i++) {
			String openPage = openPage(i);
			Matcher linksMatcher = Pattern.compile("click.php\\?http:\\/\\/(?:www\\.)?([^\\/]*?)[\"\\/]")
			        .matcher(openPage);
			while (linksMatcher.find()) {
				links.add(linksMatcher.group(1));
			}
		}
		return links;
	}

	public String openLastPage() {
		return openPage(getLastPage());
	}

	public boolean isPresentedOnLastPage(Forum forum) {
		String openLastPage = openLastPage();
		String link = "<a href=\"http://" + host + "/click.php?http://" + forum.getUrl();
		return openLastPage.contains(link);
	}

	private int getLastPage() {
		Matcher pageCountMatcher = Pattern.compile("<link rel=\"last\".*p=(\\d+)\"").matcher(reload());
		if (pageCountMatcher.find()) {
			return Integer.valueOf(pageCountMatcher.group(1));
		} else {
			return 1;
		}
	}

	public String getCode() {
		Matcher matcher = Pattern.compile("<pre>(((?!pre>).)*?" + host + ".*?)<\\/pre>", Pattern.DOTALL)
		        .matcher(openPage());
		if (matcher.find()) {
			return StringEscapeUtils.unescapeHtml4(matcher.group(1));
		} else {
			System.err.println("Cannot find code " + host + VIEWTOPIC_PHP_ID + topicNum);
			throw new NotFound404Exception("Cannot find code " + host + VIEWTOPIC_PHP_ID + topicNum);
		}
	}

}
