package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Model of token in lexical analyze.
 */
public class SmartScriptToken {

    /**
     * Type of this token.
     */
    private SmartScriptTokenType type;

    /**
     * Value of this token
     */
    private Object value;

    /**
     * Constructs new token of given type for given value.
     *
     * @param type Type of new token.
     * @param value Value of new token.
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * @return returns Type of this token
     */
    public SmartScriptTokenType getType() {
        return type;
    }

    /**
     * @return returns value of this token.
     */
    public Object getValue() {
        return value;
    }
}
