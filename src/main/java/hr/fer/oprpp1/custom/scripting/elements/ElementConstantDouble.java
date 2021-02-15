package hr.fer.oprpp1.custom.scripting.elements;

/**
 * Model of double constant element.
 */
public class ElementConstantDouble extends Element{

    /**
     * Value of this double constant element.
     */
    private double value;

    /**
     * Constructs new double constant element.
     *
     * @param value value of new double constant element.
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * @return returns value of this double constant element.
     */
    public double getValue() {
        return value;
    }

    /**
     * Returns String representation of value of this double constant element.
     *
     * @return returns String representation of value of this double constant element.
     */
    @Override
    public String asText() {
        return Double.toString(this.value);
    }
}
