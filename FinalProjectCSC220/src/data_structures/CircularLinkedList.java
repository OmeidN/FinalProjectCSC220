package data_structures;

class CircularNode<T> {
    T data;
    CircularNode<T> next;

    public CircularNode(T data) {
        this.data = data;
        this.next = null;
    }
}

public class CircularLinkedList<T> {
    private CircularNode<T> tail;

    public void add(T data) {
        CircularNode<T> newNode = new CircularNode<>(data);
        if (tail == null) {
            tail = newNode;
            tail.next = tail;
        } else {
            newNode.next = tail.next;
            tail.next = newNode;
            tail = newNode;
        }
    }

    public boolean remove(T data) {
        if (tail == null) return false;
        CircularNode<T> current = tail.next;
        CircularNode<T> prev = tail;

        do {
            if (current.data.equals(data)) {
                if (current == tail && current.next == tail) {
                    tail = null;
                } else {
                    prev.next = current.next;
                    if (current == tail) {
                        tail = prev;
                    }
                }
                return true;
            }
            prev = current;
            current = current.next;
        } while (current != tail.next);

        return false;
    }

    public void display() {
        if (tail == null) {
            System.out.println("List is empty");
            return;
        }
        CircularNode<T> current = tail.next;
        do {
            System.out.print(current.data + " -> ");
            current = current.next;
        } while (current != tail.next);
        System.out.println("(back to start)");
    }

    public boolean isEmpty() {
        return tail == null;
    }
}
