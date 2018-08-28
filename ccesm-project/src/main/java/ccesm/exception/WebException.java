package ccesm.exception;


public class WebException extends Exception {

	private static final long serialVersionUID = -7051111821582521895L;

	public WebException() {
		super();
	}

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(Throwable cause) {
		super(cause);
	}

}
