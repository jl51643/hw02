package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Model of lexical analyzer
 */
public class SmartScriptLexer {

    /**
     * Input text.
     */
    private char[] data;

    /**
     * Current token.
     */
    private SmartScriptToken token;

    /**
     * Index of first unprocessed symbol.
     */
    private int currentIndex;

    /**
     * Current lexer state.
     */
    private SmartScriptLexerState lexerState;

    /**
     * Boolean flag for tag mode of working.
     */
    private boolean hasTagName;

    /**
     * Constructs lexical analyzer for given text.
     *
     * @param text input text to tokenize.
     */
    public SmartScriptLexer(String text) {
        this.data = text.toCharArray();
        this.token = getToken();
        this.currentIndex = 0;
        this.lexerState = SmartScriptLexerState.BASIC;
    }

    /**
     * Generates and returns next token
     *
     * @return returns next token.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    public SmartScriptToken nextToken() {

        /*if current token is EOF token there is no more tokens to generate*/
        if (this.token != null && this.token.getType().equals(SmartScriptTokenType.EOF)) {
            throw new SmartScriptLexerException("No more tokens to generate");
        }
        /*if we are at the end of data generate EOF token*/
        if (currentIndex == data.length) {
            return this.token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
        }
        /*if we are in state TAG tokens generate in different way so we call nextTokenTag()*/
        if (this.lexerState.equals(SmartScriptLexerState.TAG)) {
            return nextTokenTag();
        }
        /*we have more tokens and we are not in tag so we are trying to make next TEXT token*/
        String text = tryGenerateNewTextToken();
        if (!text.equals("")) {
            /*if text is not empty we have next TEXT token*/
            return this.token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
        } else {
            /*next token is symbol for start of tag*/
            currentIndex += 2;
            return this.token = new SmartScriptToken(SmartScriptTokenType.TAG, "start");
        }    
    }

    /**
     * Generates new token while lexer is in TAG state.
     *
     * @return returns new token
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private SmartScriptToken nextTokenTag() {
        /*if this tag has not name finds possible tag name*/
        if (!this.hasTagName) {
            String tagName = generateTagNameToken();
            this.hasTagName = true;
            return this.token = new SmartScriptToken(SmartScriptTokenType.TAG_NAME, tagName);
        }
        /*quote mark signals start of new text token inside of tag*/
        if (data[currentIndex] == '\"') {
            String text = generateNewTextTokenTag();
            return this.token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
        }
        /*if we are in TAG and not in side of quotes try generate variable*/
        String text = tryGenerateNewVariableToken();
        if (!text.equals("")) {
            return this.token = new SmartScriptToken(SmartScriptTokenType.VARIABLE, text);
        }
        /*if next char is number generate INTEGER or DOUBLE token
        * number can be negative so we also have to check if next char is minus operator
        * or next character is start of negative number*/
        if (Character.isDigit(data[currentIndex]) || data[currentIndex] == '-') {
            /*we are looking forward in array data to determine type of number*/
            String numberType = seekForNumberType();
            /*if return value of method equals to string "NaN" minus character is operator*/
            if (numberType.equals("NaN")) {
                return this.token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex++]);
                /*if return value of method equals to string "INTEGER" we generate new INTEGER token"*/
            } else if (numberType.equals("INTEGER")) {
                int number = generateNewIntegerToken();
                return this.token = new SmartScriptToken(SmartScriptTokenType.INTEGER, number);
                /*if return value of method equals to string "DOUBLE" we generate new DOUBLE token"*/
            } else if (numberType.equals("DOUBLE")){
                double number = generateNewDoubleToken();
                return this.token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, number);
            } else {
                throw new SmartScriptLexerException("Problem occurred while generating new token.");
            }
        }
        /*function names start with "@" symbol*/
        if (data[currentIndex] == '@') {
            String functionName = generateNewFunctionToken();
            return this.token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, functionName);
        }
        /*check if next token can be operator*/
        if (isOperator()) {
            return this.token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, data[currentIndex++]);
        }
        /*check if next token is end of tag*/
        if (isEndOfTag()){
            currentIndex += 2;
            return this.token = new SmartScriptToken(SmartScriptTokenType.TAG, "end");
        }
        /*if we are in tag but outside quotes escaping is illegal*/
        if (data[currentIndex] == '/') {
            throw new SmartScriptLexerException("Problem occurred while generating new token. Illegal escaping.");
        }
        /*skipp whitespaces and try generate new token*/
        if (Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
            nextTokenTag();
        }
        return this.token;
    }

    /**
     * Tries to generate valid tag name.
     * Valid tag name i symbol "=" or valid variable name
     * which starts by letter and after follows zero or more letters,
     * digits or underscores.
     *
     * @return returns tag name.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private String generateTagNameToken() {
        String tagName = "";
        /*leading whitespaces are ignorable*/
        while (currentIndex < data.length && Character.isWhitespace(data[currentIndex])) {
            currentIndex++;
        }
        if (data[currentIndex] == '=') {
            return tagName += data[currentIndex++];
        } else {
            return tagName = tryGenerateNewVariableToken();
        }
    }

    /**
     * Returns true if lexer comes across symbol for end of tag.
     * Symbol for end of tag is "$}".
     *
     * @return returns true only if lexer comes across symbol for end of tag, false otherwise.
     */
    private boolean isEndOfTag() {

        if (currentIndex < data.length && data[currentIndex] == '$') {
            if (currentIndex+1 < data.length && data[currentIndex+1] == '}')
                return true;
        }
        return false;
    }

    /**
     * Returns true if next character is operator.
     * Operators are "+", "-", "*", "/", "^".
     *
     * @return returns true only if next character is operator, false otherwise.
     */
    private boolean isOperator() {
        switch (data[currentIndex]) {
            case '+', '-', '*', '/', '^' -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Returns valid function name.
     * Valid function name starts with "@" symbol
     * and  after which follows a letter and after
     * than can follow zero or more letters,
     * digits or underscores
     *
     * @return returns valid function name.
     */
    private String generateNewFunctionToken() {
        String name = "";
        name += data[currentIndex++];
        name += tryGenerateNewVariableToken();

        return name;
    }

    /**
     * Returns value of new double type token.
     *
     * @return returns value of new double type token.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private double generateNewDoubleToken() {
        String numberStr = "";
        /*we build double constant as two integer constants separated by floating point*/
        numberStr += generateNewIntegerToken();
        if (data[currentIndex] == '.') {
            if (currentIndex+1 < data.length && Character.isDigit(data[currentIndex+1])) {
                numberStr += data[currentIndex++];
                numberStr += generateNewIntegerToken();
            }
        }
        try {
            return Double.parseDouble(numberStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new SmartScriptLexerException("Problem occurred while generating new token.");
        }
    }

    /**
     * Returns value of next integer token.
     *
     * @return returns value of next integer token.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private int generateNewIntegerToken() {
        String numberStr = "";
        while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
            numberStr += data[currentIndex++];
        }
        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new SmartScriptLexerException("Problem occurred while generating new token.");
        }
    }

    /**
     * Checks if next token can be integer, double or "-" operator.
     *
     * @return returns INTEGER if next token is integer type,
     * DOUBLE if next token is double type
     * or NaN if next token is operator "-".
     */
    private String seekForNumberType() {
        String numberType = "";
        /*Helper variable to move around array*/
        int i = 0;

        if (data[currentIndex] == '-') {
            /*if after minus comes whitespace it is operator*/
            if (currentIndex+1 < data.length && Character.isWhitespace(data[currentIndex+1])) {
                return numberType = "NaN";
                /*if after minus comes digit it is negative number*/
            } else if (currentIndex+1 < data.length && Character.isDigit(data[currentIndex+1])) {
                /*move for one place that represents minus*/
                i++;
            }
        } else {
            /*move through number*/
            while (Character.isDigit(data[currentIndex + i])) {
                i++;
            }
            /*if after sequence of digits comes point it might be number type double*/
            if (currentIndex + i + 1 < data.length && data[currentIndex + i +1] == '.') {
                /*move over point*/
                i++;
                /*if after point comes either one digit number is double*/
                if (Character.isDigit(data[currentIndex + i + 1])) {
                    return numberType = "DOUBLE";
                }
            }
            else {
                return numberType = "INTEGER";
            }
        }
        return numberType;
    }

    /**
     *  Tries to generate new valid variable name.
     *  Valid variable name starts by letter and after follows zero or more letters,
     *  digits or underscores.
     *
     * @return returns new variable token name.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private String tryGenerateNewVariableToken() {

        String variableName = "";
        while (true) {
            if (currentIndex >= data.length) {
                throw new SmartScriptLexerException("Problem occurred while generating new token. Index out of bounds.");
            }
            if (Character.isLetter(data[currentIndex])) {
                variableName += data[currentIndex++];
            } else if (Character.isDigit(data[currentIndex])) {
                if (variableName.length() >= 1) {
                    variableName += data[currentIndex++];
                } else {
                    /*variable name is empty string because variable name can not start with digit*/
                    return variableName;
                }
            } else if (data[currentIndex] == '_') {
                if (variableName.length() >= 1) {
                    variableName += data[currentIndex++];
                } else {
                    /*variable name is empty string because variable name can not start with underscore*/
                    return variableName;
                }
            } else {
                break;
            }
        }
        return variableName;
    }

    /**
     * Returns value of new text token.
     *
     * @return returns value of new text token.
     * @throws SmartScriptLexerException if problem occurs while generating new text token.
     */
    private String generateNewTextTokenTag() {
        String text = "\"";
        /*skipping quote symbol*/
        currentIndex++;
        if (currentIndex >= data.length)
            throw new SmartScriptLexerException("Problem occurred while generating new token. Index out of bounds.");
        while (data[currentIndex] != '\"') {
            if (currentIndex >= data.length) {
                throw new SmartScriptLexerException("Problem occurred while generating new token. Index out of bounds");
            }
            if (data[currentIndex] == '\\') {
                if (currentIndex+1 < data.length && isEscapingLegal(data[currentIndex+1])) {
                    text += data[currentIndex] + data[currentIndex+1];
                    currentIndex += 2;
                }
                else
                    throw new SmartScriptLexerException("Problem occurred while generating new token. Illegal escaping " + data[currentIndex] + data[currentIndex+1]);
            } else {
                text += data[currentIndex++];
            }
        }
        /*adding and skipping quote symbol*/
        return text + data[currentIndex++];
    }

    /**
     * Checks if escaped symbol is legal to escape.
     * Symbols legal to escape are \, ", n, r, t
     *
     * @param s symbol that is escaped
     * @return returns true if given symbol is legal to escape, false otherwise
     */
    private boolean isEscapingLegal(char s) {
        switch (s) {
            case '\\', '\"', 'n', 'r', 't' -> {
                return true;
            }
        }
        return false;
    }

    /**
     * Tries to generate new text token.
     *
     * @return returns value of new text token or empty string if next token can not be text token.
     * @throws SmartScriptLexerException if problem occurs while generating new token.
     */
    private String tryGenerateNewTextToken() {
        String text = "";
        while (currentIndex < data.length) {
            if (data[currentIndex] == '{') {
                if (currentIndex+1 < data.length && data[currentIndex+1] == '$') {
                    break;
                } else {
                    throw new SmartScriptLexerException("Problem occurred while generating new token. Illegal symbol " + data[currentIndex]);
                }
            } else if (data[currentIndex] == '\\') {
                if (currentIndex+1 < data.length && data[currentIndex+1] == '\\') {
                    text += data[currentIndex++];
                    text += data[currentIndex++];
                } else if (currentIndex+1 < data.length && data[currentIndex+1] == '{') {
                    text += data[currentIndex++];
                    text += data[currentIndex++];
                } else {
                    throw new SmartScriptLexerException("Problem occurred while generating new token. Illegal escaping " + data[currentIndex+1] + data[currentIndex+1]);
                }
            } else {
                text += data[currentIndex++];
            }
        }
        return text;
    }

    /**
     * @return returns last generated token. If called multiple time do not generate new token.
     */
    public SmartScriptToken getToken() {
        return this.token;
    }

    /**
     * Sets state of this lexer.
     *
     * @param state state to set lexer into it.
     * @throws NullPointerException if given state is <code>null</code>.
     */
    public void setState(SmartScriptLexerState state) {
        if (state == null)
            throw new NullPointerException("State can not be null.");
        this.hasTagName = false;
        this.lexerState = state;
    }
}
