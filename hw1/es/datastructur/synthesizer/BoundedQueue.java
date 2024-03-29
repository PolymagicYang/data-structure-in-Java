package es.datastructur.synthesizer;

import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {

    /** return size of the buffer. */
    int capacity();

    /** return number of items currently in the buffer. */
    int fillCount();

    /** add item x to the end. */
    void enqueue(T x);

    /** delete and return item from the front. */
    T dequeue();

    /** return (but not delete) item from the front. */
    T peek();

    /** is buffer empty? */
    boolean isEmpty();

    /** is the buffer full? */
    boolean isFull();

    /** implement enhance for loop. */
    Iterator<T> iterator();
}
