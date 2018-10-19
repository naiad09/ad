package http.pages;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import http.client.Client;
import http.client.ClientSession;

public class AbstractHttpPage {

	protected final Client client;
	protected final String host;
	private final String fullUrl;

	private String openedPage;

	public AbstractHttpPage(String host, String fullUrl) {
		client = ClientSession.getClient();
		this.host = host;
		this.fullUrl = fullUrl;
	}

	public String openPage() {
		if (openedPage == null) {
			reload();
		}
		return openedPage;
	}

	public String reload() {
		openedPage = callGet(fullUrl);
		return openedPage;
	}

	protected String callPost(String postUrl, HashMap<String, String> hiddenKeys) {
		HttpPost post = client.newPost(host + postUrl);

		List<NameValuePair> postParams = hiddenKeys.entrySet().stream()
		        .map(e -> new BasicNameValuePair(e.getKey(), e.getValue())).collect(Collectors.toList());

		post.setEntity(new UrlEncodedFormEntity(postParams, Charset.forName("windows-1251")));

		String result = client.executeAndGetResponse(post);
		return result;
	}

	public String getFullUrl() {
		return host + fullUrl;
	}

	protected String callGet(String url) {
		return client.get(getFullUrl());
	}

}
