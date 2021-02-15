package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Model of linked list-backed collection of objects
 */
public class LinkedListIndexedCollection implements List{

    /**
     * Node of linked list
     */
    private static class ListNode {

        /**
         * Reference to previous node in collection.
         */
        private ListNode previous;

        /**
         * Reference to next node in collection.
         */
        private ListNode next;

        /**
         * Value stored in node.
         */
        private Object value;

        /**
         * Creating new list node with params <code>previous</code>, <code>next</code> and <code>value</code>.
         *
         * @param previous previous node in this collection.
         * @param next next node in this collection.
         * @param value value stored in this node.
         */
        private ListNode(ListNode previous, ListNode next, Object value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }

        /**
         * Creating new list node with <code>null</code> references for
         * previous and next node and only storing given value to node.
         *
         * @param value value stored in this node.
         */
        private ListNode(Object value) {
            this(null, null, value);
        }
    }

    /**
     * Number of currently stored elements in this collection.
     */
    private int size;

    /**
     * Reference to firs node in this list.
     */
    private ListNode first;

    /**
     * Reference of last node sored in this list.
     */
    private ListNode last;

    /**
     * Counter incremented with each structural change of collection.
     */
    private long modificationCount = 0;

    /**
     * Constructing new empty LinkedListIndexedCollection
     */
    public LinkedListIndexedCollection() {
        this.size = 0;
        this.first = null;
        this.last = null;
    }

    /**
     * Constructing new LinkedListIndexedCollection from given collection.
     *
     * @param collection given collection.
     * @throws NullPointerException if given collection is null.
     */
    public LinkedListIndexedCollection(Collection collection) {
        if (collection == null)
            throw new NullPointerException("Given collection is null");
        this.addAll(collection);

    }

