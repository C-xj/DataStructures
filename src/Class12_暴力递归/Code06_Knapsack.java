package Class12_暴力递归;

/* 从左往右的尝试模型2 --- 背包问题
 * 		给定两个长度都为N的数组weights和values，
 * 		weights[i]和values[i]分别代表i号物品的重量和价值。
 * 		给定一个正数bag，表示一个载重bag的袋子，
 * 		你装的物品不能超过这个重量。
 * 		返回你能装下最多的价值是多少？
 *
 * 	要当前货，不要当前货两种选择
 * */

public class Code06_Knapsack {


	// 重量  3 2 4 7
	// 价值  5 6 3 19
	// 11
	public static int getMaxValue(int[] w, int[] v, int bag) {

		return process(w, v, 0, 0, bag);
	}

	/** 背包问题 暴力递归
	 * 实现方式一：
	 * @param w        物品
	 * @param v        价值
	 * @param index    前面已经决定过得索引
	 * @param alreadyW 背包已经使用的容量 0..index-1
	 * @param bag      背包的总容量
	 * @return 能装下的最大价值
	 */

	private static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
		if (alreadyW > bag) {
			return -1;		// 返回-1表示没有方案，认为返回的值是真实价值
		}
		// 重量没超，但是没货了，方案有效，后续价值为0
		if (index == w.length) {
			return 0;
		}

		// 不要
		int p1 = process(w, v, index + 1, alreadyW, bag);

		// 想要
		int p2next = process(w, v, index + 1, alreadyW + w[index], bag);

		// 可能这个东西要之后超了总容量，所以要有判断
		int p2 = -1;
		if (p2next != -1) {				//  成功要了后面的货
			p2 = v[index] + p2next;		//  要当前货总价值=当前货的价值 + 要了当前货后面货的总价值
		}
		return Math.max(p1, p2);		// 返回两种可能的最大值
	}

	// 方式一的：动态规划
	public static int dpWay(int[] w,int[] v,int bag){
		int N = w.length;
		int[][] dp = new int[N + 1][bag + 1];
		// dp[N][...] = 0 初始化默认已经是了，不用重新设置
		// 从下面的行依次推导上面的行
		for (int index = N - 1;index >= 0;index--){
			// 剩余空间从1到bag
			for(int rest = 0;rest <= bag;rest++){
				// 不要这个物品
				int p1 = dp[index+1][rest];
				int p2 = -1;
				// 可以要这个物品
				if (rest - w[index] >= 0){
					p2 = v[index] + dp[index+1][rest - w[index]];
				}
				dp[index][rest] = Math.max(p1,p2);

			}
		}
		return dp[0][bag];
	}

	/* 实现方式二：
	* 	只剩下rest的空间
	* 	index...货物自由选择，但是不要超过rest的空间
	* 	返回能够获得的最大价值
	* */
	public static int process(int[] w, int[] v, int index, int rest){
		if (rest < 0){
			return -1;
		}
		// rest >= 0 ，有空间但没货
		if (index == w.length){
			return 0;
		}
		// 有货也有空间，下面写法也可以按照方式一中的写。
		int p1 = process(w,v,index + 1,rest);		// 不要当前的货
		int p2 = Integer.MIN_VALUE;
		if (rest >= w[index]){							// 可以要当前货物
			p2 = v[index] + process(w,v,index + 1,rest - w[index]);
		}
		return Math.max(p1,p2);
	}


	public static void main(String[] args) {
		int[] weights = {3, 2, 4, 7};
		int[] values = {5, 6, 3, 19};
		int bag = 11;
		System.out.println(getMaxValue(weights, values, bag));
	}

}


