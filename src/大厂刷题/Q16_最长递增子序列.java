package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-07  15:55
 */
/*
* 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
*    子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。
*    例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
*
*   输入：nums = [10,9,2,5,3,7,101,18]
*   输出：4
*   解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
*
*   动态规划O(N^2):
*           dp[i] 表示0...i最长递增子序列；
*           动态方程：dp[i] = i位置之前的所有比i位置值小的数中，谁的dp值大就再此基础上 + 1
*                 最后取dp中最大值
*   最优解O(N*logN)：
*
*       d[i]数组 表示目前为止发现所有长度为i+1(因为i从0开始)的递增子序列中最小结尾
*
*       思路：我们依次遍历数组 nums 中的每个元素，并更新数组 d 和 len 的值。如果 nums[i]>d[len] 则更新 len = len + 1，
*               否则在 d[1…len]中找满足 d[i−1]<nums[j]<d[i] 的下标 i，并更新 d[i] = nums[j]。

                根据 d 数组的单调性，我们可以使用二分查找寻找下标 i，优化时间复杂度。

        最后整个算法流程为：
            设当前已求出的最长上升子序列的长度为len（初始时为 1），从前往后遍历数组nums，在遍历到nums[i] 时：
            如果nums[i]>d[len] ，则直接加入到 d 数组末尾，并更新 len=len+1；
            否则，在 d 数组中二分查找，找到第一个比 nums[i] 小的数 d[k] ，并更新d[k+1]=nums[i]。


* */
public class Q16_最长递增子序列 {

    // O(N^2)
    public int lengthOfLIS1(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int max = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    // O(N*logN)
    public int lengthOfLIS2(int[] nums) {
        int len = 1, n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[] d = new int[n + 1];
        d[len] = nums[0];
        for (int i = 1; i < n; ++i) {
            if (nums[i] > d[len]) {
                d[++len] = nums[i];
            } else {
                int l = 1, r = len, pos = 0; // 如果找不到说明所有的数都比 nums[i] 大，此时要更新 d[1]，所以这里将 pos 设为 0
                while (l <= r) {
                    int mid = (l + r) >> 1;
                    if (d[mid] < nums[i]) {
                        pos = mid;
                        l = mid + 1;
                    } else {
                        r = mid - 1;
                    }
                }
                d[pos + 1] = nums[i];
            }
        }
        return len;
    }

}
