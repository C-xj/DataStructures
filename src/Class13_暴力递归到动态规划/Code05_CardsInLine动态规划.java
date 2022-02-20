package Class13_暴力递归到动态规划;

/*范围上尝试的模型：
 *	给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 	玩家A和玩家B依次拿走每张纸牌
 * 	规定玩家A先拿，玩家B后拿
 * 	但是每个玩家每次只能拿走最左或最右的纸牌，
 * 	玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数
 *
 *
 *	一个先手拿返回最大数值的函数f(arr,l,r)，一个后手拿返回最大数值的函数s(arr,l,r)
 * 		先手函数f(arr,l,r)中：
 *
 * 			if(l == r) return arr[l];	只剩一张
 *
 * 			先手拿的值 + 下次后手拿的值 两种选择中大的那一个
 * 			max{
 * 				1) arr[l] + s(arr,l+1,r);
 * 				2) arr[r] + s(arr,l,r-1);
 * 			}
 *
 * 		后手函数s(arr,l,r)中：
 *
 * 			if(l == r) return 0;
 *
 * 			对手拿过之后，轮到自己先手拿的值 对手给自己剩下的是两种选择中小的那一个
 * 			min{
 * 				1) 对手拿arr[l]  剩下f(arr,l+1,r);
 * 				2) 对手拿arr[r]  剩下f(arr,l,r-1);
 * 			}
 * */
public class Code05_CardsInLine动态规划 {

	// 暴力递归
	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 返回先手、后手两个人的最大分数
		return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
	}

	// 先手函数
	public static int f(int[] arr, int L, int R) {
		if (L == R) {	// 只剩一个，先手就返回这个数
			return arr[L];
		}
		// 先手拿的值 + 下次后手拿的值 两种选择中大的那一个
		return Math.max(arr[L] + s(arr, L + 1, R), arr[R] + s(arr, L, R - 1));
	}

	// 后手函数
	public static int s(int[] arr, int L, int R) {
		if (L == R) {	// 只剩一个，后手就只能返回0
			return 0;
		}
		// 对手拿过之后，轮到自己先手拿的值，对手给自己剩下的是两种选择中小的那一个(因为对手要尽可能损害你的利益)
		return Math.min(f(arr, L + 1, R), f(arr, L, R - 1));
	}


	// 动态规划1  用两个二维数组L > R 无用 ---数组左下角无用
	public static int win2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];	// 表先手 int[L][R]
		int[][] s = new int[arr.length][arr.length];	// 表后手 int[L][R]
		for (int j = 0; j < arr.length; j++) {
			f[j][j] = arr[j];		// 表示L == R 只剩一个数，就赋这个数的值给数组对应的位置
			// s[i][i] = 0; 		创建初始化已经是0
			for (int i = j - 1; i >= 0; i--) {
				f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
				s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
			}
		}
		return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
	}

	// 动态规划2  用两个二维数组L > R 无用 ---数组左下角无用
	public static int win3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];	// 表先手 int[L][R]
		int[][] s = new int[arr.length][arr.length];	// 表后手 int[L][R]

		for (int j = 0; j < arr.length; j++) {
			f[j][j] = arr[j];		// 表示L == R 只剩一个数，就赋这个数的值给数组对应的位置
			// s[i][i] = 0; 		创建初始化已经是0
		}
		for (int i = 1;i < arr.length;i++){
			int L = 0;
			int R = i;
			while (L < arr.length && R < arr.length ){
				f[L][R] = Math.max(arr[L] + s[L + 1][R], arr[R] + s[L][R - 1]);
				s[L][R] = Math.min(f[L + 1][R], f[L][R - 1]);
				L++;
				R++;
			}
		}
		return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 9, 1 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));

	}

}
