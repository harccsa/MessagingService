package com.voxpilot.mediacontrol;

/**
 * MediaControlException
 * 
 * This is thrown by Media Controller when operations
 * fail.
 */
public class MediaControlException extends Exception {

	private static final long serialVersionUID = -2650075321986657311L;

	public MediaControlException() {
		super();
	}

	public MediaControlException(String message) {
		super(message);
	}

	public MediaControlException(Throwable cause) {
		super(cause);
	}

	public MediaControlException(String message, Throwable cause) {
		super(message, cause);

	}

}
