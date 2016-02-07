package com.bblvertx.exception;

/**
 * Exception pour les paramètres invalides dans une route.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur avec message.
	 * 
	 * @param msg
	 */
	public BadRequestException(String msg) {
		super(msg);
	}

	/**
	 * Constructeur sans message.
	 */
	public BadRequestException() {
		super();
	}
}
