package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  15:13
 */


// 交换式---希尔排序 ---先分组后冒泡
/*public class ShellSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};

        int i,j,d,temp;  // d表示步长
        for(d=arr.length/2;d>=1;d/=2){  // 初始的步长为数组的一半，后面不断减半
            for (i=d;i< arr.length;i++){ //d+1从子表的第二个开始比较插入
                // 遍历各组中所有的元素，步长为d
                for (j=i-d;j>=0 ;j-=d){
                    // 如果当前元素大于加上步长后的那个元素，说明交换
                    if(arr[j]>arr[j+d]){
                        temp=arr[j];
                        arr[j]=arr[j+d];
                        arr[j+d]=temp;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}*/

// 移动式---希尔排序 先分组后直插
public class ShellSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2,5,6,2,5,7,8};

        int i,j,d,temp;  // d表示步长
        for(d=arr.length/2;d>=1;d/=2){  // 初始的步长为数组的一半，后面不断减半
            for (i=d;i< arr.length;i++){ //d+1从子表的第二个开始比较插入
                if (arr[i]<arr[i-d]){       // i++是不断切换子表操作
                    temp=arr[i];            // 直接插入排序
                    for (j=i-d;j>=0 && arr[j]>temp;j-=d){
                        arr[j+d]=arr[j];
                    }
                    arr[j+d]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
