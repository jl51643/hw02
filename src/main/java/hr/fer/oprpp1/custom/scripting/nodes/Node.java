package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Model of general graph node
 */
public abstract class Node {

    /**
     * Collection of this nodes' children.
     */
    ArrayIndexedCollection childrenCollection;

    /**
     * Appends given child into collection of this nodes' children.
     *
     * @param child
     * @throws NullPointerException if given child is null.
     */
    public void addChildNode(Node child) {
        if (this.childrenCollection == null)
            this.childrenCollection = new ArrayIndexedCollection();
        this.childrenCollection.add(child);
    }

    /**
     * Returns number of this nodes' children.
     *
     * @return returns number of this nodes' children.
     * @throws NullPointerException if this node do not have any children.
     */
    public int numberOfChildren() {
        if (this.childrenCollection == null)
            return 0;
        return this.childrenCollection.size();
    }

    /**
     * Returns nodes' child on position <code>index</code>.
     *
     * @param index position of child to return
     * @return returns nodes' child on position <code>index</code>.
     * @throws NullPointerException if this node do not have any children.
     */
    public Node getChild(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException("Index can not be negative number.");
        if (index >= this.childrenCollection.size())
            throw  new IndexOutOfBoundsException("Index must be from rande 0 to " + this.childrenCollection.size() + ". It was " + index + ".");

        return (Node) this.childrenCollection.get(index);
    }
}
