package hr.fer.oprpp1.hw02.prob1;

/**
 * Thrown if problem occurs while generating new token.
 */
public class LexerException extends RuntimeException{

    /**
     * Serial version of class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs new LexerException with <code>null</code> as its detail message.
     * The cause is not initialized.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructs a new LexerException with the specified detail message.
     * The cause is not initialized.
     *
     * @param message the detail message.
     */
    public LexerException(String message) {
        super(message);
    }

    /**
     * Constructs a new LexerException with the specified cause and a detail
     * message of <code>(cause==null ? null : cause.toString())</code>.
     *
     * @param cause the cause.
     */
    public LexerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new LexerException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     */
    public LexerException(String message, Throwable cause) {
        super(message, cause);
    }

}
