package http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.stream.Stream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import http.exceptions.NotFound404Exception;

public class Client {
	private static final String HTTP = "http://";

	private final CloseableHttpClient client = HttpClientBuilder.create().build();
	private final HashMap<String, String> cookies = new HashMap<>();

	public Client() {
	}

	private void addHeaders(HttpUriRequest post, String referer) {
		String host = getHost(post);

		if (cookies.containsKey(host)) {
			post.addHeader("Cookie", cookies.get(host));
		}
		post.setHeader("User-Agent", "Mozilla/5.0");
		post.setHeader("Accept",
		        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");
		// referer is crusial
		post.setHeader("Referer", HTTP + referer);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Upgrade-Insecure-Requests", "1");
	}

	public HttpResponse execute(HttpUriRequest get) {
		try {
			String host = getHost(get);

			HttpResponse response = client.execute(get);
			if (404 == response.getStatusLine().getStatusCode()) {
				throw new NotFound404Exception("404 for " + get.getURI());
			}
			Header[] cookiesHeaders = response.getHeaders("Set-Cookie");
			Stream.of(cookiesHeaders).forEach(h -> {
				HeaderElement element = h.getElements()[0];
				String existingCookies = cookies.get(host);
				if (existingCookies == null) {
					existingCookies = element.getName() + "=" + element.getValue();
				} else {
					existingCookies += "; " + element.getName() + "=" + element.getValue();
				}
				cookies.put(host, existingCookies);
			});
			return response;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public HttpResponse getResponse(String uri, String referer) {
		uri = HTTP + uri;
		System.out.println("Get " + uri);
		HttpGet get = new HttpGet(uri);
		addHeaders(get, referer);
		HttpResponse response = execute(get);
		return response;
	}

	public HttpPost newPost(String uri, String referer) {
		uri = HTTP + uri;
		System.out.println("Post " + uri);
		HttpPost post = new HttpPost(uri);
		addHeaders(post, referer);
		return post;
	}

	public String executeAndGetResponse(HttpPost post) {
		HttpResponse response = execute(post);
		String result = getDocumentFromResponse(response);
		return result;
	}

	public String get(String uri, String referer) {
		HttpResponse response = getResponse(uri, referer);
		String result = getDocumentFromResponse(response);
		return result;
	}

	private static String getDocumentFromResponse(HttpResponse response) {
		try {
			System.out.println("Response Code : "
			        + response.getStatusLine().getStatusCode());

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();
			InputStreamReader in = new InputStreamReader(content, "windows-1251");
			BufferedReader rd = new BufferedReader(in);

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line + "\n");
			}
			rd.close();
			return result.toString();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getHost(HttpUriRequest get) {
		String string = get.getURI().toString();
		String replaceAll = string.replaceAll(HTTP, "").replaceAll("/.*", "");
		return replaceAll;
	}
}
