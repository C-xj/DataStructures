package Class02_栈_队列_链表;

/**
 * @author Chu
 * @create 2021-11-14  14:55
 */
public class Code02_DeleteGivenValue {

    // 单向链表
    public static class Node{
        public int value;
        public Node next;

        public Node(int data) {
            value = data;
        }
    }

    // 删除定值结点，由于头结点也可能被删除，所以要返回一个新的头
    public static Node removeValue(Node head ,int num){
        while (head != null){
            if(head.value != num){
                break;
            }
            head = head.next;
        }
        // head来到第一个不需要删的位置
        Node pre = head;
        Node cur = head;
        while (cur != null){
            if(cur.value == num){
                // 如果当前是要被删除的结点，那pre就指向这个结点的下一个，完成删除
                pre.next = cur.next;
            }else {
                // 不是要删除的结点，则赋予cur，与其一起到下一个结点进行判断
                pre = cur;
            }
            cur = cur.next;
        }
        return head;
    }



}
