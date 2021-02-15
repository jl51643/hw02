package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Model of node representing a piece of textual data.
 */
public class TextNode extends Node{

    /**
     * Text stored in this node.
     */
    private String text;

    /**
     * Constructs new text node.
     *
     * @param text text stored in this node.
     */
    public TextNode(String text) {
        this.text = text;
    }

    /**
     * @return returns text stored in this node.
     */
    public String getText() {
        return text;
    }
}
