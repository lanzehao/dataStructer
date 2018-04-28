package tree.heap;

/**
 * Interface for binary heap.</br></br>
 * Implementor should maintain desired
 * correct order because none is forced by the interface.
 *
 * @param <T>
 *     Type of the elements in the heap
 *
 */
public interface BinaryHeap<T extends Comparable<T>>{

    /**
     * Adds new element to the heap.</br></br>
     * Element have to take place in heap according to rules;</br>
     * <pre>
     *  1. Add the element to the bottom level of the heap.
     *  2. Compare the added element with its parent; if they are in the correct order, stop.
     *  3. If not, swap the element with parent and return to the previous step.
     * </pre>
     * @param element Element which would be added to the heap.
     */
    void add(T element);

    /**
     * Returns the first element to the heap.</br></br>
     * Example:
     * <pre>
     * For minHeap implementation the element return will be
     * the smallest element in the heap.
     * </pre>
     *
     * @return Top element of the heap.
     */
    T peek();

    /**
     * Returns the first element of the heap and removes it from the heap;
     * @return Top element of the heap.
     */
    T pop();

    /**
     * Remove the first match of the element.
     * Method performs rebuild of the heap on every delete.
     * @param element
     * @return
     */
    boolean delete(T element);

    /**
     * Add elements from provided array to existing BinaryHeap.
     * @param array Array with elements of same type as instantiated BinaryHeap.
     */
    void heapify(T[] array);
}
