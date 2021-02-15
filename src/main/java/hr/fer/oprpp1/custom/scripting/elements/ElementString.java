package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of string element
 */
public class ElementString extends Element{

    /**
     * Content of this string element.
     */
    private String value;

    /**
     * Constructs new string element.
     *
     * @param value content of this element string.
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * @return returns content of this string elements value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns content of this string elements value.
     *
     * @return returns content of this string elements value.
     */
    @Override
    public String asText() {
        return this.value;
    }
}
