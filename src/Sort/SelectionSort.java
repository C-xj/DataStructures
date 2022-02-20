package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  12:54
 */
public class SelectionSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2};

        for (int i=0 ;i<arr.length;i++){
            int min=i;  // 临时变量 存放临时最小值的的索引
            for(int j=i+1;j< arr.length;j++){ // 从当前数的下一个开始遍历
                if(arr[j]<arr[min]){  // 遍历得到最小值的索引
                    min=j;
                }
            }
            if(min!=i){
                swap(arr,i,min); //如果遍历后 最小值的索引改变，
                        // 则交换当前i索引对应的值和遍历得到最小值索引的最小值
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void swap(int arr[],int i,int min){
        int temp =arr[i];
        arr[i]=arr[min];
        arr[min]=temp;
    }
}
