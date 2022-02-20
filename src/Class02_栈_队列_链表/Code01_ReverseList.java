package Class02_栈_队列_链表;

/**
 * @author Chu
 * @create 2021-11-14  14:31
 */
public class Code01_ReverseList {

    // 单向链表
    public static class Node{
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    // 双向链表
    public static class DoubleNode{
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data) {
            value = data;
        }
    }

    // 单链表反转
    public static Node reverseLinkedList(Node head){
        Node pre = null;
        Node next = null;
        while (head != null){
            next = head.next;

            head.next = pre;
            pre = head;

            head = next;
        }
        return pre;
    }

    // 双向链表反转
    public static DoubleNode reverseDoubleList(DoubleNode head){
        DoubleNode pre = null;
        DoubleNode next = null;
        while (head != null){
            next = head.next;

            head.next = pre;
            head.last = next;
            pre = head;

            head = next;
        }
        return pre;
    }


}
