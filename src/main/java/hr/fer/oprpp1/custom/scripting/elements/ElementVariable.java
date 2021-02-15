package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of element variable.
 */
public class ElementVariable extends Element{

    /**
     * Name of variable.
     */
    private String name;

    /**
     * Constructs new element variable.
     *
     * @param name name of new element variable.
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Returns name of this variable.
     *
     * @return returns name of this variable.
     */
    @Override
    public String asText() {
        return this.name;
    }

    /**
     * @return returns this variable name.
     */
    public String getName() {
        return this.name;
    }
}
