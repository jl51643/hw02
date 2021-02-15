package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of integer constant.
 */
public class ElementConstantInteger extends Element{

    /**
     * Value of this integer constant
     */
    private int value;

    /**
     * Constructs new integer constant element.
     *
     * @param value value of new integer constant element.
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * @return returns value of this integer constant.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns string representation of value of this integer constant.
     *
     * @return returns string representation of value of this integer constant.
     */
    @Override
    public String asText() {
        return Integer.toString(this.value);
    }
}
