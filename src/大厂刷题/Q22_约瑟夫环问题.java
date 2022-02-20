package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-10  16:51
 */
/*
* 给定一个链表头结点head，和一个正数m，从头开始，
*   每次数到m就杀死当前结点，然后被杀结点的下一个结点从1开始重新数，
*       周而复始直到只剩一个结点，返回最后的结点。
*
*   报数和编号的关系
*       1       1
*      ...     ...
*       i       i
*      i+1      1
*      ...     ...
*       2i      i
*
*             y轴     x轴
*       函数： 编号 = (报数 - 1) % i + 1
*
*   最后剩下的一个结点，其编号为1，设计一个函数f，可以计算上一轮的编号
*       根据此函数逐步推出，最初的编号，进行返回即可。
*
*           i为当前链表长度
*           s为当前杀死的编号
*
*       f函数： 前编号 = (后编号 - 1 + s) % i + 1
*
*       s = (m - 1) % i + 1     带入
*
*       前编号 = (后编号 + (m - 1) % i) % i + 1
*
*       优化： 前编号 = (后编号 + m - 1)  % i + 1
* */
public class Q22_约瑟夫环问题 {


    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node josephusKill1(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node last = head;
        while (last.next != head) {
            last = last.next;
        }
        int count = 0;
        while (head != last) {
            if (++count == m) {
                last.next = head.next;
                count = 0;
            } else {
                last = last.next;
            }
            head = last.next;
        }
        return head;
    }

    public static Node josephusKill2(Node head, int m) {
        if (head == null || head.next == head || m < 1) {
            return head;
        }
        Node cur = head.next;
        int size = 1; // tmp -> list size
        while (cur != head) {
            size++;
            cur = cur.next;
        }
        int live = getLive(size, m); // tmp -> service node position
        while (--live != 0) {
            head = head.next;
        }
        head.next = head;
        return head;
    }

    // 现在一共有i个节点，数到m就杀死节点，最终会活下来的节点，请返回它在有i个节点时候的编号
    // 旧
    // getLive(N, m)    递归
    public static int getLive(int i, int m) {
        if (i == 1) {
            return 1;
        }
        // getLive(i - 1, m)   长度为i-1时候，活下来的编号
        return (getLive(i - 1, m) + m - 1) % i + 1;
    }

    // 迭代
    public int lastRemaining2(int n,int m){
        int ans = 1;
        int r = 1;
        while (r <= n){
            ans = (ans + m - 1) % (r++) + 1;
        }
        return ans - 1;
    }

    public static void printCircularList(Node head) {
        if (head == null) {
            return;
        }
        System.out.print("Circular List: " + head.value + " ");
        Node cur = head.next;
        while (cur != head) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println("-> " + head.value);
    }

}
