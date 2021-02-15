package hr.fer.oprpp1.hw02.prob1;

/**
 * Model of token in lexical analyze.
 */
public class Token {

    /**
     * Type of this token.
     */
    private TokenType type;

    /**
     * Value of this token.
     */
    private Object value;

    /**
     * Constructs new token of given type for given value.
     *
     * @param type Type of new token.
     * @param value Value of new token.
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns value of this token.
     *
     * @return returns value of this token.
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Returns type of this token.
     *
     * @return returns type of this token.
     */
    public TokenType getType() {
        return this.type;
    }

}
