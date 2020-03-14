package bearmaps;

import java.util.HashSet;
import java.util.NoSuchElementException;

public class MinPQ<T> implements ExtrinsicMinPQ<T> {
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentException if item is already present.
     * You may assume that item is never null. */
    private pair[] items;
    private int size;
    private HashSet<T> set;

    public MinPQ() {
        items = (pair[]) new Object[8];
        items[0] = new pair(null, Double.MIN_VALUE);
        size = 0;
    }

    /* Standing for the priority of item. */
    private class pair {
        private T value;
        private double priority;

        public pair(T value, double priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) { throw new IllegalArgumentException("The item already in the heap.");}
        if (isOverFull()) { resize(); }
        set.add(item);
        items[size += 1] = new pair(item, priority);
        swim(size);
    }

    private void swim(int position) {
        while (position > 1 && less(position, position / 2)) {
            swap(position, position / 2);
            position /= 2;
        }
    }

    private void sink(int position) {
        while (position * 2 <= size) {
            int j = 2 * position;
            if (less(j, j + 1)) j += 1;
            if (!less(position, j)) break;
            swap(position, j);
            position = j;
        }
    }

    private boolean isOverFull() {
        return (double) size / items.length > 0.75;
    }

    private void resize() {
        pair[] result = (pair[]) new Object[items.length * 2];
        System.arraycopy(items, 0, result, 0, items.length * 2);
        items = result;
    }

    private boolean less(int position1, int position2) {
        return items[position1].priority < items[position2].priority;
    }

    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return set.contains(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        if (size() == 0) { throw new NoSuchElementException(); }
        return items[1].value;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        if (size() == 0) { throw new NoSuchElementException(); }
        T result = items[1].value;
        items[1] = null;
        swap(size, 1);
        size -= 1;
        sink(1);
        return result;
    }

    /* put the last item to the top of the heap. */
    private void swap(int size1, int size2) {
        pair temp = items[size1];
        items[size1] = items[size2];
        items[size2] = temp;
    }

    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        changePriorityHelper(item, priority, 1);
    }

    private void changePriorityHelper(T item, double priority, int position) {
        if (position >= size) {
            return;
        }
        if (items[position].equals(item)) {
            items[position].priority = priority;
            return;
        }
        changePriorityHelper(item, priority, position * 2);
        changePriorityHelper(item, priority, position * 2 + 1);
    }
}
