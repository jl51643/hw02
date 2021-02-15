package hr.fer.oprpp1.hw02.prob1;

/**
 * Model of simple lexical analyzer
 */
public class Lexer {

    /**
     * Input text.
     */
    private char[] data;

    /**
     * Current token
     */
    private Token token;

    /**
     * Index of first unprocessed symbol.
     */
    private int currentIndex;

    /**
     * Current lexer state.
     */
    private LexerState lexerState;

    /**
     * Constructs lexical analyzer for given text.
     *
     * @param text input text to tokenize.
     */
    public Lexer(String text) {
        this. data = text.toCharArray();
        this.token = getToken();
        this.currentIndex = 0;
        this.lexerState = LexerState.BASIC;
    }

    /**
     * Generates and returns next token.
     *
     * @return returns next token.
     * @Throws LexerException if problem occurs while generating new token.
     */
    public Token nextToken() {

        if (this.lexerState.equals(LexerState.EXTENDED))
            return nextTokenExtended();

        if (this.token != null && this.token.getType().equals(TokenType.EOF)) {
            throw new LexerException("No more tokens to generate");
        }

        if (currentIndex == data.length) {
            return this.token = new Token(TokenType.EOF, null);
        }

        String word = tryGenerateNewWordToken();
        if (!word.equals("")) {
            return this.token = new Token(TokenType.WORD, word);
        }

        Long number = tryGenerateNewNumberToken();
        if (number != Long.MIN_VALUE) {
            return this.token = new Token(TokenType.NUMBER, number);
        }

        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            nextToken();
        } else {
            return this.token = new Token(TokenType.SYMBOL, data[currentIndex++]);
        }
        return this.token;
    }

    /**
     * Generates next token when lexer is in extended state.
     *
     * @return returns new token when lexer is in extended state.
     */
    private Token nextTokenExtended() {

        if (this.token != null && this.token.getType().equals(TokenType.EOF)) {
            throw new LexerException("No more tokens to generate.");
        }

        if (currentIndex == data.length) {
            return this.token = new Token(TokenType.EOF, null);
        }

        String word = "";
        while (currentIndex < data.length &&
                data[currentIndex] != '#' &&
                !Character.isWhitespace(data[currentIndex])) {
            word += data[currentIndex++];
        }
        if (!word.equals(""))
            return this.token = new Token(TokenType.WORD, word);

        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            nextTokenExtended();
        }
        else {
            currentIndex++;
            return this.token = new Token(TokenType.SYMBOL, '#');
        }
        return this.token;
    }

    /**
     * Tries to make new token of type number.
     *
     * @return returns value of new number token or <code>MIN_VALUE</code> if next token can not be number.
     */
    private Long tryGenerateNewNumberToken() {
        Long number = Long.MIN_VALUE;
        String numberStr = "";
        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
            numberStr += data[currentIndex++];
        }
        if (numberStr.equals("")) {
            return number;
        }
        else {
            try {
                return Long.parseLong(numberStr);
            } catch (NumberFormatException e) {
                throw new LexerException("Problem occurred while generating new token.");
            }
        }
    }

    /**
     * Tries to make new token of type word.
     *
     * @return returns value of new word token or empty string if next token can not be word.
     * @throws LexerException if problem occurs while generating new token
     */
    private String tryGenerateNewWordToken () {
        String word = "";
        while (true) {
            if (currentIndex >= data.length)
                break;
            if (Character.isLetter(data[currentIndex])) {
                word += data[currentIndex++];
            } else if (Character.compare(data[currentIndex], '\\') == 0) {
                if (currentIndex+1 >= data.length)
                    throw new LexerException("Invalid escaping.");
                if (Character.isDigit(data[currentIndex+1])) {
                    word += data[currentIndex + 1];
                    currentIndex = currentIndex + 2;
                } else if (Character.compare(data[currentIndex+1], '\\') == 0) {
                    word += "\\";
                    currentIndex = currentIndex + 2;
                } else {
                    throw new LexerException("Invalid escaping.");
                }

            } else {
                break;
            }
        }
        return word;
    }

    /**
     * Returns last generated token. If called multiple times do not generate new token.
     *
     * @return returns last generated token.
     */
    public Token getToken() {
        return this.token;
    }

    /**
     * Stets state of this lexer.
     *
     * @param state state to set lexer into it.
     * @throws NullPointerException if given state is <code>null</code>.
     */
    public void setState(LexerState state) {
        if (state == null)
            throw new NullPointerException("State can not be null.");
        this.lexerState = state;
    }

}
