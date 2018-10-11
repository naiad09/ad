package http.exceptions;

public class PostActionFailed extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PostActionFailed(String message) {
		super(message);
	}

}
