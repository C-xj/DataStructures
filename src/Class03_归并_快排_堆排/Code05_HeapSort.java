package Class03_归并_快排_堆排;

import java.util.Arrays;

// 堆排序
/*
* 1、先让整个数组都变成大根堆结构，建立堆的过程：
* 	1）从上到下的方法，时间复杂度为O(N*logN)
* 	2）从下到上的方法，时间复杂度为O(N)
* 2、把堆的最大值和堆末尾的值交换，然后减少堆的大小之后，再去调整堆，
* 	一直周而复始，时间复杂度为O(N*logN)
* 3、堆的大小减小成0之后，排序完成
* */
public class Code05_HeapSort {

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 先将数组整理成大根堆形式
/*		for (int i = 0; i < arr.length; i++) {
			heapInsert(arr, i);
		}*/

		// 优化：将数组变成大根堆，查看每个数要不要下沉（从右向左，一直保持大根堆）
		for (int i = arr.length -1 ; i >= 0; i-- ){
			heapify(arr , i ,arr.length);
		}

		int heapSize = arr.length;
		// 将最大值(即堆顶元素，换到最后，堆元素个数减一)
		swap(arr, 0, --heapSize);
		while (heapSize > 0) {
			// 如果堆中还有元素，重新整理为大根堆
			heapify(arr, 0, heapSize);
			// 循环交换堆顶和堆位的数，最后完成从小到大的排序
			swap(arr, 0, --heapSize);
		}
	}

	public static void heapInsert(int[] arr, int index) {
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) /2);
			index = (index - 1)/2 ;
		}
	}

	public static void heapify(int[] arr, int index, int heapsize) {
		int left = index * 2 + 1;
		while (left < heapsize) {
			int largest = left + 1 < heapsize && arr[left + 1] > arr[left] ? left + 1 : left;
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
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
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}

}
