package hr.fer.oprpp1.custom.scripting.parser;

public class SmartScriptParserException extends RuntimeException{

    /**
     * Serial version of class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs new SmartScriptParserException with <code>null</code> as its detail message.
     * The cause is not initialized.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructs a new SmartScriptParserException with the specified detail message.
     * The cause is not initialized.
     *
     * @param message the detail message.
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructs a new SmartScriptParserException with the specified cause and a detail
     * message of <code>(cause==null ? null : cause.toString())</code>.
     *
     * @param cause the cause.
     */
    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new SmartScriptParserException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause the cause.
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

}
