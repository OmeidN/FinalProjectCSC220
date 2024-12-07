package data_structures;

class BSTNode<T extends Comparable<T>> {
    T data;
    BSTNode<T> left;
    BSTNode<T> right;

    public BSTNode(T data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinarySearchTree<T extends Comparable<T>> {
    private BSTNode<T> root;

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private BSTNode<T> insertRec(BSTNode<T> node, T data) {
        if (node == null) {
            return new BSTNode<>(data);
        }
        if (data.compareTo(node.data) < 0) {
            node.left = insertRec(node.left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.right = insertRec(node.right, data);
        }
        return node;
    }

    public boolean search(T data) {
        return searchRec(root, data);
    }

    private boolean searchRec(BSTNode<T> node, T data) {
        if (node == null) return false;
        if (data.compareTo(node.data) == 0) return true;
        if (data.compareTo(node.data) < 0) return searchRec(node.left, data);
        return searchRec(node.right, data);
    }

    public void inorderTraversal() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(BSTNode<T> node) {
        if (node != null) {
            inorderRec(node.left);
            System.out.print(node.data + " ");
            inorderRec(node.right);
        }
    }
}
