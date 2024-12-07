package data_structures;

import java.util.Arrays;

public class Array<T> {
    private T[] data; // Internal array to store elements
    private int size; // Current number of elements in the array

    @SuppressWarnings("unchecked")
    public Array(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }
        data = (T[]) new Object[capacity];
        size = 0;
    }

    // Adds an element to the end of the array
    public void add(T element) {
        ensureCapacity();
        data[size++] = element;
    }

    // Inserts an element at a specific index
    public void insert(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        ensureCapacity();
        System.arraycopy(data, index, data, index + 1, size - index); // Shift elements to the right
        data[index] = element;
        size++;
    }

    // Removes an element at a specific index
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        T removedElement = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1); // Shift elements to the left
        data[--size] = null; // Clear the last element
        return removedElement;
    }

    // Retrieves an element at a specific index
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        return data[index];
    }

    // Updates an element at a specific index
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds.");
        }
        data[index] = element;
    }

    // Returns the current number of elements
    public int size() {
        return size;
    }

    // Checks if the array is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Ensures there is enough capacity to add more elements
    private void ensureCapacity() {
        if (size == data.length) {
            resize(data.length * 2);
        }
    }

    // Resizes the internal array
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] newData = (T[]) new Object[newCapacity];
        System.arraycopy(data, 0, newData, 0, size);
        data = newData;
    }

    @Override
    public String toString() {
        return "Array{" +
                "data=" + Arrays.toString(Arrays.copyOf(data, size)) +
                ", size=" + size +
                '}';
    }
}
