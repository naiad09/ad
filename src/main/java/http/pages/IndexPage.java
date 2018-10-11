package http.pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ad.domain.entities.Account;
import http.exceptions.NotFound404Exception;

public class IndexPage extends AbstractHttpPage {

	private static final Pattern TOPICS_PATTERN = Pattern.compile(
	        "<tr id=\"forum.*?>.*?<div class=\"tclcon\"><h3><a href=\"\\S+?\">(.+?)<\\/a><\\/h3>(.*?)<\\/div>"
	                + "\\s*<\\/div>\\s*<\\/td>.+?<td class=\"tc3\">(\\d+)<\\/td>\\s+<td class=\"tcr\">"
	                + "<a href=\"\\S+id=(\\d+).*?\">(.*?)<\\/a><br \\/><span>.*? - (.+?)<\\/span><\\/td>\\s+<\\/tr>",
	        Pattern.DOTALL);

	private static final List<String> prMarkers = Arrays.asList("еклам", "истовк", "пиар", "ваши", "dvertising");

	private static final List<String> badMarkers = Arrays.asList("остев");

	public IndexPage(String baseUrl) {
		super(baseUrl, "");
	}

	public int getProfileId() {
		String indexPage = openPage();
		Matcher matcher = Pattern.compile("var UserID = (\\d+);").matcher(indexPage);
		matcher.find();
		return Integer.valueOf(matcher.group(1));
	}

	public SignaturePage getSignaturePage() {
		return new SignaturePage(host, getProfileId());
	}

	public ForumHtmlScan getTopicId() {
		String response = openPage();
		List<ForumHtmlScan> scans = getScans(response);
		scans.sort((a1, a2) -> (a1.posts < a2.posts) ? 1 : -1);
		scans.removeIf(s -> badMarkers.stream().anyMatch(m -> (s.name + s.topicTitle).toLowerCase().contains(m)));
		scans.removeIf(s -> !prMarkers.stream().anyMatch(m -> s.getText().toLowerCase().contains(m)));
		scans.removeIf(s -> !s.getTopicTitle().matches(".*\\d.*"));
		if (!scans.isEmpty()) {
			ForumHtmlScan scan = scans.get(0);
			return scan;
		} else {
			System.err.println("Cannot find topic " + host);
			throw new NotFound404Exception("Cannot find topic " + host);
		}
	}

	private List<ForumHtmlScan> getScans(String response) {
		Matcher matcher = TOPICS_PATTERN.matcher(response);
		List<ForumHtmlScan> scans = new ArrayList<>();
		while (matcher.find()) {
			ForumHtmlScan scan = new ForumHtmlScan();
			scan.name = matcher.group(1);
			scan.text = matcher.group(2);
			scan.posts = Integer.valueOf(matcher.group(3));
			scan.topicNum = Integer.valueOf(matcher.group(4));
			scan.topicTitle = matcher.group(5);
			scans.add(scan);
		}
		return scans;
	}

	public Account getAccount() {
		String open = openPage();
		Matcher matcher = Pattern
		        .compile(
		                "(PR\\.nick|PiarNik|pLogin)\\s*=\\s*['\"](.*?)['\"].*?(PR\\.pass|PiarPas|pPass)\\s*=\\s*['\"](.*?)['\"];",
		                Pattern.DOTALL)
		        .matcher(open);
		if (matcher.find()) {
			return new Account(matcher.group(2), matcher.group(4));
		} else {
			System.err.println("Cannot find account");
			throw new NotFound404Exception("Cannot find account " + host);
		}
	}

	public int getVisitors() {
		String open = openPage();
		Matcher matcher = Pattern.compile("Посетили за сутки<\\/a> \\(Пользователей: <strong>(\\d+)<\\/strong>")
		        .matcher(open);
		if (matcher.find()) {
			return Integer.valueOf(matcher.group(1));
		} else {
			System.err.println("Cannot find visitors " + host);
			return 0;
		}
	}

	public boolean isActive() {
		String pageCode = openPage();
		return pageCode.contains("Сегодня") || pageCode.contains("Вчера");
	}

	public static class ForumHtmlScan {
		private String name;

		private String text;

		private int posts;

		private int topicNum;

		private String topicTitle;

		@Override
		public String toString() {
			return topicTitle;
		}

		public String getText() {
			return text + name + topicTitle;
		}

		public int getTopicNum() {
			return topicNum;
		}

		public String getTopicTitle() {
			return topicTitle;
		}

	}

}
