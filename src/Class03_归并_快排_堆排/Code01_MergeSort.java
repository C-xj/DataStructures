package Class03_归并_快排_堆排;

import java.util.Arrays;

public class Code01_MergeSort {

	// 递归方法实现：归并排序1
	public static void mergeSort1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上，变成有序
	public static void process(int[] arr, int l, int r) {
		if (l == r) {
			return;
		}
		int mid = l + ((r - l) >> 1);
		process(arr, l, mid);
		process(arr, mid + 1, r);
		merge(arr, l, mid, r);
	}

	public static void merge(int[] arr, int l, int m, int r) {
		int[] help = new int[r - l + 1];
		int i = 0;
		int p1 = l;
		int p2 = m + 1;
		while (p1 <= m && p2 <= r) {
			help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= m) {
			help[i++] = arr[p1++];
		}
		while (p2 <= r) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}

	/*
	 * arr：排序的原始数组
	 * low:初始索引
	 *  mid：中间索引
	 *  high：最后索引
	 *   temp：中转数组
	 * */
/*	public static void merge(int arr[] ,int low,int mid ,int high,int temp[]){
		int i,j,k; // i（low）:左边有序序列的初始索引
		// j（mid+1）：右边有序序列的初始索引
		// k：指向temp数组的当前索引
		for (k=low;k<=high;k++){  //把原数组的元素复制到temp数组中
			temp[k]=arr[k];
		}
		for (i=low,j=mid+1,k=i;i<=mid && j<=high ;k++){
			if (temp[i]<temp[j]){
				arr[k]=temp[i++];  // 将较小的值重新赋给arr数组
			}else {
				arr[k]=temp[j++];
			}
		}
		// for循环结束是由于其中一个子序列归并完，故需要把另一个剩余部分归并到尾部
		while (i<=mid) arr[k++]=temp[i++];
		while (j<=high) arr[k++]=temp[j++];
	}*/


	// 非递归方法实现：归并排序2
	// 		分组排序，分组的值越来越大,mergeSize = 1, 2, 4, ...
	public static void mergeSort2(int[] arr){
		if (arr == null || arr.length < 2){
			return;
		}
		int N = arr.length;
		int mergeSize = 1;
		while (mergeSize < N){
			int L = 0;
			while (L  < N){
				int M = L + mergeSize-1;
				if (M >= N){		// 最后省的几个数没有右组
					break;
				}
				int R = Math.min(M + mergeSize,N - 1);	// 有可能最后右组不够对应长度，因此需要取最小的
				merge(arr,L,M,R);
				L = R + 1;		// L跑到下一组的第一个位置
			}
			if (mergeSize > N / 2){		// mergeSize每次乘2，因此要小于整个数组长度
				break;
			}
			mergeSize <<= 1;
		}
	}


	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			mergeSort1(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		mergeSort1(arr);
		printArray(arr);

	}

}
