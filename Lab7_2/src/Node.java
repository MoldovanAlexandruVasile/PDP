public class Node {
    RunnableThread key;
    Node left, right;

    Node(RunnableThread item) {
        key = item;
        left = right = null;
    }
}
