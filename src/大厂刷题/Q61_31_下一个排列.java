package 大厂刷题;

/*
* 整数数组的一个 排列  就是将其所有成员以序列或线性顺序排列。
* 	整数数组的 下一个排列 是指其整数的下一个字典序更大的排列。
* 	更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，
* 	那么数组的 下一个排列 就是在这个有序容器中排在它后面的那个排列。
* 	如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。

* 	例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
	类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
	而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
	给你一个整数数组 nums ，找出 nums 的下一个排列。

	必须 "原地" 修改，只允许使用额外常数空间。

* 思路：
*	1、我们希望下一个数比当前数大，这样才满足“下一个排列”的定义。因此只需要将后面的「大数」与前面的「小数」交换，就能得到一个更大的数。比如 123456，将 5 和 6 交换就能得到一个更大的数 123465。
* 	2、我们还希望下一个数增加的幅度尽可能的小，这样才满足“下一个排列与当前排列紧邻“的要求。为了满足这个要求，我们需要：
		① 在尽可能靠右的低位进行交换，需要从后向前查找
		② 将一个 尽可能小的「大数」 与前面的「小数」交换。比如 123465，下一个排列应该把 5 和 4 交换而不是把 6 和 4 交换
		③ 将「大数」换到前面后，需要将「大数」后面的所有数重置为升序，升序排列就是最小的排列。以 123465 为例：首先按照上一步，交换 5 和 4，得到 123564；然后需要将 5 之后的数重置为升序，得到 123546。显然 123546 比 123564 更小，123546 就是 123465 的下一个排列
*
*
*
* */
public class Q61_31_下一个排列 {

	public static void nextPermutation(int[] nums) {
		int N = nums.length;
		// firstLess：从右往左第一次降序的位置
		int firstLess = -1;
		for (int i = N - 2; i >= 0; i--) {
			if (nums[i] < nums[i + 1]) {
				firstLess = i;
				break;
			}
		}
		if (firstLess < 0) {			// 依然是-1，表示是最大的字典序，因此返回其逆序
			reverse(nums, 0, N - 1);
		} else {						// 存在第一次降序
			int rightClosestMore = -1;	// 记录比nums[firstLess]大的数位置在哪
			// 找最靠右的、同时比nums[firstLess]大的数位置在哪
			// 这里其实也可以用二分优化，但是这种优化无关紧要了
			for (int i = N - 1; i > firstLess; i--) {
				if (nums[i] > nums[firstLess]) {
					rightClosestMore = i;
					break;
				}
			}
			swap(nums, firstLess, rightClosestMore);
			reverse(nums, firstLess + 1, N - 1);
		}
	}

	public static void reverse(int[] nums, int L, int R) {
		while (L < R) {
			swap(nums, L++, R--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

}
