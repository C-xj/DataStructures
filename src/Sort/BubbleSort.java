package Sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-08  10:40
 */
public class BubbleSort {
    public static void main(String[] args) {
        int arr[]={3,9,-1,10,-2};

        /*int temp=0; // 临时变量
        for(int i=0;i<=arr.length-1;i++){
            for (int j=0;j< arr.length-1-i;j++){
                if(arr[j]<arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));*/

        int temp=0; // 临时变量
        boolean flag=false; // 标识变量，表示是否进行过交换

        for(int i= arr.length-1;i>=0;i--){

            for (int j=0;j< i;j++){
                // 如果前面比后面的小则交换
                if(arr[j]>arr[j+1]){
                    flag=true; // 进入过交换则把标识变量赋值为true

                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
            if(flag==false){ // 在此趟排序中一次交换都没有发生过
                break;
            }else {
                flag=false; // 如果交换，重置flag，进行下趟判断是否交换
            }

        }
        System.out.println(Arrays.toString(arr));

    }
}
