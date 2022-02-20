package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-12  10:31
 */
/*
* 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
*
*   输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
    输出：6
    解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
*
*   思路方法1：
*       计算i位置上方可以存多少水，然后累加。
*           ① 比如当前位置i值的大小为5，其左边(0..i-1)最大值为17,其右边(i+1..arr.length-1)最大值为23,则其上方可以存17-5 = 12的水
*           ② 如果有一边的值没有当前值大，那么其可以存的水为0
*                   ②   ①
*       水[i] = max( 0, min( max(0..i-1),max(i+1..arr.length-1) ) - arr[i])
*           需要两个辅助数组存各个位置的左边/右边最大值
*
*
*   思路方法2：
*       双指针，最左，最右向中间移动，两边分别存一个扫过位置的最大值max，
*           两边谁的max小对应的指针就往中间扫，如果比之前的max大就替换，比之前的小则计算扫过位置的存水量。
*
* */
public class Q29_一维接雨水问题 {

    // 实现方式一：动态规划①
    public static int water1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] leftMaxs = new int[N];
        leftMaxs[0] = arr[0];
        for (int i = 1; i < N; i++) {       // 用于存当前位置左边的最大值
            leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
        }

        int[] rightMaxs = new int[N];
        rightMaxs[N - 1] = arr[N - 1];
        for (int i = N - 2; i >= 0; i--) {  // 用于存当前位置右边的最大值
            rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
        }
        int water = 0;
        for (int i = 1; i < N - 1; i++) {
            water += Math.max(Math.min(leftMaxs[i - 1], rightMaxs[i + 1]) - arr[i], 0);
        }
        return water;
    }

    // ②
    public static int water2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int[] rightMaxs = new int[N];
        rightMaxs[N - 1] = arr[N - 1];
        for (int i = N - 2; i >= 0; i--) {  // 用于存当前位置右边的最大值
            rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
        }
        int water = 0;
        int leftMax = arr[0];
        for (int i = 1; i < N - 1; i++) {   // 将两个遍历放一起，既计算了当前位置水量进行累加，也同时更新了当前位置左边的最大值
            water += Math.max(Math.min(leftMax, rightMaxs[i + 1]) - arr[i], 0);
            leftMax = Math.max(leftMax, arr[i]);
        }
        return water;
    }

    // 实现方式二：双指针
    public static int water3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int L = 1;              // 因为索引0位置的左边为0，存不住水，从1开始
        int leftMax = arr[0];
        int R = N - 2;          // 因为索引N - 1位置的右边为0，存不住水，从N - 2开始
        int rightMax = arr[N - 1];
        int water = 0;
        while (L <= R) {
            if (leftMax <= rightMax) {
                water += Math.max(0, leftMax - arr[L]);
                leftMax = Math.max(leftMax, arr[L++]);
            } else {
                water += Math.max(0, rightMax - arr[R]);
                rightMax = Math.max(rightMax, arr[R--]);
            }
        }
        return water;
    }

}
