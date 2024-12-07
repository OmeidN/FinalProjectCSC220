package data_structures;

class DoublyNode<T> {
    T data;
    DoublyNode<T> prev;
    DoublyNode<T> next;

    public DoublyNode(T data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}

public class DoublyLinkedList<T> {
    private DoublyNode<T> head;
    private DoublyNode<T> tail;

    public void add(T data) {
        DoublyNode<T> newNode = new DoublyNode<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public boolean remove(T data) {
        if (head == null) return false;
        DoublyNode<T> current = head;
        while (current != null && !current.data.equals(data)) {
            current = current.next;
        }
        if (current == null) return false;

        if (current == head) {
            head = head.next;
            if (head != null) head.prev = null;
        } else if (current == tail) {
            tail = tail.prev;
            if (tail != null) tail.next = null;
        } else {
            current.prev.next = current.next;
            current.next.prev = current.prev;
        }
        return true;
    }

    public void displayForward() {
        DoublyNode<T> current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    public void displayBackward() {
        DoublyNode<T> current = tail;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.prev;
        }
        System.out.println("null");
    }

    public boolean isEmpty() {
        return head == null;
    }
}
