package Class02_栈_队列_链表;

/**
 * @author Chu
 * @create 2021-11-14  15:29
 */
// 双向链表实现栈和队列
public class Code03_DoubleEndsQueueToStackAndQueue {

    public static class Node<T>{
        public T value;
        public Node<T> last;
        public Node<T> next;

        public Node(T data){
            value = data;
        }
    }

    // 双向链表一些方法
    public static class DoubleEndsQueue<T>{
        public Node<T> head;
        public Node<T> tail;

        // 头部进
        public void addFromHead(T value){
            Node<T> cur = new Node<>(value);
            if(head == null){
                head = cur;
                tail = cur;
            }else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        // 尾部进
        public void addFromBottom(T value){
            Node<T> cur = new Node<>(value);
            if(head == null){
                head = cur;
                tail = cur;
            }else {
                cur.last = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        // 头部出
        public T popFromHead(){
            if(head == null){
                return null;
            }
            Node<T> cur = head;
            if (head == tail){  // 只有最后一个结点时
                head = null;
                tail = null;
            }else {             // 让待删除的结点与链表不在挂钩
                head = head.next;
                cur.next = null;
                head.last = null;
            }
            return cur.value;
        }

        // 尾部出
        public T popFromBottom(){
            if(head == null){
                return null;
            }
            Node<T> cur = tail;
            if (head == tail){  // 只有最后一个结点时
                head = null;
                tail = null;
            }else {             // 让待删除的结点与链表不在挂钩
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }


        // 判断是否为空
        public boolean isEmpty(){
            return head == null;
        }


        // 栈结构 - - - 先进后出
        // 补充知识：成员内部类，可以无条件访问外部类的属性和方法
        //                  外部类访问内部类，必须要创建一个内部类对象
        public static class MyStack<T>{
            private DoubleEndsQueue<T> queue;

            public MyStack(){
                DoubleEndsQueue<T> queue = new DoubleEndsQueue<>();
            }

            public void push(T value){
                queue.addFromHead(value);
            }

            public T pop(){
                return queue.popFromHead();
            }

            public boolean isEmpty(){
                return queue.isEmpty();
            }

        }


        // 队列结构 - 头进尾出
        public static class MyQueue<T>{
            private DoubleEndsQueue<T> queue;

            public MyQueue(){
                DoubleEndsQueue<T> queue = new DoubleEndsQueue<>();
            }

            public void push(T value){
                queue.addFromHead(value);
            }

            public T poll(){
                return queue.popFromBottom();
            }

            public boolean isEmpty(){
                return queue.isEmpty();
            }

        }


    }


}
