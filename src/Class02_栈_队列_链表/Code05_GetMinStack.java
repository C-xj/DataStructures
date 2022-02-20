package Class02_栈_队列_链表;

import java.util.Stack;

/**
 * @author Chu
 * @create 2021-11-14  17:09
 */
// 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
public class Code05_GetMinStack {

    //  方式一：使用两个栈，一个正常存取数据，另一个存当前栈中最小的值(即刚加入的数，
    //              与此栈的栈顶元素进行对比，小的加入最小栈的栈顶)，弹出是两个栈一起弹出
    //  方式二：使用两个栈，一个正常存取数据，另一个存当前栈中最小的值(当刚加入的数
    //              小于等于最小栈的栈顶时，加入最小栈栈顶)，弹出是当两个栈的顶部相等时才弹出



    // 方式一：
    public static class MyStack1{
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack1(){
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }


        public void push(int newNum){
            if(this.stackMin.isEmpty()){
                this.stackMin.push(newNum);
            }else if(newNum < this.getMin()){
                this.stackMin.push(newNum);
            }else {
                int newMin = this.stackMin.peek();
                this.stackMin.push(newMin);
            }
            this.stackData.push(newNum);
        }

        public int pop(){
            if (this.stackData.isEmpty()){
                throw new RuntimeException("Your stack is empty.");
            }
            this.stackMin.pop();

            return this.stackData.pop();
        }

        public int getMin(){
            if (this.stackMin.isEmpty()){
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }

    }

    // 方式二：
    public static class MyStack2{
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public MyStack2(){
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }


        public void push(int newNum){
            if(this.stackMin.isEmpty()){
              this.stackMin.push(newNum);
            }else if(newNum <= this.getMin()){
                this.stackMin.push(newNum);
            }
            this.stackData.push(newNum);
        }

        public int pop(){
            if (this.stackData.isEmpty()){
                throw new RuntimeException("Your stack is empty.");
            }
            int value = this.stackData.pop();
            if (value == this.getMin()){
                this.stackMin.pop();
            }
            return value;
        }

        public int getMin(){
            if (this.stackMin.isEmpty()){
                throw new RuntimeException("Your stack is empty.");
            }
            return this.stackMin.peek();
        }

    }

}
