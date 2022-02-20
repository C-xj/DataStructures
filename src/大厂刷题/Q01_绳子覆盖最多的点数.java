package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-04  16:30
 */
/* 题目1：
*       给定一个有序数组arr，代表坐落在X轴上的点，给定一个整数L，代表绳子的长度
*          返回绳子最多压中几个点，即使绳子边缘处盖住点也算
*
* */
public class Q01_绳子覆盖最多的点数 {

    /**
     * 方法一：暴力方法
     * 以数组每个元素开头 往后遍历，直到不能覆盖的那个元素为止
     * 然后在遍历下一个元素
     */
    public static int test(int[] arr, int L) {
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            int curPoint = arr[i];
            int point = 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] - curPoint <= L) {
                    point++;
                }
            }
            max = Math.max(max, point);
        }
        return max;
    }



    /**
     * 方法二：二分查找
     *      数组中，前i个数，以最后一个数为绳子结尾，其前面最多覆盖的点数
     */
    public static int maxPoint1(int[] arr, int L) {
        // res记录覆盖点数量的最大值
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            int index = nearestIndex(arr, i, arr[i] - L);
            res = Math.max(res, i - index + 1);
        }
        return res;
    }

    /**
     * 2 7 13 18 20 32
     * 查找范围在 0 .. R 上 大于等于value的最小值 的索引
     */
    private static int nearestIndex(int[] arr, int R, int value) {
        int index = R;
        int L = 0;
        while (L <= R) {
            int mid = L + ((R - L) >> 1);
            if (arr[mid] >= value) {
                R = mid - 1;
                index = mid;
            } else if(arr[mid] < value) {
                L = mid + 1;
            }
        }
        return index;
    }

    /**
     * 方法三：滑动窗口
     *          两个指针，固定一个，移动另一个到超过绳长，记录期间覆盖的点的数量
     */
    public static int maxPoint2(int[] arr, int L) {
        int res = 0;
        int left = 0;
        int right = 0;
        int N = arr.length;
        while (L < N && right < N) {
            while (right < N && arr[right] - arr[left] <= L) {
                right++;
            }
            res = Math.max(res, right - left++);
        }
        return res;
    }


    public static void main(String[] args) {
        int[] arr = {2, 7, 13, 15, 16, 17, 18, 22, 32};
        System.out.println(test(arr, 8));
        System.out.println(maxPoint1(arr, 8));
        System.out.println(maxPoint2(arr, 8));
    }

}
