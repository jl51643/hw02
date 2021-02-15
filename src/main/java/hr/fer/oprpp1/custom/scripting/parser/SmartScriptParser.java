package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elements.*;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerException;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.oprpp1.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.oprpp1.custom.scripting.nodes.*;

import java.util.Arrays;

/**
 * Model of syntax parser
 */
public class SmartScriptParser {

    /**
     * Root node of syntax tree.
     */
    private DocumentNode documentNode;

    /**
     * Lexical analyzer,
     * tokenizes input document.
     */
    private SmartScriptLexer lexer;

    /**
     * Helper stack for building syntax tree.
     */
    private ObjectStack stack;

    /**
     * Constructs new syntax parser
     *
     * @param documentBody input document to parse.
     */
    public SmartScriptParser(String documentBody) {
        this.stack = new ObjectStack();
        this.lexer = new SmartScriptLexer(documentBody);
        this.documentNode = new DocumentNode();
        try {
            parse();
        } catch (SmartScriptLexerException e) {
            throw new SmartScriptParserException(e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Parses given document into document object model.
     */
    private void parse() {
        this.stack.push(this.documentNode);
        while (!lexer.nextToken().getType().equals(SmartScriptTokenType.EOF)) {
           processToken();
        }
    }

    /**
     * Processes current token.
     * At this point only possible tokens are text token or tag token
     *
     * @throws SmartScriptParserException if problem occurred while processing token.
     */
    private void processToken() {
        switch (lexer.getToken().getType()) {
            case TEXT -> {
                processTextNode();
            }
            case TAG -> {
                processTagNode();
            }
            default -> {
                throw new SmartScriptParserException("Problem occurred while processing new token. Invalid token type \"" + lexer.getToken().getType() + "\".");
            }
        }

    }

    /**
     * Processes tag. Sets lexer into TAG mode of work,
     * determines tag name and is name valid.
     * If tag name is valid performs certain actions
     * connected with that tag and adds that tag on
     * document nodes tree hierarchy.
     *
     * @throws SmartScriptParserException if problem occurred while processing node.
     */
    private void processTagNode() {
        lexer.setState(SmartScriptLexerState.TAG);
        if (!(lexer.nextToken().getType().equals(SmartScriptTokenType.TAG_NAME)/* && lexer.getToken().getValue().equals("start")*/))
            throw new SmartScriptParserException("Problem occurred while generating next node");
        validateTagName();

    }

    /**
     * Determines name of tag and if that name is valid.
     *
     * @throws SmartScriptParserException if problem occurred while validating tag name
     */
    private void validateTagName() {
        switch (lexer.getToken().getValue().toString().toUpperCase()) {
            case "FOR" -> processForLoopNode();
            case "=" -> processEchoNode();
            case "END" -> processEndNode();
            default -> {
                throw new SmartScriptParserException("Problem occurred while generating new node. Invalid tag name \"" + lexer.getToken().getValue() + "\".");
            }
        }

    }

    /**
     * Processes END tag and returns lexer into BASIC mode of work.
     */
    private void processEndNode() {
        stack.pop();
        if (stack.size() == 0)
            throw new SmartScriptParserException("Problem occurred while generating new node. More tags are closed than opened.");
        closeEndNode();
    }

    /**
     * Makes sure that ENG tags are closed and lexer is back to BASIC mode of work.
     */
    private void closeEndNode() {
        closeNode();
    }

    /**
     * Makes sure that tags are closed and lexer is back to BASIC mode of work.
     */
    private void closeNode() {
        if (!lexer.nextToken().getType().equals(SmartScriptTokenType.TAG))
            throw new SmartScriptParserException("Problem occurred while generating new node. Expected TAG token but got " + lexer.getToken().getType());
        if (!lexer.getToken().getValue().toString().equals("end"))
            throw new SmartScriptParserException("Problem occurred while generating new node. Expected TAG token with value \"end\" but got " + lexer.getToken().getType() + " with value " + lexer.getToken().getValue());
        lexer.setState(SmartScriptLexerState.BASIC);
    }

    /**
     * Initializes echo node and adds it's elements
     * into internal array of elements
     * and adds this echo node in it's parent collection of children.
     */
    private void processEchoNode() {
        EchoNode echoNode = initializeEchoNode();
        Node nodeOnTopOfStack = (Node) stack.peek();
        nodeOnTopOfStack.addChildNode(echoNode);
    }

    /**
     * Creates new echo node with all it's elements.
     *
     * @return returns new echo node element.
     */
    private EchoNode initializeEchoNode() {
        final int DEFAULT_ELEMENTS_CAPACITY = 5;
        int i = 0;
        Element[] elements = new Element[DEFAULT_ELEMENTS_CAPACITY];
        while (!(lexer.nextToken().getType().equals(SmartScriptTokenType.TAG)
                && lexer.getToken().getValue().toString().equals("end"))) {
            if (i == elements.length)
                elements = reallocateElements(elements);
            elements[i++] = processEchoNodeTokens();
        }
        lexer.setState(SmartScriptLexerState.BASIC);
        elements = trimElements(elements);
        return new EchoNode(elements);
    }

    /**
     * Returns new array without null elements from end of given array.
     *
     * @param elements array of elements to remove null elements.
     * @return returns new array without null elements from end of given array.
     */
    private Element[] trimElements(Element[] elements) {
        int i = 0;
        for (Element element : elements) {
            if (element == null)
                break;
            i++;
        }
        Element[] newElements = new Element[i];
        return newElements = Arrays.copyOf(elements, newElements.length);
    }

    /**
     * Turns tokens of each type into elements of that type.
     *
     * @return returns new element of type which was last generated token type.
     * @throws SmartScriptParserException if problem occurred while generating new element
     */
    private Element processEchoNodeTokens() {
        switch (lexer.getToken()/*nextToken()*/.getType()) {
            case TEXT -> {
                return new ElementString(lexer.getToken().getValue().toString());
            }
            case VARIABLE -> {
                return new ElementVariable(lexer.getToken().getValue().toString());
            }
            case DOUBLE -> {
                return new ElementConstantDouble(Double.parseDouble(lexer.getToken().getValue().toString()));
            }
            case INTEGER -> {
                return new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString()));
            }
            case FUNCTION -> {
                return new ElementFunction(lexer.getToken().getValue().toString());
            }
            case OPERATOR -> {
                return new ElementOperator(lexer.getToken().getValue().toString());
            }
            default -> {
                throw new SmartScriptParserException("Problem occurred while generating new echo node element");
            }
        }
    }

    /**
     * Reallocates array of elements
     * if there was not enough initial capacity
     * for all echo node elements.
     *
     * @param elements current array of echo node elements.
     * @return new array doubled in capacity with copy of all previous elements.
     */
    private Element[] reallocateElements(Element[] elements) {
        Element[] newElements = new Element[elements.length + elements.length];
        return newElements = Arrays.copyOf(elements, newElements.length);
    }

    /**
     * Processes for-loop node.
     * Initializes for-loop node elements,
     * creates new for-loop node,
     * adds for-loop node into it's parent collection of children
     * and processes all other tokens as this for-loop node's children
     * as long as there is not tag to close for-loop.
     */
    private void processForLoopNode() {
        ForLoopNode forLoopNode = initializeForLoopNode();
        Node nodeOnTopOfStack = (Node) stack.peek();
        nodeOnTopOfStack.addChildNode(forLoopNode);
        if (forLoopNode.getStepExpression() != null)
            closeForLoopTag();
        else
            lexer.setState(SmartScriptLexerState.BASIC);
        stack.push(forLoopNode);
        lexer.nextToken();
        processToken();
    }

    /**
     * Checks if for-loop is initialized correctly and returns lexer into BASIC mode
     * @throws SmartScriptParserException if problem occurred while closing for loop tag.
     */
    private void closeForLoopTag() {
        closeNode();
    }

    /**
     * Determines elements of for-loop tag and initializes for-loop node.
     *
     * @return returns new initialized for-loop node
     */
    private ForLoopNode initializeForLoopNode() {
        ElementVariable variable = getForLoopVariable();
        Element startExpression = getForLoopStartExpression();
        Element endExpression = getForLoopEndExpression();
        Element stepExpression = getStepExpression();

        return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
    }

    /**
     * @return returns new element of type that is type of token in place for for-loop step expression.
     * @throws SmartScriptParserException if problem occurred while processing for-loop arguments
     */
    private Element getStepExpression() {
        try {
            return getForLoopElement();
        } catch (SmartScriptParserException e) {
            return null;
        }

    }

    /**
     * @return returns new element of type that is type of token in place for for-loop end expression.
     * @throws SmartScriptParserException if problem occurred while processing for-loop arguments
     */
    private Element getForLoopEndExpression() {
        return getForLoopElement();
    }

    /**
     * @return returns new element of type that is type of token in place for for-loop start expression.
     * @throws SmartScriptParserException if problem occurred while processing for-loop arguments
     */
    private Element getForLoopStartExpression() {
        return getForLoopElement();
    }

    /**
     * Checks if in for-loop tag are valid types of elements.
     * Allowed types of elements at this point are
     * variable, string, integer constant and double constant
     *
     * @return returns new element of type that is type of token.
     * @throws SmartScriptParserException if problem occurred while processing for-loop arguments
     */
    private Element getForLoopElement() {
        switch (lexer.nextToken().getType()) {
            case VARIABLE -> {
                return new ElementVariable(lexer.getToken().getValue().toString());
            }
            case TEXT -> {
                return new ElementString(lexer.getToken().getValue().toString());
            }
            case INTEGER -> {
                return new ElementConstantInteger(Integer.parseInt(lexer.getToken().getValue().toString()));
            }
            case DOUBLE -> {
                return new ElementConstantDouble(Double.parseDouble(lexer.getToken().getValue().toString()));
            }
            default -> {
                throw new SmartScriptParserException("Problem occurred while generating new node. Invalid token type. Token type " + lexer.getToken().getType() + " not allowed at this place." );
            }
        }
    }

    /**
     * Checks if given element on place for for-loop variable is valid variable name
     * and returns new variable element if given name is valid.
     *
     * @return returns new variable element if given name is valid.
     */
    private ElementVariable getForLoopVariable() {
        if (!lexer.nextToken().getType().equals(SmartScriptTokenType.VARIABLE)) {
            throw new SmartScriptParserException("Problem occurred while generating new node. Expected Token type VARIABLE but got " + lexer.getToken().getType() + ".");
        } else {
            return new ElementVariable(lexer.getToken().getValue().toString());
        }
    }

    /**
     * Processes text node.
     * Creates new text node from given text token
     * and adds it into it's parent collection of children.
     */
    private void processTextNode() {
        TextNode textNode = new TextNode(lexer.getToken().getValue().toString());
        Node nodeOnTopOfStack = (Node) stack.peek();
        nodeOnTopOfStack.addChildNode(textNode);
    }

    /**
     * @return returns this document node.
     */
    public DocumentNode getDocumentNode() {
        return this.documentNode;
    }
}
