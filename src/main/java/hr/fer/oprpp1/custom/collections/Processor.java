package hr.fer.oprpp1.custom.collections;

/**
 * Model of an object capable of performing some operation on the passed object.
 */
public interface Processor {

    /**
     * Performs selected operation.
     *
     * @param value Object over which process will be executed.
     */
    void process(Object value);
}
