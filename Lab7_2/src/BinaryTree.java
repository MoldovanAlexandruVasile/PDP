import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {
    Node root;

    BinaryTree() {
        root = null;
    }

    private Node addRecursive(Node current, RunnableThread thread) {
        if (current == null) {
            return new Node(thread);
        }
        current.left = addRecursive(current.left, thread);
        return current;
    }

    void add(RunnableThread value) {
        root = addRecursive(root, value);
    }

    Integer traverseLevelOrder() throws InterruptedException {
        if (root == null) {
            return 0;
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
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
        if (remainder != 0)
            return 1;
        return 0;
    }
}