package Class07_二叉树的递归套路;

/**
 * @author Chu
 * @create 2021-11-30  15:42
 */
/* 一个树是中序遍历，
    1）找当前结点的后继结点
        情况① 当前结点有右树，则其后继就是其右树中递归到的最左边的结点
        情况② 在当前结点是其父结点左孩子时才是其后继结点， 来到父亲结点的位置，再次判断寻找
    2）找当前结点的前驱结点
        情况① 当前结点有左孩子，则其前驱就是其左孩子
        情况② 如果当前结点是其父结点的右孩子，则其前驱就是其父结点，否则上移到此父结点，然后再次判断
    */
public class Code01_SuccessorNode {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node parent; // 当前结点的父结点

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node getSuccessorNode(Node node) {
        if (node == null) {
            return node;
        }
        // 当前结点有右树，则其后继就是其右树中递归到的最左边的结点
        if (node.right != null) {
            return getLeftMost(node.right);
        } else {
            // 没有右树
            Node parent = node.parent;
            while (parent != null && parent.right == node) { // 在当前结点是其父结点左孩子时才是其后继结点
                // 来到父亲结点的位置，再次判断寻找
                node = parent;
                parent = node.parent;
            }
            return parent;
        }
    }

    // 找到当前结点的最左边的结点
    public static Node getLeftMost(Node node) {
        if (node == null) {
            return node;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

}
