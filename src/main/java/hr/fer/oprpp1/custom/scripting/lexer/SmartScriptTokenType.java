package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Token types that can be used
 * {@link #EOF}
 * {@link #TEXT}
 * {@link #INTEGER}
 * {@link #DOUBLE}
 * {@link #OPERATOR}
 * {@link #VARIABLE}
 * {@link #FUNCTION}
 * {@link #TAG}
 * {@link #TAG_NAME}
 */
public enum SmartScriptTokenType {

    /**
     * EOF value
     */
    EOF,
    /**
     * String value
     */
    TEXT,
    /**
     * Start or end of tag value
     */
    TAG,
    /**
     * Integer value
     */
    INTEGER,
    /**
     * Double value
     */
    DOUBLE,
    /**
     * Operator symbol
     */
    OPERATOR,
    /**
     * Variable name
     */
    VARIABLE,
    /**
     * Function name
     */
    FUNCTION,
    /**
     * Tag name
     */
    TAG_NAME
}
