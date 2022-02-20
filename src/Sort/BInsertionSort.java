package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  14:16
 */
public class BInsertionSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2};

        int i,j,temp,low,high,mid;

        for( i=1;i< arr.length;i++){
            if(arr[i]<arr[i-1]){ //前有序表的最后一个元素
                // 大于后一个无序表中元素，要把此元素插入到前面有序表，使其依然有序
                temp=arr[i]; // 后续需要移位，故A[i]会被破坏，要暂存A[i]

                low=0;
                high=arr.length-1;
                                    // 最后要查找后 指针 high在low左边一位
                while (low<=high){  // 找到要插入的位置，[low，i-1]全部后移
                    mid=(low+high)/2;
                    if (arr[mid]<temp){
                        low=mid+1;
                    }else {
                        high=mid-1;
                    }
                }

                for (j=i-1;j>=low ;j--){ //有序表从后往前遍历 low==high+1
                    arr[j+1]= arr[j]; // 所有大于temp的元素都后移一位
                }
                arr[j+1]=temp; // 复制到插入位置
                // 如果在for循环中声明j，此操作就会出错
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
