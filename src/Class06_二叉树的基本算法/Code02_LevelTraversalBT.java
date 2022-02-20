package Class06_二叉树的基本算法;

import java.util.LinkedList;

/**
 * @author Chu
 * @create 2021-11-25  20:41
 */
// ①：层序遍历，使用队列进行辅助
// ②：可以通过设置flag变量的方式，来发现某一层的结束
public class Code02_LevelTraversalBT {

    public static class Node {
        public int value;
        public  Node left;
        public  Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 层序遍历，使用队列进行辅助
    public static void level(Node head){
        if (head == null){
            return;
        }
        LinkedList<Node> queue = new LinkedList<>();
        queue.add(head);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            if (cur.left != null){
                queue.add(cur.left);
            }
            if (cur.right != null){
                queue.add(cur.right);
            }
        }
    }

}
