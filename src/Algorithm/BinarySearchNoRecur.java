package Algorithm;

/**
 * @author Chu
 * @create 2021-05-20  11:20
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        // 测试
        int[] arr={1,3,8,10,11,67,110};
        int index = binarySearch(arr,11);
        System.out.println("index="+index); // 0

    }

    // 二分查找的非递归实现
    /*
    * arr 待查找数组
    * target 需要查找的数
    * return 返回对应下标，-1表示没有找到
    * */
    public static int binarySearch(int [] arr, int target){
        int left=0;
        int right= arr.length-1;
        while (left<=right){
            int mid=(left+right)/2;
            if (arr[mid]==target){
                return mid;
            }else if (arr[mid]<target){
                left=mid+1;
            }else {
                right=mid-1;
            }
        }
        return -1;
    }


    /*
    * 局部最小二分查找法(一个无序序列，找到一个局部最小值)
    * */
    public static int getLessIndex(int[] arr){
        if (arr == null || arr.length == 0){
            return -1; // 不存在
        }
        if (arr.length == 1 || arr[0] < arr[1]){
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]){
            return arr.length -1;
        }
        int left = 1;
        int right = arr.length-2;
        int mid = 0;
        while (left < right){
            mid = (left + right) / 2;
            if (arr[mid] > arr[mid - 1]){
                right = mid - 1;
            }else if (arr[mid] > arr[mid + 1]){
                left = mid + 1;
            }else {
                return mid;
            }
        }
        return left;
    }
}
