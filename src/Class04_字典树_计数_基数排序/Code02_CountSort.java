package Class04_字典树_计数_基数排序;


/**
 * @author Chu
 * @create 2021-11-22  20:23
 */
// 计数排序：先创建一个最大数的数组，用来存储每个数值出现的次数(下标即是出现的数值)，最后遍历此数组，进行排序。
public class Code02_CountSort {

    public static void countSort(int[] arr){

        if(arr == null || arr.length <2){
            return;
        }
        // 找出待排数组中最大值
        int max = Integer.MIN_VALUE;
        for (int i = 0 ;i < arr.length ;i++){
            max = Math.max(max,arr[i]);
        }

        // 创建连续到最大值的数组，用来存储每个数值出现的次数，下标索引即是数值
        int[] bucket = new int[max + 1];
        for(int i = 0 ;i < arr.length ;i++){
            bucket[arr[i]]++;
        }
        // 遍历每个数，根据出现的每个数值的个数，依次重新赋值给arr，达到排序
        int i = 0;
        for (int j = 0 ;j < bucket.length ;j++){
            while(bucket[j]-- > 0){
                arr[i++] = j;
            }

        }
    }


}
