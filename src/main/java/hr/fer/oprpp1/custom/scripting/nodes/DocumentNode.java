package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elements.Element;

/**
 * Model of node representing whole document.
 */
public class DocumentNode extends Node {

    /**
     * Returns string representation of document node.
     *
     * @return returns string representation of document node.
     */
    @Override
    public String toString() {
        return printDocument();
    }

    /**
     * Returns string representation of input document.
     *
     * @return returns string representation of input document.
     */
    private String printDocument() {
        String document = "";
        document += printChildren(this);
        return document;
    }

    /**
     * Appends string representation of all child nodes in given node's children collection.
     *
     * @param node node parent which child nodes will be appended into string.
     * @return returns string of all given node children nodes.
     */
    private String printChildren(Node node) {
        String children = "";
        int i = 0;
        while (node.numberOfChildren() > i) {
            children += printChild(node.getChild(i));
            children += printChildren(node.getChild(i));
            i++;
        }
        return children;
    }

    /**
     * Returns string representation of given node.
     *
     * @param node node to convert into string.
     * @return returns string representation of given node.
     */
    private String printChild(Node node) {
        String child = "";
        if (node instanceof TextNode) {
            child = ((TextNode) node).getText();
        } else if (node instanceof ForLoopNode) {
            child = printForLoop((ForLoopNode) node);
        } else if (node instanceof EchoNode) {
            child = printEchoNode((EchoNode) node);
        }
        return child;
    }

    /**
     * Returns string representation of echo node.
     *
     * @param node echo node
     * @return returns string representation of echo node.
     */
    private String printEchoNode(EchoNode node) {
        String echoNode = "{$=";
        for (Element element : node.getElements()) {
            echoNode += " " + element.asText();
        }
        echoNode += " $}";
        return echoNode;
    }

    /**
     * Returns string representation of for-loop node.
     *
     * @param node for-loop node.
     * @return returns string representation of for-loop node.
     */
    private String printForLoop(ForLoopNode node) {
        String forLoop = "{$FOR";
        forLoop += " " + ((ForLoopNode) node).getVariable().asText();
        forLoop += " " + ((ForLoopNode) node).getStartExpression().asText();
        forLoop += " " +((ForLoopNode) node).getEndExpression().asText();
        if (node.getStepExpression() != null) {
            forLoop += " " + ((ForLoopNode) node).getStepExpression().asText();
        }
        forLoop += " $}";
        return forLoop;
    }

    /**
     * Compares string outputs of two document node models.
     *
     * @param o document node to compare value with.
     * @return returns true if two document nodes have same string representation, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof DocumentNode) {
            return this.toString().equals(o.toString());
        }
        return false;
    }
}
