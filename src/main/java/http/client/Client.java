package http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
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
	private final HashMap<String, HashMap<String, String>> cookies = new HashMap<>();
	private final HashMap<String, String> referers = new HashMap<>();

	public Client() {
	}

	private void addHeaders(HttpUriRequest post) {
		String host = getHost(post);

		if (cookies.containsKey(host)) {
			String c = getCookies(host);
			// System.out.println(host + " = " + c);
			post.addHeader("Cookie", c);
		}
		post.setHeader("User-Agent", "Mozilla/5.0");
		post.setHeader("Accept",
		        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Accept-Encoding", "gzip, deflate");
		post.setHeader("Connection", "keep-alive");
		// referer is crusial
		post.setHeader("Referer", HTTP + referers.get(host));
		post.setHeader("Host", host);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Upgrade-Insecure-Requests", "1");
	}

	public String getCookies(String host) {
		HashMap<String, String> hashMap = cookies.get(host);
		String collect = "uid=" + hashMap.get("uid");
		if (hashMap.containsKey("mybb_ru")) {
			collect = "mybb_ru=" + hashMap.get("mybb_ru") + "; " + collect;
		}
		return collect;
	}

	public HttpResponse execute(HttpUriRequest get) {
		String host = getHost(get);
		HttpResponse response;
		try {
			try {
				response = client.execute(get);
			}
			catch (UnknownHostException e) {
				sleep(2000);
				response = client.execute(get);
			}
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (404 == response.getStatusLine().getStatusCode()) {
			throw new NotFound404Exception("404 for " + get.getURI());
		}
		Header[] cookiesHeaders = response.getHeaders("Set-Cookie");
		Stream.of(cookiesHeaders).forEach(h -> {
			HeaderElement element = h.getElements()[0];
			HashMap<String, String> existingCookies = cookies.get(host);
			if (existingCookies == null) {
				existingCookies = new HashMap<>();
			}

			existingCookies.put(element.getName(), element.getValue());
			cookies.put(host, existingCookies);
		});
		return response;
	}

	public HttpResponse getResponse(String uri) {
		referers.put(getHost(uri), uri);
		uri = HTTP + uri;
		System.out.println("Get " + uri);
		HttpGet get = new HttpGet(uri);
		addHeaders(get);
		HttpResponse response = execute(get);
		return response;
	}

	public HttpPost newPost(String uri) {
		uri = HTTP + uri;
		System.out.println("Post " + uri);
		HttpPost post = new HttpPost(uri);
		addHeaders(post);
		return post;
	}

	public String executeAndGetResponse(HttpPost post) {
		HttpResponse response = execute(post);
		String result = getDocumentFromResponse(response);
		return result;
	}

	public String get(String uri) {
		HttpResponse response = getResponse(uri);
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
		return getHost(string);
	}

	private static String getHost(String string) {
		String replaceAll = string.replaceAll(HTTP, "").replaceAll("/.*", "");
		return replaceAll;
	}

	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException e) {
		}
	}
}
