package data_structures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class BinaryHeap<T> {
    private ArrayList<T> heap; // Internal storage for the heap
    private Comparator<T> comparator; // Comparator to define min-heap or max-heap behavior

    public BinaryHeap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    // Adds an element to the heap
    public void add(T element) {
        heap.add(element); // Add element at the end
        siftUp(heap.size() - 1); // Restore heap property by sifting up
    }

    // Removes and returns the root element of the heap
    public T remove() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        T root = heap.get(0);
        T lastElement = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            siftDown(0); // Restore heap property by sifting down
        }
        return root;
    }

    // Returns the root element without removing it
    public T peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap.get(0);
    }

    // Returns the size of the heap
    public int size() {
        return heap.size();
    }

    // Checks if the heap is empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    // Helper method to restore heap property by sifting up
    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) {
                break; // Heap property is satisfied
            }
            swap(index, parentIndex);
            index = parentIndex; // Move up to the parent
        }
    }

    // Helper method to restore heap property by sifting down
    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            // Compare with left child
            if (leftChild < size && comparator.compare(heap.get(leftChild), heap.get(smallest)) < 0) {
                smallest = leftChild;
            }

            // Compare with right child
            if (rightChild < size && comparator.compare(heap.get(rightChild), heap.get(smallest)) < 0) {
                smallest = rightChild;
            }

            // If no swaps are needed, stop
            if (smallest == index) {
                break;
            }

            swap(index, smallest);
            index = smallest; // Move down to the smallest child
        }
    }

    // Helper method to swap two elements in the heap
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    @Override
    public String toString() {
        return "BinaryHeap{" + "heap=" + heap + '}';
    }
}
