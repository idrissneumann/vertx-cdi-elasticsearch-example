package com.bblvertx.exception;

/**
 * Exception fonctionnelle.
 * 
 * @author Idriss Neumann <neumann.idriss@gmail.com>
 *
 */
public class FonctionalException extends Exception {

    private static final long serialVersionUID = 1L;

    public FonctionalException() {
        super();
    }

    /**
     * @param code
     *            d'erreur à renvoyer (les codes sont specifiés dans
     *            ICodesExcepetion)
     * @param cause
     *            l'exception à l'origine de cette erreur
     */
    public FonctionalException(String code, Throwable cause) {
        super(code, cause);
    }

    /**
     * @param code
     *            d'erreur à renvoyer (les codes sont specifiés dans
     *            ICodesExcepetion)
     */
    public FonctionalException(String code) {
        super(code);
    }

    /**
     * @param code
     *            d'erreur à renvoyer (les codes sont specifiés dans
     *            ICodesExcepetion)
     */
    public FonctionalException(Throwable cause) {
        super(cause);
    }

}
