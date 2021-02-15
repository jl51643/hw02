package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elements.Element;
import hr.fer.oprpp1.custom.scripting.elements.ElementVariable;
import org.jetbrains.annotations.Nullable;

/**
 * Model of node representing a single for-loop construct.
 */
public class ForLoopNode extends Node{

    /**
     * Variable of this for-loop node.
     */
    private ElementVariable variable;

    /**
     * Start expression of this for-loop node.
     */
    private Element startExpression;

    /**
     * End expression of this for-loop node.
     */
    private Element endExpression;

    /**
     * Step expression of this for-loop node.
     */
    @Nullable
    private Element stepExpression;

    /**
     * Constructs new for-loop node
     *
     * @param variable variable of this for-loop node.
     * @param startExpression start expression of this for-loop node.
     * @param endExpression end expression of this for-loop node.
     * @param stepExpression step expression of this for-loop node.
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * @return returns variable of this for-loop node.
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * @return returns start expression of this for-loop node.
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * @return returns end expression of this for-loop node.
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * @return returns step expression of this for-loop node.
     */
    public Element getStepExpression() {
        return stepExpression;
    }
}
