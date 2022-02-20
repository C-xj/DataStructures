package 大厂刷题;

import java.util.LinkedList;

/**
 * @author Chu
 * @create 2022-01-06  21:09
 */
/*
* 给定一个字符串str，str表示一个公式
        公式里可能有整数、加减乘除符号和左右括号
        返回公式的计算结果，难点在手括号可能嵌套很多层
        str="48+((70-65)-43)+8*1",返回-1816
        str="3+1*4",返回7
        str="3+(1*4)"，返回7
*   说明：
*       ① 可以认为给定的字符串一定时正确的公式，即不需要对str做公式有效性检查
*       ② 如果是负数，就需要用括号括起来，但如果负数作为公式的开头或括号部分的开头，
*           则可以没有括号，比如"-3*4"和"(-3*4)"都是合法的
*       ③ 不用考虑计算过程中会发生溢出的情况
*
*
* 思路： 例如 1  + 2 * ( 3 + 4 * ( 2  -  1  )  )  +  5
*           0  1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
*
*   一个f函数，有两个返回值，一个为计算结果，一个为执行到的位置
*
*   cur初始为0，
*       1）每遇到数字
*              cur*10后 + 此数字，结果赋给cur，
*       2）当遇到符号时，
*           判断栈顶是否是乘除，是就先计算，然后cur=0，压入计算值，如果不是则cur=0，直接压入栈中
*
*   ① 首先f(0),创建一个栈，将1+2*分别压入栈中，遇到(，
*   ② 递归调用f(5),创建一个栈，将3+4*压入栈，遇到(，
*   ③ 递归调用f(10),创建一个栈，将2-1压入栈，遇到)，计算，返回数据...
*   ④ 把结果压入f(5)的栈，然后遇到)，计算返回数据...
*   ......
*
* */
public class Q13_公式字符串的结算结果 {

    public static int getValue(String str) {

        return f(str.toCharArray(), 0)[0];
    }

    // 请从str[i...]往下算，遇到字符串终止位置或者右括号，就停止
    // 返回两个值，长度为2的数组:0) 负责的这一段的结果是多少; 1) 负责的这一段计算到了哪个位置
    public static int[] f(char[] str, int i) {
        // 双端队列
        LinkedList<String> que = new LinkedList<String>();
        int cur = 0;
        int[] bra = null;
        // 从i出发，开始撸串,不能遇到右括号或者字符串的结尾
        while (i < str.length && str[i] != ')') {
            if (str[i] >= '0' && str[i] <= '9') {
                cur = cur * 10 + str[i++] - '0';
            } else if (str[i] != '(') { // 遇到的是运算符号
                addNum(que, cur);
                que.addLast(String.valueOf(str[i++]));
                cur = 0;
            } else { // 遇到左括号了
                bra = f(str, i + 1);    // 进入递归
                cur = bra[0];             // 括号内的计算结果
                i = bra[1] + 1;           // 子过程计算到什么位置，下一步继续
            }
        }
        // 跳出后，要记得放入最后一个数字
        addNum(que, cur);
        return new int[] { getNum(que), i };
    }

    // 将数字加入，并计算调整
    public static void addNum(LinkedList<String> que, int num) {
        if (!que.isEmpty()) {
            int cur = 0;
            String top = que.pollLast();
            if (top.equals("+") || top.equals("-")) {
                que.addLast(top);
            } else {
                cur = Integer.valueOf(que.pollLast());
                num = top.equals("*") ? (cur * num) : (cur / num);
            }
        }
        que.addLast(String.valueOf(num));
    }

    //
    public static int getNum(LinkedList<String> que) {
        int res = 0;
        boolean add = true;
        String cur = null;
        int num = 0;
        while (!que.isEmpty()) {
            cur = que.pollFirst();
            if (cur.equals("+")) {
                add = true;
            } else if (cur.equals("-")) {
                add = false;
            } else {
                num = Integer.valueOf(cur);
                res += add ? num : (-num);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String exp = "48*((70-65)-43)+8*1";
        System.out.println(getValue(exp));

        exp = "4*(6+78)+53-9/2+45*8";
        System.out.println(getValue(exp));

        exp = "10-5*3";
        System.out.println(getValue(exp));

        exp = "-3*4";
        System.out.println(getValue(exp));

        exp = "3+1*4";
        System.out.println(getValue(exp));

    }


}
