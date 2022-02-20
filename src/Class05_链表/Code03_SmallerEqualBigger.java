package Class05_链表;

/**
 * @author Chu
 * @create 2021-11-23  19:21
 */
// 将单向链表按某值划分成左边小、中间相等、右边大的形式
//  方式1）把链表放入数组里，再数组上做partition（笔试用）
//  方式2）分成小、中、大三部分，再把各个部分之间串起来(面试用)
//      ① 先分小、中、大三个区(每个区都有一个头、一个尾)，
//      ② 遍历链表，与基准作比较，每个区的第一个直接存到头和尾，并断掉该结点在链表中的连接，后面数要进行比较连接到，
//      ③ 每个区记录这个短链的一个头，一个尾，最后进行连接。
public class Code03_SmallerEqualBigger {
    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    // 方式一：
    // 遍历将链表的结点存入结点数组中
    public static Node listPartition1(Node head, int pivot) {
        if (head == null) {
            return head;
        }
        Node cur = head;
        int i = 0;
        // 计算链表长度
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        // 创建一个链表长度的结点数组
        Node[] nodeArr = new Node[i];
        i = 0;
        cur = head;
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        // 根据指定值，进行划分交换为小于、等于、大于形式
        arrPartition(nodeArr, pivot);
        // 重新对结点数组的元素进行连接
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        // 输出第一个元素，即新的头结点
        return nodeArr[0];
    }

    // 荷兰国旗问题：
    // 交换结点数组中的值，进行根据一个数划分成左边小、中间相等、右边大的形式
    public static void arrPartition(Node[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].value < pivot) { // 此步完成的是原地交换，或者把与基准相等的数与小于基准的数进行交换
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].value == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    public static void swap(Node[] nodeArr, int a, int b) {
        Node tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    // 方式二：
    public static Node listPartition2(Node head, int pivot) {
        Node sH = null; // small head   小于区域的头
        Node sT = null; // small tail
        Node eH = null; // equal head   等于区域的头
        Node eT = null; // equal tail
        Node bH = null; // big head     大于区域的头
        Node bT = null; // big tail
        Node next = null; // save next node
        // every node distributed to three lists
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.value < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.value == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (bH == null) {
                    bH = head;
                    bT = head;
                } else {
                    bT.next = head;
                    bT = head;
                }
            }
            head = next;
        }
        // 小于区域的尾巴连等于区域的头，等于区域的尾巴连大于区域的头
        if (sT != null) {   // 如果有小于区域
            sT.next = eH;
            eT = eT == null ? sT : eT;  // 下一步，谁去连大于区域的头，谁就变成eT
        }
        // 上面的if，不管跑了没有，et
        // all reconnect
        if (eT != null) {   // 如果小于区域和等于区域，不是都没有
            eT.next = bH;
        }
        return sH != null ? sH : eH != null ? eH : bH;
    }

    public static void printLinkedList(Node node) {
        System.out.print("Linked List: ");
        while (node != null) {
            System.out.print(node.value + " ");
            node = node.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Node head1 = new Node(7);
        head1.next = new Node(9);
        head1.next.next = new Node(1);
        head1.next.next.next = new Node(8);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(2);
        head1.next.next.next.next.next.next = new Node(5);
        printLinkedList(head1);
        // head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);

    }
}
