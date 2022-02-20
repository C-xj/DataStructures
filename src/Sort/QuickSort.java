package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  16:17
 */
public class QuickSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};
        Quicksort(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));
        //Quicksort2(arr,0,arr.length-1);
        //System.out.println(Arrays.toString(arr));

    }

    // 单边循环 单指针快排
    /*public static void Quicksort2(int arr[],int low,int high){
        if (low<high){
            int keypos=partition2(arr,low,high);  //获取划分最后key的索引位置
            Quicksort2(arr,low,keypos-1);    // 递归划分key索引左表
            Quicksort2(arr,keypos+1,high);   // 递归划分key索引右表
        }
    }

    public static int partition2(int[] arr ,int low ,int high){
        int pivot = arr[low]; // 取第一个元素为基准
        int mark = low;
        for (int i=low+1;i<=high;i++){
            if(arr[i]<pivot){
                mark++;
                int temp=arr[mark];
                arr[mark]=arr[i];
                arr[i]=temp;
            }
        }
        // mark与一开始基准元素交换，确定新基准
        arr[low]=arr[mark];
        arr[mark]=pivot;
        return mark;
    }*/

    public static void Quicksort(int arr[],int low,int high){
        if (low<high){
            int keypos=Partition(arr,low,high);  //获取划分最后key的索引位置
            Quicksort(arr,low,keypos-1);    // 递归划分key索引左表
            Quicksort(arr,keypos+1,high);   // 递归划分key索引右表
        }
    }

    public static int Partition(int arr[],int low ,int high){
        int key=arr[low]; // 保存第一个元素作为基准
        while (low<high){
            while (low<high && arr[high]>=key){
                high--;
            }
            // 出现比key小的数，将其移到左边
            arr[low]=arr[high];  //因为arr[low]已经保存下来，可以被覆盖

            // 左右两边来回切换

            while (low<high && arr[low]<key){
                low++;
            }
            // 出现比key大的数，将其移到右边
            arr[high]=arr[low]; //因为arr[high]已经被上述arr[low]保存下来，
                                // 可以被覆盖
        }
        // 大循环的停止时是 --- low==high
        arr[low]=key;  // 把基准赋给到最终low/high的位置

        return low;  // 返回当前位置，以便后面递归分组
    }

}
