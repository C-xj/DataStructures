package 大厂刷题;

import java.util.HashMap;

/*
* 给定一个正整数数组 nums和一个整数 k，返回 num中 「好子数组」的数目。
	如果 nums的某个子数组(连续)中不同整数的个数恰好为 k，则称 nums的这个连续、不一定不同的子数组为 「好子数组 」。
*
* 		输入：nums = [1,2,1,2,3], k = 2
		输出：7
		解释：恰好由 2 个不同整数组成的子数组：[1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2].
*
*	解法1：滑动窗口：
* 		数组：2 2 2 1 1 1 3 3 3 3 3  	k=3：
* 		索引：0 1 2 3 4 5 6 7 8 9 10
*	 	方式①:
* 			left以0索引开始：
* 				窗口1：刚好k = 3时，right1来到索引6，
* 				窗口2：刚好k = 4(即k + 1)时，right2来到索引11，
* 						11 - 6 = 5个，就是以0为开始的好子数组。
* 			left以1索引开始：...
*			...
* 	  	注意：两个窗口要同时调整滑动
*
* 		方式②：
* 			我们需要记录两个左指针 left1与 left2来表示左端点区间[left1,left2)。
* 				第一个左指针表示极大的包含 k 个不同整数的区间的左端点，
* 				第二个左指针则表示极大的包含 k-1个不同整数的区间的左端点。
*			left2 - left1，就是当前窗口的好子数组
*
* 	解法2：把 「恰好」 转换成为 「最多」
*		把「恰好」改成「最多」就可以使用双指针一前一后交替向右的方法完成，
* 		这是因为对于每一个确定的左边界，最多包含 K 种不同整数的右边界是唯一确定的，并且在左边界向右移动的过程中，右边界或者在原来的地方，或者在原来地方的右边。
*
*	 而「恰好存在 K 个不同整数的子区间的个数」恰好等于「最多存在 K 个不同整数的子区间的个数」与「最多存在 K - 1 个不同整数的子区间的个数」的差
*
* 	因为原问题就转换成为求解「最多存在 KK 个不同整数的子区间的个数」与 「最多存在 K - 1K−1 个不同整数的子区间的个数」，它们其实是一个问题。
*
* */
public class Q64_992_K个不同正数的子数组 {

	// 滑动窗口 ②
	// nums 数组，题目规定，nums中的数字，不会超过nums的长度
	// [ ]长度为5，0~5
	public static int subarraysWithKDistinct1(int[] nums, int k) {
		int n = nums.length;
		// k-1种数的窗口词频统计
		int[] lessCounts = new int[n + 1];
		// k种数的窗口词频统计
		int[] equalCounts = new int[n + 1];
		int lessLeft = 0;		// k-1种数窗口的左边界 (left1)
		int equalLeft = 0;		// k种数窗口的左边界	 (left2)
		int lessKinds = 0;		// 记录k-1种数窗口的不同数的个数
		int equalKinds = 0;		// 记录k种数窗口的不同数的个数
		int ans = 0;
		for (int r = 0; r < n; r++) {		// r表示窗口的右边界端口
			// 当前刚来到r位置！
			if (lessCounts[nums[r]] == 0) {
				lessKinds++;
			}
			if (equalCounts[nums[r]] == 0) {
				equalKinds++;
			}
			lessCounts[nums[r]]++;		// 增加对应数字出现的次数
			equalCounts[nums[r]]++;		// 增加对应数字出现的次数
			while (lessKinds == k) {	// 减到 k - 1 跳出循环
				if (lessCounts[nums[lessLeft]] == 1) {
					lessKinds--;
				}
				lessCounts[nums[lessLeft++]]--;
			}
			while (equalKinds > k) {	// 减到 k 跳出循环
				if (equalCounts[nums[equalLeft]] == 1) {
					equalKinds--;
				}
				equalCounts[nums[equalLeft++]]--;
			}
			ans += lessLeft - equalLeft;
		}
		return ans;
	}

	// 解法2：把 「恰好」 转换成为 「最多」
	// 「恰好存在 K 个不同整数的子区间的个数」恰好等于「最多存在 K 个不同整数的子区间的个数」与「最多存在 K - 1 个不同整数的子区间的个数」的差
	public static int subarraysWithKDistinct2(int[] arr, int k) {
		return numsMostK1(arr, k) - numsMostK1(arr, k - 1);
	}

	// 最多存在 K 个不同整数的子区间的个数
	private static int numsMostK1(int[] arr, int K) {
		int len = arr.length;
		int[] freq = new int[len + 1];

		int left = 0;		// 窗口左边界
		int right = 0;		// 窗口右边界
		int count = 0;		// 记录[left, right) 里不同整数的个数
		int res = 0;		// 记录多少子数组
		// [left, right) 包含不同整数的个数小于等于 K
		while (right < len) {
			if (freq[arr[right]] == 0) {	// 此数字没出现过，不同整数个数+1
				count++;
			}
			freq[arr[right]]++;				// 此数字出现次数+1
			right++;						// 拓展右边界

			while (count > K) {				// 当出现大于K，减少词频
				freq[arr[left]]--;
				if (freq[arr[left]] == 0) {	// 减到零，则不同整数个数-1
					count--;
				}
				left++;						// 移动左边界
			}
			// [left, right) 区间的长度就是对结果的贡献
			// 当前左右边界窗口下 count == k 一共有多少个子数组
			res += right - left;
		}
		return res;
	}


	// 最多存在 K 个不同整数的子区间的个数
	public static int numsMostK2(int[] arr, int k) {
		int i = 0, res = 0;
		HashMap<Integer, Integer> count = new HashMap<>();
		for (int j = 0; j < arr.length; ++j) {
			if (count.getOrDefault(arr[j], 0) == 0) {
				k--;
			}
			count.put(arr[j], count.getOrDefault(arr[j], 0) + 1);
			while (k < 0) {
				count.put(arr[i], count.get(arr[i]) - 1);
				if (count.get(arr[i]) == 0) {
					k++;
				}
				i++;
			}
			res += j - i + 1;
		}
		return res;
	}

}
