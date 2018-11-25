import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    Node root;

    BinaryTree() {
        root = null;
    }

    private Node addRecursive(Node current, RunnableThread value) {
        if (current == null) {
            return new Node(value);
        }
        if (current.left == null) {
            current.left = addRecursive(current.left, value);
        } else if (current.right == null) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    void add(RunnableThread value) {
        root = addRecursive(root, value);
    }

    void traverseLevelOrder() throws InterruptedException {
        if (root == null) {
            return;
        }
        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);
        Integer remainder = 0;
        while (!nodes.isEmpty()) {
            Node node = nodes.remove();
            node.key.setRemainder(remainder);
            node.key.start();
            remainder = node.key.getRemainder();
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right!= null) {
                nodes.add(node.right);
            }
        }
    }
}