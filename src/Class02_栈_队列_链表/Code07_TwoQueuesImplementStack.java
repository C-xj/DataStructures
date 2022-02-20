package Class02_栈_队列_链表;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chu
 * @create 2021-11-14  19:57
 */
// 两个队列实现一个栈
public class Code07_TwoQueuesImplementStack {


    //   思路：1、 两个队列数据来回换，一个队列加入元素，弹出元素时，需要把队列中的元素放到另外一个队列中，删除最后一个元素
    //        2、 两个队列始终保持只有一个队列是有数据的,一个空

    public static class StackByQueue<T> {

        // LinkedList是Queue的一个实现类
        private Queue<T> queue1 = new LinkedList<>();

        private Queue<T> queue2 = new LinkedList<>();

        /**
         * 向空的队列中加入数据（因为总有一个有数据，一个为空）
         * 压栈
         * 入栈非空的队列
         */
        public boolean push(T t) {
            if (!queue1.isEmpty()) {
                return queue1.offer(t);
            } else {
                return queue2.offer(t);
            }
        }

        /**
         * 保证一个队列有数据，一个队列最后为空
         * 弹出并删除元素
         */
        public T pop() {
            if (queue1.isEmpty() && queue2.isEmpty()) {
                throw new RuntimeException("queue is empty");
            }
            if (!queue1.isEmpty() && queue2.isEmpty()) {
                // 循环中向另一个队列添加数,留下最后一个，在后面弹出
                while (queue1.size() > 1) {
                    queue2.offer(queue1.poll());
                }
                // 留最后一个弹出，此队列即为空
                return queue1.poll();
            }
            if (queue1.isEmpty() && !queue2.isEmpty()) {
                while (queue2.size() > 1) {
                    queue1.offer(queue2.poll());
                }
                return queue2.poll();
            }

            return null;
        }
    }

}


