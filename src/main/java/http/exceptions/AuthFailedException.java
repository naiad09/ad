package http.exceptions;

public class AuthFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AuthFailedException(String message) {
		super(message);
	}

}
