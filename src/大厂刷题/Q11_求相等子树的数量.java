package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-06  15:46
 */
/*
* 如果一个结点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
*   给定一颗二叉树的头结点head，返回head整棵树上有多少棵相等子树
* */
public class Q11_求相等子树的数量 {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    // 实现方式一：O(N * logN)
    public static int sameNumber1(Node head){
        if (head == null){
            return 0;
        }
        return sameNumber1(head.left) + sameNumber1(head.right) + (same(head.left,head.right) ? 1 : 0);
    }

    // 判断两个子树是否是相等子树
    public static boolean same(Node h1,Node h2){
        if (h1 == null ^ h2 == null){   // 比较遇到，一个为空，另一个不为空情况(步调不一致)，则不会相等
            return false;
        }
        if (h1 == null && h2 == null){  // 都为空，表示(相等子树)，返回true
            return true;
        }
        // 都还有值，比较当前值是否相等，然后继续比较它们，左子树和右子树的值。
        return h1.value == h2.value && same(h1.left,h2.left) && same(h1.right,h2.right);
    }


    // 实现方式二：O(N)
    public static int sameNumber2(Node head){
        String algorithm = "SHA-256";
        Hash hash = new Hash(algorithm);
        return process(head,hash).ans;
    }

    public static class Info{
        public int ans;     // 相同子树数量
        public String str;  // Hash字符串是啥

        public Info(int ans, String str) {
            this.ans = ans;
            this.str = str;
        }
    }

    public static Info process(Node head,Hash hash){
        if (head == null){
            return new Info(0,hash.hashCode("#,"));
        }
        Info l = process(head.left,hash);
        Info r = process(head.right,hash);
        int ans = (l.str.equals(r.str) ? 1 : 0) + l.ans + r.ans;
        String str = hash.hashCode(String.valueOf(head.value) + "," + l.str + r.str);
        return new Info(ans,str);
    }



}
