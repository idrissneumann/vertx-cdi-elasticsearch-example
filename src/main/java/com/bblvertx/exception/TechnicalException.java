package com.bblvertx.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Exception technique.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class TechnicalException extends RuntimeException {
	private static final Logger LOGGER = LogManager.getLogger(TechnicalException.class);
	private static final long serialVersionUID = 1L;

	public TechnicalException() {
		super();
	}

	/***
	 * @param code
	 *            d'erreur à renvoyer (les codes sont specifiés dans
	 *            ICodesExcepetion)
	 * @param cause
	 *            l'exception à l'origine de cette erreur
	 */
	public TechnicalException(String code, Throwable cause) {
		super(code, cause);
	}

	/**
	 * 
	 * @param code
	 *            : code d'erreur à renvoyer (les codes sont specifiés dans
	 *            ICodesExcepetion)
	 */
	public TechnicalException(String code) {
		super(code);
	}

	/**
	 * @param cause
	 *            l'exception à l'origine de cette erreur
	 */
	public TechnicalException(Throwable cause) {
		super(cause);
		LOGGER.error(cause);
	}

}