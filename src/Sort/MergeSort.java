package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  21:18
 */
public class MergeSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};
        int[] temp = new int[arr.length];
        Mergesort(arr,0,arr.length-1,temp);

        System.out.println(Arrays.toString(arr));

    }

    public static void Mergesort(int arr[],int low,int high,int temp[]){
        if (low<high){
            int mid=(low+high)/2;                 // 从中间划分
            Mergesort(arr,low,mid,temp);         // 递归对左半部分划分归并排序
            Mergesort(arr,mid+1,high,temp); // 递归对右半部分划分归并排序
            Merge(arr,low,mid,high,temp);       //  把当前划分后的元素，归并排序
        }
    }

    /*
    * arr：排序的原始数组
    * low:初始索引
    *  mid：中间索引
    *  high：最后索引
    *   temp：中转数组
    * */
    public static void Merge(int arr[] ,int low,int mid ,int high,int temp[]){
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
    }
}
