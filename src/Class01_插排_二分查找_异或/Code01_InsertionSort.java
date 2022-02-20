package Class01_插排_二分查找_异或;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-11-12  15:47
 */
public class Code01_InsertionSort {


    // 插入排序
/*    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }*/

    // 基于二分查找的插入排序
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};

        if (arr== null || arr.length < 2){
            return;
        }
        for (int i = 1 ;i < arr.length; i++){

            // 二分查找
            int low = 0;
            int high = arr.length-1;
            int mid;
            // 最后要查找后 指针 high在low左边一位
            while (low <= high){  // 找到要插入的位置，[low，i-1]全部后移
                mid=(low + high) / 2;
                if (arr[mid] < arr[i]){
                    low = mid + 1;
                }else {
                    high = mid - 1;
                }
            }
            for(int j = i-1 ; j >= low && arr[j] > arr[j+1] ;j--){
                swap(arr,j,j+1);
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    // a ^ 0 = a ,  a ^ a = 0
    // 如果i==j,也就是在同一个内存的数字，这样就不能完成交换
    public static void swap(int[] arr ,int i ,int j){
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }




}
