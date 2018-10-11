package http.pages;

import java.util.HashMap;

import http.exceptions.AuthFailedException;

public class SignaturePage extends AbstractHttpPage {

	private static final String SIGNATURE_URL = "/profile.php?section=signature&id=";

	private final int profileId;

	public SignaturePage(String baseUrl, int profileId) {
		super(baseUrl, SIGNATURE_URL);
		this.profileId = profileId;
	}

	public void setSignature(String signature) {
		callGet(SIGNATURE_URL + profileId);

		HashMap<String, String> hiddenKeys = new HashMap<>();
		hiddenKeys.put("form_sent", "1");
		hiddenKeys.put("signature", signature);

		String result = callPost(SIGNATURE_URL + profileId, hiddenKeys);
		if (!result.contains("pun-redirect")) {
			throw new AuthFailedException("Cannot fill signature for " + host);
		}
	}

}
