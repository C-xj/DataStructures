package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  13:37
 */
public class InsertionSort {
/*    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};

        int i,j,temp;

        for( i=1;i< arr.length;i++){
            if(arr[i]<arr[i-1]){ //前有序表的最后一个元素
                // 大于后一个无序表中元素，要把此元素插入到前面有序表，使其依然有序
                temp=arr[i]; // 后续需要移位，故A[i]会被破坏，要暂存A[i]
                for (j=i-1;j>=0 && arr[j]>temp;j--){ //有序表从后往前遍历
                    arr[j+1]= arr[j]; // 所有大于temp的元素都后移一位
                }
                arr[j+1]=temp; // 复制到插入位置
                        // 如果在for循环中声明j，此操作就会出错
            }
        }
        System.out.println(Arrays.toString(arr));
    }*/

/*    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};

        if (arr== null || arr.length < 2){
            return;
        }
        for (int i =1 ;i < arr.length; i++){
            for(int j = i-1 ; j >= 0 && arr[j] > arr[j+1] ;j--){
                swap(arr,j,j+1);
            }
        }
        System.out.println(Arrays.toString(arr));
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
