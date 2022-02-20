package Class05_链表;

/**
 * @author Chu
 * @create 2021-11-24  15:22
 */
/*
* 面试题1(下一题实现的前提)：
    判断链表是否有环问题，找到环的第一个结点：

实现方式一：1）快慢指针，当slow == fast时则说明存在环(slow每次走一步，fast每次走两步，初始slow = head.next fast = head.next.next)，
          2）这时将fast退回头结点head，并改为每次走一步，当slow == fast时就是环的第一个结点
实现方式二：HashSet，遍历所有结点，第一次出先，存入set中，当后面第二次出现，则说明有环的存在，此时的结点就是环的第一个结点

* 面试题2(下面代码实现)：
*   给定两个可能有环页可能无环的单链表，头结点head1和head2.
*       请实现一个函数，如果两个链表相交，请返回相交的第一个结点。如果不相交，返回null。
* */

/*
* 面试题3：
*   能不能不给单链表的头结点，只给想要删除的结点，就能做到再链表上把这个点删除？
*       不能用待删除的结点的下一个结点去覆盖待删除的结点，因为在内存存在问题；
*           因此，要想删除单链表的一个结点，还是要知道它的头结点，准确的连好指针。
* */
public class Code05_FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 面试题2：主函数，使用到面试题1的代码
    public static Node getIntersectNode(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        // 如果两个链表都是无环
        if (loop1 == null && loop2 == null) {
            return noLoop(head1, head2);
        }
        // 如果两个链表都是有环：有下面三个情况
        //      (① 两个链表各自独立的环；② 同一个环，入环结点是同一个；③ 同一个环，入环结点不是同一个)
        if (loop1 != null && loop2 != null) {
            return bothLoop(head1, loop1, head2, loop2);
        }
        // 一个链表有环，一个链表无环，是不会相交的
        return null;
    }


    // 面试题1：找到链表第一个入环结点，如果无环，返回null
    //    使用方式一实现：
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node n1 = head.next; // n1 -> slow
        Node n2 = head.next.next; // n2 -> fast
        while (n1 != n2) {
            if (n2.next == null || n2.next.next == null) {
                return null;
            }
            n2 = n2.next.next;
            n1 = n1.next;
        }
        // 快指针回到头结点，后面也是每次移动一步，最后相遇停留的位置就是入环的第一个结点
        n2 = head; // n2 -> walk again from head
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }


    // 如果两个链表都无环，返回第一个相交结点，如果不相交，返回null
    // （如果两个无环链表相交，后面的必定是相交的，因为一个结点不能指向两个下一个结点）
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        // 先依次遍历两个链表，保存最后一个结点，进行比较
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        // 如果内存地址不同，则不存在相交。
        //  （因为两个无环链表，前面存在相交，后面一定也是相交）
        if (cur1 != cur2) {
            return null;
        }

        // n ：链表1长度减去链表2长度的值
        cur1 = n > 0 ? head1 : head2;           // 谁长，谁的头变成cur1
        cur2 = cur1 == head1 ? head2 : head1;   // 谁短，谁的头变成cur2
        n = Math.abs(n);
        // 让两个链表中长的那个先走差值步数(n)
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        // 然后再一起走，遇到第一个内存地址相同的点，就是第一个交点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    // 如果两个链表都是有环
    //   (① 两个链表各自独立的环；② 同一个环，入环结点是同一个；③ 同一个环，入环结点不是同一个)
    public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = null;
        Node cur2 = null;
        // 情况② 入环结点是同一个时：
        if (loop1 == loop2) {
            cur1 = head1;
            cur2 = head2;
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            // n ：链表1长度减去链表2长度的值
            cur1 = n > 0 ? head1 : head2;           // 谁长，谁的头变成cur1
            cur2 = cur1 == head1 ? head2 : head1;   // 谁短，谁的头变成cur2
            n = Math.abs(n);
            // 让两个链表中长的那个先走差值步数(n)
            while (n != 0) {
                n--;
                cur1 = cur1.next;
            }
            // 然后再一起走，遇到第一个内存地址相同的点，就是第一个交点
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        // 情况③ 入环结点不是同一个时：
        //          让其中一个入环结点，进入环中移动，与另一个入环结点进行内存比较，相等则返回。
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1) {
                if (cur1 == loop2) {   // 转回自己之前遇到另一个入环结点
                    return loop1;
                }
                cur1 = cur1.next;
            }
            // 情况① 两个链表有各自独立的环：
            //      此情况下，两个链表没有交点，返回null
            return null;
        }
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
