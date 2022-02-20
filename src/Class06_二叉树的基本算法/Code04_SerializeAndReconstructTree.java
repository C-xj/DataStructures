package Class06_二叉树的基本算法;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chu
 * @create 2021-11-29  16:07
 */
/*
 * 序列化：用数组/队列等存储树，加null。使用反序列化可以还原树。
 *
 * */

public class Code04_SerializeAndReconstructTree {

    /*
     * 二叉树可以通过先序、后序或按层遍历的方式序列化和反序列化
     * 以下代码全部实现了。
     * 但是，二叉树无法通过中序遍历的方式实现序列化和反序列化
     * 因为不同的两棵树，可能得到同样的中序序列，即便补了空位置也可能一样
     * 比如如下两棵树
     * 			__2
     * 		   /
     * 		  1
     * 		  和
     * 		  1__
     * 			 \
     * 			  2
     * 补足空位置的中序遍历结果都是{ null, 1, null, 2, null}
     * */

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 先序序列化
    public static Queue<String> preSerial(Node head){
        LinkedList<String> ans = new LinkedList<>();
        pres(head,ans);
        return ans;
    }

    // 序列化
    public static void pres(Node head,Queue<String> ans){
        if (head == null){
            ans.add(null);
        }else {
            ans.add(String.valueOf(head.value));
            pres(head.left,ans);
            pres(head.right,ans);
        }
    }

    public static Node buildByPreQueue(Queue<String> prelist){
        if (prelist == null || prelist.size() == 0){
            return null;
        }
        return preb(prelist);
    }

    // 反序列化
    public static Node preb(Queue<String> prelist){
        String value = prelist.poll();
        if (value == null){
            return null;
        }
        Node head = new Node(Integer.parseInt(value));
        head.left = preb(prelist);
        head.right = preb(prelist);
        return head;
    }


    // 按层序列化
    public static Queue<String> levelSerial(Node head) {
        LinkedList<String> ans = new LinkedList<>();
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(head);
            while (!queue.isEmpty()){
                head = queue.poll();
                if (head.left != null){
                    // 既序列化也加入队列
                    ans.add(String.valueOf(head.left.value));
                    queue.add(head.left);
                }else {
                    // 只序列化
                    ans.add(null);
                }
                if (head.right != null){
                    ans.add(String.valueOf(head.right.value));
                    queue.add(head.right);
                }else {
                    ans.add(null);
                }
            }
        }
        return ans;
    }


    // 题目二：直观打印一颗二叉树
    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        printInOrder(head.right, height + 1, "v", len);
        String val = to + head.value + to;
        int lenM = val.length();
        int lenL = (len - lenM) / 2;
        int lenR = len - lenM - lenL;
        val = getSpace(lenL) + val + getSpace(lenR);
        System.out.println(getSpace(height * len) + val);
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }

}
