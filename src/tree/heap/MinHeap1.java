package tree.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MinHeap is an implementation of the BinaryHeap interface. It stores elements
 * in array of type T. </br></br> Correct order of the elements in the MinHeap
 * is such that the parent should be lesser or equal to its children.
 *
 * <pre>
 * Solution 1:
 *      Implemented with array.
 * </pre>
 *
 * @param <T>
 *     Type of the elements with MinHeap would hold.
 *
 */
public class MinHeap1<T extends Comparable<T>> implements BinaryHeap<T>{
    private static final int INIT_CAPACITY = 16;
    private T[] array;
    private int size;

    public MinHeap1(){
        array = (T[]) new Comparable[INIT_CAPACITY];
        size = 0;
    }


    public MinHeap1(T[] array){
        this();
        this.heapify(array);
    }

    /**
     * Add method runs in <b>O(log n)</b> for the worst case and <b>O(1)</b> on average.
     * @param element Element which would be added to the heap.
     */
    @Override
    public void add(T element) {
        /**
         * Check if array is too small for additional element. If it is,
         * array is doubled in size through call to instance method resize.
         */
        if(size >= array.length){
            array = this.resize();
        }

        int lastIndex = size++;
        array[lastIndex] = element;
        this.upHeap();
    }

    /**
     * Peek in this implement runs in <b>O(1)</b>.</br>
     * It calls convenience method peek_min and retrieves smallest element from the heap.
     * @return
     */
    @Override
    public T peek() {
        return peek_min();
    }

    public T peek_min(){
        if(size < 1){
            return null;
        }
        return array[0];
    }

    /**
     * Pop in this implementation runs in <b>O(log n)</b>.</br>
     * Run time heavily depends on the necessary swaps in downHeap method.
     * This method calls convenience method pop_min and retrieves smallest element
     * from the heap.</br>
     * The element is removed from the heap afterwards.
     * @return
     */
    @Override
    public T pop() {
        return pop_min();
    }

    public T pop_min(){
        if(size < 1){
            return null;
        }
        T element = array[0];
        array[0] = array[size - 1];
        array[size - 1] = null;
        size--;
        downHeap(0);
        return element;
    }

    /**
     * The delete method takes <b>O(log n)</b> for the element to find.</br>
     * It removes first element that matches
     * @param element
     * @return
     */
    @Override
    public boolean delete(T element) {
        int elementIndex = findElementIndex(element);
        if(elementIndex < 0){
            return false;
        }
        for(int index = elementIndex; index < size; index++){
            array[index] = array[index + 1];
        }
        array[size] = null;
        size--;
        this.reheapify();
        return true;
    }

    @Override
    public void heapify(T[] array) {
        for(T t : array){
            if(t == null){
                continue;
            }
            add(t);
        }

    }

    private void reheapify(){
        int size = this.size;
        this.size = 0;
        for(int i = 0; i < size; i++){
            add(array[i]);
        }
    }

    private int getParentIndex(int c){
        return (c - 1) / 2;
    }

    private boolean hasParent(int c){
        return c > 0;
    }

    private int getLeftChildIndex(int p){
        return p * 2 + 1;
    }

    private int getRightChildIndex(int p){
        return p * 2 + 2;
    }

    private boolean hasLeftChild(int p){
        int leftChildIndex = getLeftChildIndex(p);
        return leftChildIndex < size && array[leftChildIndex] != null;
    }

    private boolean hasRightChild(int p){
        int rightChildIndex = getRightChildIndex(p);
        return rightChildIndex < size && array[rightChildIndex] != null;
    }

    private int findElementIndex(T element){
        for(int i = 0; i < size; i++){
            if(array[i].equals(element)){
                return i;
            }
        }
        return -1;
    }

    private T[] resize() {
        return Arrays.copyOf(array, array.length * 2);
    }

    private void swap(int firstIndex, int secondIndex){
        T temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * Up heap method. Method is used after the insertion of the element.</br>
     * It goes from last element in the heap and puts element in correct place
     * by comparing it with parents, and doing the swap if it's necessary.
     */
    private void upHeap(){
        int elementIndex = size - 1;
        while(hasParent(elementIndex) && array[elementIndex].compareTo(array[getParentIndex(elementIndex)]) < 0){
            this.swap(elementIndex, getParentIndex(elementIndex));
            elementIndex = getParentIndex(elementIndex);
        }
    }

    /**
     * Down heap method. Method is used after pop method.</br>
     * Pop removes top element of the heap and put the last one to the top.</br>
     * After that downHeap should compare top element with its children and
     * swap them if it's necessary to remain correct order.
     *
     * @param index
     *            Index of the element.
     *
     */
    private void downHeap(int index){
        int smallest = index;

        //Check if left child is smaller than parent.
        if(hasLeftChild(index) && array[getLeftChildIndex(index)].compareTo(array[index]) < 0){
            smallest = getLeftChildIndex(index);
        }

        //Check if right child is smaller than parent.
        if(hasRightChild(index) && array[getRightChildIndex(index)].compareTo(array[index]) < 0){
            smallest = getRightChildIndex(index);
        }

        /**
         * Swap smallest child with parent and proceeds recursively with
         * downHeap using index of the smallest item.
         */
        if(smallest != index){
            swap(index, smallest);
            downHeap(smallest);
        }

    }

    private void goThroughByLevel(){
        int depth = getDepth();
        List<List<Integer>> levels = new ArrayList<>();
        levels.add(new ArrayList<Integer>(){});
        levels.get(0).add(0);
        for(int d = 1; d < depth + 1; d++){
            List<Integer> pre = levels.get(d-1);
            List<Integer> curr = new ArrayList<>();
            for (int i : pre) {
                if(hasLeftChild(i)){
                    curr.add(getLeftChildIndex(i));
                }
                if(hasRightChild(i)){
                    curr.add(getRightChildIndex(i));
                }
            }
            levels.add(curr);
        }
        for (List<Integer> level : levels) {
            for (int i : level) {
                System.out.print(array[i] + " ");
            }
            System.out.print("\n");
        }
    }

    private int getDepth(){
        if(size == 0){
            return 0;
        }
        int n = 1, depth = 1;
        while(n < size){
            n += n * 2;
            depth++;
        }
        return depth;
    }


    public static void main(String[] args) {
        int[] arr = {1,5,69,99,884,15,66,4,7,665,45,8823,467,87,45,994,5,7,6};
//        int[] arr = {1,5,69,99};

        MinHeap1 minHeap = new MinHeap1();
        for(int num : arr){
            minHeap.add(num);

        }
        minHeap.goThroughByLevel();
    }

}
