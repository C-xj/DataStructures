package search;

import java.util.ArrayList;

/**
 * @author Chu
 * @create 2021-05-10  10:55
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {1,3,13,35,66,88,99,99,99,996}; // 注意：折半查找必须是顺序表

        // 调用递归方法
        //int index = binarysEARCH(arr, 0, arr.length - 1, 66);


        // 调用递归（数组中重复元素）的方法
        ArrayList list=binarysEARCH2(arr, 0, arr.length - 1, 99);
        System.out.println(list);

        /*// 调用普通方法
        int index = binarySearch(arr, 66);
        if (index == -1) {
            System.out.println("查找失败");
        } else {
            System.out.println("查找到" + arr[index]);
        }*/

    }

    // 不使用递归
    public static int binarySearch(int arr[], int vaule) {
        int low = 0;
        int high = arr.length - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arr[mid] == vaule) {
                return mid;         // 查找成功则返回所在位置
            } else if (arr[mid] > vaule) {
                high = mid - 1;         // 从前半部分继续查找
            } else {
                low = mid + 1;          // 从后半部分继续查找
            }
        }
        return -1;                  // 查找失败
    }

    // 使用递归
    public static int binarysEARCH(int arr[],int low,int high,int vaule){
        int mid=(low+high)/2;

        if (vaule==arr[mid]) {
            return mid;
        } else if (vaule<arr[mid]){        // 向前半部分递归
            return binarysEARCH(arr,low,mid-1,vaule);
        }else {                           // 向后半部分递归
            return binarysEARCH(arr,mid+1,high,vaule);
        }
    }


    //完成一个课后思考题
    /* 返回数组中所有value值对应的索引（用于应对出现重复的情况）
    * 思路分析
    * 1、在找到mid索引值时，不要马上返回
    * 2、向mid索引值的左边扫描，将所有满足的value的元素下标，加入到集合ArrayList
    * 3、向mid索引值的右边扫描，将所有满足的value的元素下标，加入到集合ArrayList
    * 4、将ArrayList返回
    * */
    public static ArrayList binarysEARCH2(int arr[], int low, int high, int vaule){
        int mid=(low+high)/2;

        if (vaule==arr[mid]) {
            ArrayList<Integer> resIndexList = new ArrayList<>();

            //向mid索引值的左边扫描，将所有满足的value的元素下标，加入到集合ArrayList
            int temp=mid-1;
            while (true){
                if(temp<0 || arr[temp]!=vaule){ // 退出
                    break;
                }
                // 否则，就把temp放到resIndexList集合中
                resIndexList.add(temp);
                temp--;    // temp向左移
            }
            resIndexList.add(mid); // 放入中间部分

            temp=mid+1;
            while (true){
                if(temp>arr.length-1 || arr[temp]!=vaule){ // 退出
                    break;
                }
                // 否则，就把temp放到resIndexList集合中
                resIndexList.add(temp);
                temp++;    // temp向右移
            }

            return resIndexList;


        } else if (vaule<arr[mid]){        // 向前半部分递归
            return binarysEARCH2(arr,low,mid-1,vaule);
        }else {                           // 向后半部分递归
            return binarysEARCH2(arr,mid+1,high,vaule);
        }
    }



}
