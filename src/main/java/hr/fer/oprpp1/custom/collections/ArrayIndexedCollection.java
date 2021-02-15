package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Model of resizable array-backed collection
 */
public class ArrayIndexedCollection implements List{

    /**
     * Default capacity of collection.
     */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * Counter incremented with each structural change of collection.
     */
    private long modificationCount = 0;

    /**
     * Current size of collection.
     */
    private int size;

    /**
     * Array of elements stored in collection.
     */
    private Object[] elements;

    /**
     * Constructing new ArrayIndexedCollection with capacity set to default capacity which is 16.
     * Size of new ArrayIndexedCollection is set to 0.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructing new ArrayIndexedCollection with capacity of given <code>initialCapacity</code>.
     * Size of new ArrayIndexedCollection is set to 0.
     *
     * @param initialCapacity length of new array of ArrayIndexedCollection
     * @throws IllegalArgumentException if initialCapacity is less than 1
     */
    public ArrayIndexedCollection(int initialCapacity) {
        if (initialCapacity < 1)
            throw new IllegalArgumentException("Capacity must be at least 1! It was " + initialCapacity + ".");
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    /**
     * Constructing new ArrayIndexedCollection from given collection.
     * Size of ArrayIndexedCollection is set to size of given collection.
     *
     * @param collection collection of Objects which will be added to new ArrayIndexedCollection
     * @throws NullPointerException if given reference to collection is null
     */
    public ArrayIndexedCollection(Collection collection) {
       this(collection, collection.size());
    }

    /**
     * Constructing new ArrayIndexedCollection from given collection with capacity of
     * <code>initialCapacity</code> or given collection if size of given collection is greater
     * than <code>initialCapacity</code>.
     * Size of new ArrayIndexedCollection is set to size of given collection
     *
     * @param collection collection of Objects which will be added to new ArrayIndexedCollection
     * @param initialCapacity capacity of new ArrayIndexedCollection
     * @throws NullPointerException if given reference to collection is null
     */
    public ArrayIndexedCollection(Collection collection, int initialCapacity) {
        if (initialCapacity > collection.size())
            this.elements = new Object[initialCapacity];
        else
            this.elements = new Object[collection.size()];
        this.addAll(collection);
    }

    /**
     * Returns the number of currently stored objects in this collection.
     *
     * @return number of currently stored objects in this collection
     */
    @Override
    public int size(){
        return this.size;
    }

    /**
     * Adds given object into this ArrayIndexedCollection on first empty place
     *
     * @param value object to add in collection
     * @throws NullPointerException if given value is <code>null</code>.
     */
    @Override
    public void add(Object value) {
        this.modificationCount++;
        if (value == null)
            throw new NullPointerException("Can not add null into collection");
        if (this.elements.length == this.size) {
            Object[] tmp = new Object[this.size * 2];
            for(int i = 0; i < this.elements.length; i++)
                tmp[i] = this.elements[i];
            this.elements = tmp;
        }
        this.elements[this.size++] = value;
    }

    /**
     * Removes all elements from this collection.
     */
    @Override
    public void clear() {
        this.modificationCount++;
        for (Object element : this.elements) {
            element = null;
        }
        this.size = 0;
    }

    /**
     * Returns true only if collection contains given object.
     *
     * @param value object to check if it is in this collection.
     * @return true if collection contains given object and false otherwise.
     */
    @Override
    public boolean contains(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value))
                return true;
        }
        return false;
    }

    /**
     * Allocates new array with size equals to the size of this collections,
     * fills it with collection content and
     * returns the array.
     *
     * @return new array of objects contained in this collection.
     */
    @Override
    public Object[] toArray() {
         return Arrays.copyOf(this.elements, this.size);
    }

    /**
     * Returns true only if the collection contains given value and removes
     * one occurrence of it.
     *
     * @param value Object to remove.
     * @return
     */
    @Override
    public boolean remove(Object value) {
        this.modificationCount++;
       for (int i = 0; i < this.size; i++) {
           if (this.elements[i].equals(value)) {
               this.remove(i);
               return true;
           }
       }
       return false;
    }

    /**
     * Returns the object that is stored in backing array at position
     * <code>index</code>.
     *
     * @param index index in backing array.
     * @return object on position <code>index</code> in backing array.
     * @throws IndexOutOfBoundsException if given <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    @Override
    public Object get(int index) {
        if (index >= 0 && index <= this.size - 1)
            return this.elements[index];
        throw new IndexOutOfBoundsException("Index must be between 0 and " + this.size + ". It was " + index + ".");
    }

    /**
     * Inserts the given value at the given position.
     *
     * @param value Object to be inserted into collection.
     * @param position Position on which object will be inserted.
     * @throws IndexOutOfBoundsException if given position is smaller than 0 or greater than size of this collection.
     * @throws NullPointerException if given value is null.
     */
    @Override
    public void insert(Object value, int position) {
        this.modificationCount++;
        if (value == null)
            throw new NullPointerException("Can not insert null into collection!");

        if (position > this.size || position < 0)
            throw new IndexOutOfBoundsException("You can only insert values at positions from 0 to " + this.size + ". You tried with position " + position + ".");

        if (this.size == 0) {
            this.add(value);
        } else {
            this.add(this.elements[size-1]); //duplicating last element

            for (int i = this.size-2; i > position; i--) {
                this.elements[i] = this.elements[i-1];//shifting other elements for one position
            }

            this.elements[position] = value;//overwriting duplicated element With value to insert
        }
    }

    /**
     * Searches the collection and returns the index of the first occurrence
     * of the given value or -1 if the value is not found
     *
     * @param value value to search.
     * @return index of first occurrence of the given value or -1 if the value is not found
     */
    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < this.size; i++) {
            if (this.elements[i].equals(value))
                return i;
        }

        return -1;
    }

    /**
     * Removes element at specified <code>index</code>> from collection.
     *
     * @param index Index od element to remove.
     * @throws IndexOutOfBoundsException if <code>index</code> is smaller than 0 or greater than size-1 of this collection.
     */
    @Override
    public void remove(int index) {
        this.modificationCount++;
        if (index < 0 ||index > this.size-1)
            throw new IndexOutOfBoundsException("You can only remove values at positions from 0 to " + (this.size-1) + ". You tried remove element from position " + index + ".");

        for (int i = index; i < this.size-1; i++) {
            this.elements[i] = this.elements[i+1];
        }
        this.elements[this.size-1] = null;
        size--;
    }

    /**
     * Creates new ElementsGetter.
     *
     * @return returns new ElementsGetter.
     */
    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayIndexedCollectionElementsGetter(this);
    }

    /**
     * Model of object which returns elements from collection
     */
    private static class ArrayIndexedCollectionElementsGetter implements ElementsGetter {

        /**
         * Reference to collection to get elements.
         */
        private ArrayIndexedCollection collection;

        /**
         * Number of given elements from collection.
         */
        private int given;

        /**
         * Modification count in moment of creating new object of type ElementsGetter.
         */
        private long savedModificationCount;

        /**
         * Constructs new ElementsGetter.
         */
        public ArrayIndexedCollectionElementsGetter(ArrayIndexedCollection col) {
            this.collection = col;
            this.given = 0;
            this.savedModificationCount = col.modificationCount;
        }

        /**
         * Returns true only if there is more elements of collection to get.
         *
         * @return returns true only if there is more elements of collection to get, false otherwise.
         * @throws ConcurrentModificationException if collection has been modified since declaration of this ElementsGetter
         */
        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since declaration of this ElementsGetter");

            return given < collection.size;
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
            if (savedModificationCount != collection.modificationCount)
                throw new ConcurrentModificationException("Collection has been modified since last declaration of ElementsGetter.");

            if (hasNextElement()) {
                return collection.get(given++);
            } else {
                throw new NoSuchElementException("No more elements to get.");
            }
        }
    }
}
