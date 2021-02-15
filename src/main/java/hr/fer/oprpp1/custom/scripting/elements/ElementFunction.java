package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of function element
 */
public class ElementFunction extends Element{

    /**
     * Name of this function element.
     */
    private String name;

    /**
     * Constructs new function element.
     *
     * @param name name of this function element.
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * @return returns name of this function element.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns name of this function element.
     *
     * @return returns name of this function element.
     */
    @Override
    public String asText() {
        return this.name;
    }
}
