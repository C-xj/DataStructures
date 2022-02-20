package Class02_栈_队列_链表;

import java.util.Stack;

/**
 * @author Chu
 * @create 2021-11-14  19:36
 */
// 用两个栈实现队列
public class Code06_TwoStacksImplementQueue {

    // 使用两个栈，一个push先存入数据，pop中转后导入，达到队列效果
    // 1）pop为空，push才能导进来pop数据
    // 2）push在导进pop数据，要一次性导入完
    public static class TwoStackQueue{
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        public TwoStackQueue(){
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();

        }

        // push栈向pop栈导入数据
        private void pushToPop(){
            if (stackPop.empty()){
                while (!stackPush.empty()){
                    stackPop.push(stackPush.pop());
                }
            }
        }

        public void push(int pushInt){
            stackPush.push(pushInt);
            pushToPop();
        }

        public int pop(){
            if (stackPop.empty() && stackPush.empty()){
                throw new RuntimeException("Queue is empty.");
            }
            pushToPop();
            return stackPop.pop();
        }

        public int peek(){
            if (stackPop.empty() && stackPush.empty()){
                throw new RuntimeException("Queue is empty.");
            }
            pushToPop();
            return stackPop.peek();
        }
    }


}