    /**
     * Adds given object into this collection
     *
     * @param value object to add in collection
     * @throws NullPointerException if given value is null
     */
    @Override
    public void add(Object value) {
        this.modificationCount++;
        if (value == null)
            throw new NullPointerException("Can not add null into collection");

        ListNode newNode = new ListNode(value);

        if (first == null && last == null) {
            first = newNode;
            last = newNode;
        } else if (first.equals(last)) {
            first.next = newNode;
            newNode.previous = last;
            last = newNode;

        } else {
            last.next = newNode;
            newNode.previous = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void clear() {
        this.modificationCount++;
        this.first = null;
        this.last = first;
        this.size = 0;
    }



    /**
     * Returns the number of currently stored objects in this collection.
     *
     * @return number of currently stored objects in the collection.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns true only if this collection contains given object.
     *
     * @param value object to check if it is in this collection.
     * @return returns true if this collection contains given object and false otherwise.
     */
    @Override
    public boolean contains(Object value) {

        ListNode current;
        for (current = this.first; current != null; current = current.next) {
            if (current.value.equals(value))
                return true;
        }
        return false;
    }

    /**
     * Returns true only if the collection contains given value and removes
     * one occurrence of it.
     *
     * @param value Object to remove.
     * @return returns true if the collection contains given value and false otherwise.
     */
    @Override
    public boolean remove(Object value) {
        this.modificationCount++;
        if (this.size == 0)
            return false;
        for (ListNode current = first; current != null; current = current) {
            if (current.value.equals(value)) {
                this.remove(this.indexOf(current.value));
                return true;
            }
        }
        return false;
    }

    /**
     * Allocates new array with size equals to the size of this collections,
     * fills it with collection content and returns the array.
     *
     * @return new array of objects contained in this collection.
     */
    @Override
    public Object[] toArray() {

        Object[] array = new Object[this.size];
        ListNode current;
        int i;
        for (i = 0, current = this.first; i < this.size; i++, current = current.next) {
            array[i] = current.value;
        }

        return array;
    }

    /**
     * Returns the object that is stored in linked list at position <code>index</code>.
     *
     * @param index index of object in list
     * @return Returns the object that is stored in linked list at position <code>index</code>.
     * @throws IndexOutOfBoundsException if <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    @Override
    public Object get(int index) {
        if (index < 0 || index > this.size-1)
            throw new IndexOutOfBoundsException("There is no element at position " + index + "!");

        ListNode current = this.first;
        for (int i = 0; i < index; i ++) {
           current = current.next;
        }
        return current.value;
    }

    /**
     * Inserts the given value at the given position in linked-list.
     *
     * @param value Value to insert into this linked-list.
     * @param position Position on which object will be inserted.
     * @throws IndexOutOfBoundsException if position is smaller than 0 or greater than size of collection.
     * @throws NullPointerException if given value is <code>null</code>.
     */
    @Override
    public void insert(Object value, int position) {
        this.modificationCount++;
        if (value == null)
            throw new NullPointerException("Can not insert null!");
        if (position < 0 || position > this.size)
            throw new IndexOutOfBoundsException("Can not insert element on position " + position + "!");

        if (this.size == 0) {
            this.add(value);
        } else {
           /* ListNode newNode = this.first;
            for (int i = 0; i < position; i++)
                newNode = newNode.next;
            newNode.previous.next = newNode;
            newNode.next.previous = newNode;*/
            ListNode current = this.first;
            ListNode newNode = new ListNode(value);
            for (int i = 0; i < position-1; i++) {
                current = current.next;
            }
            newNode.previous = current;
            newNode.next = current.next;
            current.next = newNode;
            newNode.next.previous = newNode;
            this.size++;
        }

    }

    /**
     * Searches the collection and returns the index of the first occurrence
     * of the given value or -1 if the value is not found
     *
     * @param value Value to search in this collection.
     * @return Position of first occurrence of the given value or -1 if the value is not found
     */
    @Override
    public int indexOf(Object value) {
        ListNode current;
        int index;
        for (current = this.first, index = 0; current.next != null; current = current.next, index++) {
            if (current.value.equals(value))
                return index;
        }

        return -1;
    }

    /**
     * Removes element at specific <code>index</code> from collection.
     *
     * @param index position of element which will be removed.
     * @throws IndexOutOfBoundsException if <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    @Override
    public void remove(int index) {
        this.modificationCount++;

        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Can not remove element from position " + index + ".");

        if (this.size == 1) {
            this.clear();
        } else {
            ListNode curretn;
            for (curretn = this.first; index > 0; curretn = curretn.next, index--) {
                continue;
            }
            curretn.previous.next = curretn.next;
            curretn.next.previous = curretn.previous;
            this.size--;
        }
    }

    /**
     * Creates new ElementsGetter.
     *
     * @return returns new ElementsGetter.
     */
    @Override
    public ElementsGetter createElementsGetter() {
        return new LinkedListIndexedCollectionElementsGetter(this);
    }

    /**
     * Model of object which returns elements from collection
     */
    private static class LinkedListIndexedCollectionElementsGetter implements ElementsGetter{

        /**
         * Reference to collection to get elements.
         */
        private LinkedListIndexedCollection collection;

        /**
         * Next list node to return.
         */
        private ListNode nextToGet;

        /**
         * Modification count in moment of creating new object of type ElementsGetter.
         */
        private long savedModificationCount;

        /**
         * Constructs new ElementsGetter.
         */
        public LinkedListIndexedCollectionElementsGetter(LinkedListIndexedCollection l) {
            this.collection = l;
            this.nextToGet = l.first;
            this.savedModificationCount = l.modificationCount;
        }

        /**
         * Returns true only if there is more elements of collection to get.
         *
         * @return returns true only if there is more elements of collection to get, false otherwise.
         * @throws ConcurrentModificationException if collection has been modified since declaration of this ElementsGetter
         */
        @Override
        public boolean hasNextElement() {
            if (this.savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since declaration of this ElementsGetter.");
            return nextToGet != null;
        }

        /**
         * Returns next object from collection.
         *
         * @return returns next element from collection.
         * @throws NoSuchElementException if all elements of collection are given.
         * @throws ConcurrentModificationException if collection has been modified since declaration of this ElementsGetter
         */
        @Override
        public Object getNextElement() {
            if (this.savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since declaration of this ElementsGetter.");
            if (hasNextElement()) {
                ListNode current = this.nextToGet;
                this.nextToGet = nextToGet.next;
                return current.value;
            } else {
                throw new NoSuchElementException("No more elements to get.");
            }
        }
    }
}
