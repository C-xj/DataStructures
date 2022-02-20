package Class12_暴力递归;

import java.util.Stack;

/* 逆序一个栈：
 *       给一个栈，请逆序这个栈，不能申请额外的数据结构，只能使用递归函数。
 * */
public class Code02_ReverseStackUsingRecursive {

	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		int i = f(stack);
		reverse(stack);
		stack.push(i);
	}

	// 去除栈中的栈底元素，并返回
	/*假设栈中元素1，2，3(栈底元素)
	* 	代码执行过程：首先第一次f函数result=1，然后last=第二次f函数的返回值；
	* 				第二次f函数中result=2，然后last=第三次f函数的返回值；
	* 				第三次f函数中result=3，然后返回result->3；
	* 				然后回到第二次f函数，即last=3，将此次函数中result->2压入栈中，返回last->3；
	* 				然后回到第一次f函数，即last=3，将此次函数中result->1压入栈中，返回last->3；
	* 				代码执行完毕。
	* */
	public static int f(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			int last = f(stack);
			stack.push(result);
			return last;
		}
	}

	public static void main(String[] args) {
		Stack<Integer> test = new Stack<Integer>();
		test.push(1);
		test.push(2);
		test.push(3);
		test.push(4);
		test.push(5);
		reverse(test);
		while (!test.isEmpty()) {
			System.out.println(test.pop());
		}

	}

}
