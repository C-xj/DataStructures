package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-05  16:04
 */
/*
*   返回一个数组中，子数组最大累加和
*       i位置以前的子数组最大累加和： 取两种情况最大值
*               ① arr[i] 其本身
*               ② 向左扩，与dp[i-1]扩到的位置一样(即，dp[i-1]扩到的子串 + arr[i])
*   最后返回dp中最大的数值
* */
public class Q08_子数组最大累加和 {

    // 动态规划
    public static int maxSum1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 上一步 dp的值
        int pre = arr[0];
        int max = arr[0];    // 记录最大数值
/*        for (int i = 1; i < arr.length; i++) {
            int p1 = arr[i];
            int p2 = arr[i] + pre;
            int cur = Math.max(p1, p2);
            max = Math.max(max, cur);
            pre = cur;
        }*/
        for (int i = 1; i < arr.length; i++) {
            pre = Math.max(arr[i], arr[i] + pre);
            max = Math.max(max, pre);
        }
        return max;
    }

    // 贪心算法
    public static int maxSum2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;    // 记录最大数值
        int cur = 0;            // 记录累加和
        for (int i = 0; i < arr.length; i++) {
            // 累加每项
            cur += arr[i];
            // 不断更新区间累计的最大值（相当于不断确定最大子序终止位置）
            max = Math.max(max, cur);
            // 相当于重置最大子序起始位置，因为遇到负数一定是拉低总和
            cur = cur < 0 ? 0 : cur;
        }
        return max;
    }
}
