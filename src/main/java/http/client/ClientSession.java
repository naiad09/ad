package http.client;

public class ClientSession {

	private static Client client;

	public static Client getClient() {
		if (client == null) {
			client = new Client();
		}
		return client;
	}

}
