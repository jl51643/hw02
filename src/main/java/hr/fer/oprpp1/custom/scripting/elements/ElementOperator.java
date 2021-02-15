package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of operator element.
 */
public class ElementOperator extends Element{

    /**
     * Symbol of this operator element.
     */
    private String symbol;

    /**
     * Constructs new operator element.
     *
     * @param symbol symbol of this operator.
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return returns symbol of this operator element.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns symbol of this operator element.
     *
     * @return returns symbol of this operator element.
     */
    @Override
    public String asText() {
        return this.symbol;
    }
}
