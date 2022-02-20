package Class05_链表;

import java.util.HashMap;

/**
 * @author Chu
 * @create 2021-11-24  13:54
 */
/* 面试题：
    一个特殊的单链表结点描述如下（多存在一个rand）：
        rand指针是单链表结点结构中新增的指针，rand可能指向链表中的任意一个结点，也可能指向null
        给定一个由Node结点类型组成的无环单链表的头结点head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头结点。
*
实现方式一：1） 遍历一遍，将每个结点存入map的键和值中（①，①‘） 其中①‘是当前结点的克隆结点。
          2） 在遍历map，模仿①，把①’的结点next、rand进行指向
实现方式二：1）遍历一遍，将克隆结点①’放入当前结点①与①的下一个结点中间(即①‘在①后面)，
          2）两两拿出(①、①’)，通过①的rand(因为①’的rand在①的rand之后)，对①‘的rand进行连接
* */

public class Code04_CopyListWithRandom {

    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    // 实现方式一：map
    public static Node copyListWithRand1(Node head) {
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node cur = head;
        // 第一次遍历，将每个结点存入map的键和值中（①，①‘） 其中①‘是当前结点的克隆结点。
        while (cur != null) {
            // cur老结点作为key，创建出一个与它值相同的结点，存入到value
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        // 第二次遍历，模仿①，把①’的结点next、rand进行指向
        // cur老结点，map.get(cur)新结点
        while (cur != null) {
            // 例如
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        // 返回克隆结点的头结点
        return map.get(head);
    }


    // 实现方式二：
    public static Node copyListWithRand2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        // copy node and link to every node
        // 1 -> 2
        // 1 -> 1' -> 2 -> 2'
        // 1）遍历一遍，将克隆结点①’放入当前结点①与①的下一个结点中间(即①‘在①后面)，
        while (cur != null) {
            // cur代表老结点
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node curCopy = null;
        // set copy node rand
        // 2）两两拿出(①、①’)，通过①的rand(因为①’的rand在①的rand之后)，对①‘的rand进行连接
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        // res克隆结点的头结点
        Node res = head.next;
        cur = head;
        // split
        //  进行分离操作 1 -> 1' -> 2 -> 2'
        //       变成：   1 -> 2  和 1‘ -> 2'
        while (cur != null) {
            // next的值是记录旧结点①的下一个结点①’
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }


    public static void printRandLinkedList(Node head) {
        Node cur = head;
        System.out.print("order: ");
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
        cur = head;
        System.out.print("rand:  ");
        while (cur != null) {
            System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = null;
        Node res1 = null;
        Node res2 = null;
        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

        head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);
        head.next.next.next.next.next = new Node(6);

        head.rand = head.next.next.next.next.next; // 1 -> 6
        head.next.rand = head.next.next.next.next.next; // 2 -> 6
        head.next.next.rand = head.next.next.next.next; // 3 -> 5
        head.next.next.next.rand = head.next.next; // 4 -> 3
        head.next.next.next.next.rand = null; // 5 -> null
        head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

        printRandLinkedList(head);
        res1 = copyListWithRand1(head);
        printRandLinkedList(res1);
        res2 = copyListWithRand2(head);
        printRandLinkedList(res2);
        printRandLinkedList(head);
        System.out.println("=========================");

    }
}
