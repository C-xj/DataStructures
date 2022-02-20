package 大厂刷题;

import java.util.Arrays;
import java.util.HashMap;

// 来自小红书
// 一个无序数组长度为n，所有数字都不一样，并且值都在[0...n-1]范围上
// 返回让这个无序数组变成有序数组的最小交换次数(交换是任意交换，不是只能相邻交换  从大到小/从小到大)
/*
* 	离散化：
* 		因为数组中的数不是连续的大小关系，给它们离散化
* 		例如：[13,88,9]  离散化后[1,2,0]
* 				将最小的9离散为0；最大的88离散为2；中间的离散为1
*
* 		此题中，离散后数组中一定是0 ~ n-1这些数！！！
*
*
*
*
*
* 	下标循环怼：
* 		[0,1,2,3,4]
* 		[4,2,3,1,0]		0位置的4要去到4位置，就将4位置的0怼出来了，然后这个0要去0位置，直到形成闭环(4->0)。
*						上面4 0 是一个闭环， 2 3 1 是一个闭环（环与环之间不相交）
*
* 		假设一个数组长度为N，一共有m个环，
* 			第1个环：有a个数字，	交换次数a - 1
* 			第2个环：有b个数字，	交换次数b - 1
* 			第3个环：有c个数字，	交换次数c - 1
* 			...
* 			总的交换次数： N - m
* */
public class Q60_整体变有序的最小交换次数_离散化_下标循环怼 {


	// 纯暴力，arr长度大一点都会超时
	// 但是绝对正确
	public static int minSwap1(int[] arr) {
		return process1(arr, 0);
	}

	// 让arr变有序，最少的交换次数是多少！返回
	// times, 之前已经做了多少次交换
	public static int process1(int[] arr, int times) {
		boolean sorted = true;					// true认为有序
		for (int i = 1; i < arr.length; i++) {	// 遍历arr
			if (arr[i - 1] > arr[i]) {			// 违反从小到大，有违规，变成false
				sorted = false;
				break;
			}
		}
		// 如果依然有序，一次也不用交换，沿用之前的交换此数
		if (sorted) {
			return times;
		}
		// 数组现在是无序的状态！
		// 因为可以随意交换(不是相邻交换，所以交换此数一定小于数组长度，
		// 		n个数，即使不做任何交换，一个数单纯的去到其应该去的位置，最多也就n-1次)
		if (times >= arr.length - 1) {		// 大于数组长度，此分支无效，返回系统最大值
			return Integer.MAX_VALUE;
		}
		int ans = Integer.MAX_VALUE;		// 记录最小交换次数
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {	// 两个循环枚举所以可能的交换
				swap(arr, i, j);			// 一次交换后递归返回交换后最小交换次数
				ans = Math.min(ans, process1(arr, times + 1));
				swap(arr, i, j);			// 恢复现场
			}
		}
		return ans;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 已知arr中，只有0~n-1这些值，并且都出现1次
	// 下标循环怼：记录交换次数
	public static int minSwap2(int[] arr) {
		int ans = 0;
		for (int i = 0; i < arr.length; i++) {
			while (i != arr[i]) {		// 判断：如果i与i位置的数字不等就开始下标循环怼
				// 交换(使i位置对应的数去到与此数相等的对应下标位置，对应下标位置的数来到i位置，交换次数++，继续判断)
				swap(arr, i, arr[i]);
				ans++;
			}
		}
		return ans;
	}


	// 为了测试
	public static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = i;
		}
		for (int i = 0; i < len; i++) {
			swap(arr, i, (int) (Math.random() * len));
		}
		return arr;
	}

	public static void main(String[] args) {
		int n = 6;
		int testTime = 2000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * n) + 1;
			int[] arr = randomArray(len);
			int ans1 = minSwap1(arr);
			int ans2 = minSwap2(arr);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}


	// 怎么做离散化
	// [13,88,9]  离散化后  [1,2,0]
	public static void change(int[] arr){
		// [13,88,9] -> [13,88,9]
		int[] copy = Arrays.copyOf(arr, arr.length);
		Arrays.sort(copy);		// [9,13,88]
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0;i < copy.length;i++){
			map.put(copy[i],i);
		}
		// 9 -> 0
		// 13 -> 1
		// 88 -> 2
		for (int i = 0;i < arr.length;i++){
			arr[i] = map.get(arr[i]);
		}
	}

}
