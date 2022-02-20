package Sort;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-09  9:53
 */
public class RadixSort {
    public static void main(String[] args) {
        int arr[]={53,3,542,748,14,214};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    // 基数排序
    public static void radixSort(int[] arr){

        // 1、得到数组中最大数的位数
        int max=arr[0]; // 假定第一个数就是最大的数
        for (int i=1;i< arr.length;i++){
            if (arr[i]>max){
                max=arr[i];
            }
        }
        // 得到最大数是几位数
        int maxLength=(max + "").length();

        //定义一个二维数组，表示10个桶，每个桶就是一个一维数组
        // 说明
        // 1、二维数组包含10个一维数组
        // 2、防止在放入数的时候，数据溢出，每个一维数组（桶），大小定为arr.length
        // 3、故可以看出，基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际存放多少数据，定义一个一维数组来记录每个桶的数据个数
        // 例如 bucketElementCounts[0]记录就是bucket[0]桶中数据个数
        int[] bucketElementCounts = new int[10];


        // 使用循环处理
        for (int i=0,n=1 ;i<maxLength;i++,n*=10){ // 数取模用到 n
            // 针对每个元素对应的位进行排序，个、十、百。。。
            for (int j=0 ;j< arr.length;j++){
                // 去除每个元素的对应位的值
                int digitofElement = arr[j] / n % 10;
                // 放入到对应的桶中   bucketElementCounts[digitofElement]默认0，即桶下标0存第一个数据
                bucket[digitofElement][bucketElementCounts[digitofElement]] = arr[j];
                bucketElementCounts[digitofElement]++;   // 记录的是每个桶存数据的个数
            }

            // 按照桶的顺序（一维数组的下标依次取出数据，放入原来数组）
            int index = 0;
            // 遍历每一个桶，并将桶中数据，放回原数组
            for(int k=0 ;k< bucketElementCounts.length;k++){
                //如果桶中有数据，我们才放入到原数组
                if (bucketElementCounts[k]!=0){  // 不为0，即存入了和数据
                    //循环该桶即第k个桶（即第k个一维数组）
                    for (int l=0;l< bucketElementCounts[k];l++){
                        // 取出元素放入到arr
                        arr[index++]=bucket[k][l];
                    }
                }
                // 每一轮处理后，要把每个桶中存放数据的个数置零，bucketElementCounts[k]=0 ！！！
                bucketElementCounts[k]=0;
            }
        }
    }
}
