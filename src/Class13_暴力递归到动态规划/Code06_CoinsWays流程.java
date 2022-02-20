package Class13_暴力递归到动态规划;


/* 题目一：
 *	有一个数组，数组中每个位置的值，是某一个货币的面值(都是正数，且无重复值)，每一种面值的货币都可以使用任意张。
 *		假如：目标金额为1000,有多少种方法使用上面的货币表示出来。
 * */

public class Code06_CoinsWays流程 {

// --------------------------流程1：暴力递归------------------------

	// arr里都是正数，没有重复值，每一个值代表一种货币，每一种都可以用无限张，目标金额数是aim，
	public static int way1(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0){
			return 0;
		}

		return process1(arr, 0, aim);
	}

	// 可以自由使用arr[index...]所有的面值(index及其以后的面值)
	public static int process1(int[] arr, int index, int rest) {
		// rest >= 0 因为下面不会出现rest的情况
		if (index == arr.length) {		// 没有货币可以选择，且刚好消费完rest，返回1（即是一种方案）
			return rest == 0 ? 1 : 0;
		}
		// arr[index] 0张 1张 ... 不要超过rest的钱数
		int ways = 0;
		for (int zhang = 0; arr[index] * zhang <= rest; zhang++) {
			ways += process1(arr, index + 1, rest - arr[index] * zhang);
		}
		return ways;
	}


// --------------------------流程2：记忆化搜索-加缓存------------------------
	public static int way2(int[] arr, int aim) {
		if (arr == null || arr.length == 0 || aim < 0){
			return 0;
		}
		// 缓存表
		int[][] dp = new int[arr.length+1][aim+1];
		// 一开始所有的过程，都没有计算 dp[..][..] = -1
		for (int i = 0 ;i < dp.length;i++){
			for (int j = 0 ;j < dp[0].length;j++){
				dp[i][j] = -1;
			}
		}

		return process2(arr, 0, aim,dp);
	}

	// 如果index和rest的参数组合，是没有算过的，dp[index][rest] == -1
	// 如果index和rest的参数组合，是算过的，dp[index][rest] > -1
	public static int process2(int[] arr, int index, int rest,int[][] dp) {
		if (dp[index][rest] != -1){
			return dp[index][rest];
		}

		if (index == arr.length) {		// 没有货币可以选择，且刚好消费完rest，返回1（即是一种方案）
			dp[index][rest] = rest == 0 ? 1 : 0;
			return dp[index][rest];
		}
		// arr[index] 0张 1张 ... 不要超过rest的钱数
		int ways = 0;
		for (int zhang = 0; arr[index] * zhang <= rest; zhang++) {
			ways += process2(arr, index + 1, rest - arr[index] * zhang,dp);
		}
		dp[index][rest] = ways;
		return dp[index][rest];
	}


// --------------------------流程3：动态规划1------------------------
	public static int way3(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;			// 没有货币可以选择，且刚好消费完rest，返回1（即是一种方案）
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				int ways = 0;
				for (int zhang = 0; arr[index] * zhang <= rest; zhang++) {
					ways += dp[index + 1][rest - arr[index] * zhang];
				}
				dp[index][rest] = ways;
			}
		}
		return dp[0][aim];
	}

// --------------------------流程3：动态规划2------------------------
	public static int way4(int[] arr, int aim) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		dp[N][0] = 1;
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 0; rest <= aim; rest++) {
				// 优化枚举行为
				/* 假如，要获得第10行(面值为3)，列值为100的点的值 dp[10][100]
				* 		for (int zhang = 0; arr[index] * zhang <= rest; zhang++) {
							ways += process2(arr, index + 1, rest - arr[index] * zhang,dp);
						}
				*       dp[index][rest] = ways;
				*
				*   ① 从上面暴力递归中，可以看出，此位置依赖于dp[11][..]以及货币面值3使用的张数的累加。
				* 		即 dp[10][100] = dp[11][100](0张) + dp[11][97](1张) + ... + dp[11][0]
				* 	② 因为dp[10][97] 比 dp[10][100]先得到，
				* 		而 dp[10][97] =  dp[11][97](1张) + ... + dp[11][0]
				*	③ 前提当rest不越界情况下，即 rest - arr[index] >=0时
				* 		因此 dp[10][100] = dp[11][100](0张) + dp[10][97]
				* */
				// 先让其获得整下方的方法数量，再加上其左边的1张的方法数量
				dp[index][rest] = dp[index+1][rest];
				if(rest - arr[index] >=0) {
					dp[index][rest] += dp[index][rest - arr[index]];
				}
			}
		}
		return dp[0][aim];
	}



	public static void main(String[] args) {
		int[] arr = {5,10,50,100};
		int sum = 1000;
		System.out.println(way1(arr,sum));
		System.out.println(way2(arr,sum));
		System.out.println(way3(arr,sum));
		System.out.println(way4(arr,sum));
	}

}
