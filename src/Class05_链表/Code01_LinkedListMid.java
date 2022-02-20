package Class05_链表;


/**
 * @author Chu
 * @create 2021-11-23  16:12
 */
// 快慢指针
    /*
    * 1）输入链表头结点，奇数长度返回中点，偶数长度返回上中点
    * 1）输入链表头结点，奇数长度返回中点，偶数长度返回下中点
    * 1）输入链表头结点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    * 1）输入链表头结点，奇数长度返回中点前一个，偶数长度返回下中点前一个
    * */
public class Code01_LinkedListMid {

    public static class Node{
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    // 偶数个结点：返回上中结点
    public static Node midOrUpMidNode(Node head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }
        // 链表有三个点或以上
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 偶数个结点：返回上中结点
    public static Node midOrDownMidNode(Node head){
        if (head == null || head.next == null ){
            return null;
        }
        if (head.next.next == null){
            return head;
        }

        // 链表有两个点或以上
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

}
