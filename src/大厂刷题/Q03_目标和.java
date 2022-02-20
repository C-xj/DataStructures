package 大厂刷题;

import java.util.HashMap;

/**
 * @author Chu
 * @create 2022-01-04  19:01
 */
/* 题目7
 *     给定一个数组arr，可以在每个数字之前决定+或者-，但是必须所有数字都参与
 *        再给定一个数target，请问最后算出target的方法数是多少？
 * */
public class Q03_目标和 {

    /*  方法一：暴力递归
    *       可以自由使用arr[index......]所有的数字
    *       算出rest这个数，方法数是多少？并返回
    * */
    public static int findTargetSumWays1(int[] arr, int s) {
        return process1(arr, 0, s);
    }

    public static int process1(int[] arr, int index, int rest) {
        if (index == arr.length) {      // 后面没数可做选择了，剩余值为0，则是一种方法
            return rest == 0 ? 1 : 0;
        }
        // 返回当前数选+或-两种情况
        return process1(arr, index + 1, rest - arr[index])
                + process1(arr, index + 1, rest + arr[index]);
    }

    /*  方法二：记忆化搜索(傻缓存)
     *     HashMap<Integer, HashMap<Integer, Integer>> dp
     *          index + rest
     *     例如：index == 7 rest = 13 情况 25种方法
     *          index == 7 rest = 35 情况 17种方法
     *      {
     *              7 : { 13, 25}
     *                  { 35, 17}
     *      }
     * */
    public static int findTargetSumWays2(int[] arr, int s) {
        return process2(arr, 0, s, new HashMap<>());
    }

    public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
        if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
            return dp.get(index).get(rest);
        }
        int ans = 0;
        if (index == arr.length) {
            ans = rest == 0 ? 1 : 0;
        } else {
            ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
        }
        if (!dp.containsKey(index)) {
            dp.put(index, new HashMap<>());
        }
        dp.get(index).put(rest, ans);
        return ans;
    }

    // 优化点一 :
    // 你可以认为arr中都是非负数
    // 因为即便是arr中有负数，比如[3,-4,2]
    // 因为你能在每个数前面用+或者-号
    // 所以[3,-4,2]其实和[3,4,2]达成一样的效果
    // 那么我们就全把arr变成非负数，不会影响结果的

    // 优化点二 :
    // 如果arr都是非负数，并且所有数的累加和是sum
    // 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0

    // 优化点三 :
    // arr内部的数组，不管怎么+和-，最终的结果都一定不会改变奇偶性
    // 所以，如果所有数的累加和是sum，
    // 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0

    // 优化点四 :
    // 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
    // 其中一个方案是 : +1 -2 +3 -4 +5 = 3
    // 该方案中取了正的集合为P = {1，3，5}
    // 该方案中取了负的集合为N = {2，4}
    // 所以任何一种方案，都一定有 sum(P) - sum(N) = target
    // 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
    // sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
    // 2 * sum(P) = target + 数组所有数的累加和
    // sum(P) = (target + 数组所有数的累加和) / 2
    // 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
    // 那么就一定对应一种target的方式
    // 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
    // 求有多少方法组成7，其实就是求有多少种达到累加和(7+11)/2=9的方法

    // 优化点五 :
    // 二维动态规划的空间压缩技巧
    public static int findTargetSumWays3(int[] arr, int target) {
        int sum = 0;
        for (int n : arr) {
            sum += n;
        }
        //        优化二                   优化三                             优化四(转为经典背包动态规划)
        return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset(arr, (target + sum) >> 1);
    }

    // 经典背包问题
    /*
    * 由于dp 的每一行的计算只和上一行有关，因此可以使用滚动数组的方式，去掉dp的第一个维度，将空间复杂度优化到 O(neg)。
    * 实现时，内层循环需采用倒序遍历的方式，这种方式保证转移来的是 dp[i−1][] 中的元素值。
    * */
    public static int subset(int[] nums, int s) {
        // 优化五：一个数组标识二维数组
        int[] dp = new int[s + 1];
        dp[0] = 1;
        for (int n : nums) {
            for (int i = s; i >= n; i--) {
                dp[i] += dp[i - n];
            }
        }
        return dp[s];
    }


    // 二维动态规划 其中dp[i][j] 表示在数组 nums 的前 i个数中选取元素，使得这些元素之和等于 j 的方案数。
/*    p[i][j]={   dp[i−1][j],                       j<nums[i]
                  dp[i−1][j]+dp[i−1][j−nums[i]],    j≥nums[i]*/
/*    class Solution {
        public int findTargetSumWays(int[] nums, int target) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            int diff = sum - target;
            if (diff < 0 || diff % 2 != 0) {
                return 0;
            }
            int n = nums.length, neg = diff / 2;
            int[][] dp = new int[n + 1][neg + 1];
            dp[0][0] = 1;
            for (int i = 1; i <= n; i++) {
                int num = nums[i - 1];
                for (int j = 0; j <= neg; j++) {
                    dp[i][j] = dp[i - 1][j];
                    if (j >= num) {
                        dp[i][j] += dp[i - 1][j - num];
                    }
                }
            }
            return dp[n][neg];
        }
    }*/

}
