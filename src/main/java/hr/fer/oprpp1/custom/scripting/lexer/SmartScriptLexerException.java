package hr.fer.oprpp1.custom.scripting.lexer;

public class SmartScriptLexerException extends RuntimeException {

    /**
     * Serial version of class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs new SmartScriptLexerException with <code>null</code> as its detail message.
     * The cause is not initialized.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Constructs a new SmartScriptLexerException with the specified detail message.
     * The cause is not initialized.
     *
     * @param message the detail message.
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }

    /**
     * Constructs a new SmartScriptLexerException with the specified cause and a detail
     * message of <code>(cause==null ? null : cause.toString())</code>.
     *
     * @param cause the cause.
     */
    public SmartScriptLexerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new SmartScriptLexerException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     */
    public SmartScriptLexerException(String message, Throwable cause) {
        super(message, cause);
    }

}
