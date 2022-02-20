package Class12_暴力递归;

/* 简单的过河问题：
 * 		一个人需要过河，但是河里有鳄鱼，鳄鱼吃掉人之后会变的虚弱，就会被其他鳄鱼吃掉
 * 			当鳄鱼吃掉鳄鱼也会变的虚弱，会被其他鳄鱼吃掉，假如鳄鱼都极其聪明。
 * 			这个人在什么情况下可以过河，什么情况下不能过河？
 *
 * 	答案：鳄鱼为奇数(例如1)时，不能过河；为偶数(例如2)时可以过河。
 * 		解释：偶数关系中存在对峙和博弈，都不会主动去吃，因为谁吃掉就会变成奇数个鳄鱼时过河的"人"
 *
 * 	投信问题：
 * 		一个村庄有N个人，每个人只能投出一封信，同时也只能接收一封信，问一共有可以投出多少可能的信。
 * 			f(1):1个人时，   			0
 *			f(2):A > B 、 B > A			1
 *			f(3):A > B、B > C、C > A     2
 * 			...
 * 			f(5):情况①假如A > B,B > A 那剩下的三个人的就是个f(3)
 * 				 情况②假如A > B,B没有投递给A，那么AB就是一个整体等效为1个人
 * 						(一个等着发信，一个等着手信)，那与剩下的三个人一起的就是个f(4)
 * 					A > B、A > C、A > D、A > E四种大情况
 * 				因此 f(5) = 4 * (f(3) + f(4))
 *
 * */

/* N皇后问题：
 * 		N皇后问题是指在N*N的棋盘上要摆N个皇后
 * 		要求任何两个皇后不同行、不同列，也不再同一条斜线上
 *
 * 	给定一个整数n，返回n皇后的摆法有多少种。
 * 	n = 1，返回1
 * 	n = 2或3，无论怎么摆都不行，返回0
 *   ...
 * 	n = 8 ,返回92
 *
 * */
public class Code08_NQueens {

	public static int num1(int n) {
		if (n < 1) {
			return 0;
		}
		// record[0] ?  record[1]  ?  record[2]
		int[] record = new int[n]; // record[i] -> i行的皇后，放在了第几列（实现只用一个数组实现）
		return process1(0, record, n);
	}

	// 潜台词：record[0..i-1]的皇后，任何两个皇后一定都不共行、不共列，不共斜线
	// 目前来到了第i行
	// record[0..i-1]表示之前的行，放了的皇后位置
	// n代表整体一共有多少行 0~n-1行
	// 返回值是，摆完所有的皇后，合理的摆法有多少种
	public static int process1(int i, int[] record, int n) {
		if (i == n) { // 终止行，都摆完没有问题，返回1，表示这种摆法
			return 1;
		}
		int res = 0;
		for (int j = 0; j < n; j++) { // 当前行在i行，尝试i行所有的列  -> j
			// 当前i行的皇后，放在j列，会不会和之前(0..i-1)的皇后，不共行共列或者共斜线，
			// 如果是，认为有效，记录摆放的位置，统计后续摆放的可能性
			if (isValid(record, i, j)) {
				record[i] = j;
				res += process1(i + 1, record, n);
				// 不需要还原现场，因为，后续也是要修改的，直接覆盖掉数组上的值就可以。
			}
		}
		return res;
	}

	// record[0..i-1]你需要看，record[i...]不需要看
	// 返回i行皇后，放在了j列，是否有效
	public static boolean isValid(int[] record, int i, int j) {
		for (int k = 0; k < i; k++) { // i行之前的某个k行的皇后
			if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
				return false;
			}
		}
		return true;
	}


	// 请不要超过32皇后问题
	public static int num2(int n) {
		if (n < 1 || n > 32) {
			return 0;
		}
		int limit = n == 32 ? -1 : (1 << n) - 1;
		return process2(limit, 0, 0, 0);
	}

	// 使用位运算优化
	// colLim 列的限制，1的位置不能放皇后，0的位置可以
	// leftDiaLim 左斜线的限制，1的位置不能放皇后，0的位置可以
	// rightDiaLim 右斜线的限制，1的位置不能放皇后，0的位置可以
	public static int process2(int limit, 
			int colLim, 
			int leftDiaLim,
			int rightDiaLim) {
		if (colLim == limit) { // base case
			return 1;
		}
		// 所有候选皇后的位置，都在pos上
		int pos = limit & (~(colLim | leftDiaLim | rightDiaLim));
		int mostRightOne = 0;
		int res = 0;
		while (pos != 0) {
			mostRightOne = pos & (~pos + 1);
			pos = pos - mostRightOne;
			res += process2(limit, 
					colLim | mostRightOne,
					(leftDiaLim | mostRightOne) << 1,
					(rightDiaLim | mostRightOne) >>> 1);
		}
		return res;
	}

	public static void main(String[] args) {
		int n = 14;

		long start = System.currentTimeMillis();
		System.out.println(num2(n));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

		start = System.currentTimeMillis();
		System.out.println(num1(n));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + "ms");

	}
}
