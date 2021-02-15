package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elements.Element;

/**
 * Model of a node representing a command which generates some textual output dynamically.
 */
public class EchoNode extends Node{

    /**
     * Elements of this echo node.
     */
    private Element[] elements;

    /**
     * Constructs new echo node.
     *
     * @param elements array of elements of this echo node.
     */
    public EchoNode(Element[] elements) {
        this.elements = elements;
    }

    /**
     * @return returns array of elements of this echo node.
     */
    public Element[] getElements() {
        return elements;
    }
}
