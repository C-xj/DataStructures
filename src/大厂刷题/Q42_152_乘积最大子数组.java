package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  15:43
 */
/*
* 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
*
* 以某个位置为结尾的往左推多远的子数组的最大累乘积curmax和最小累乘积curmin，求max
*
*   最大累乘积curmax：
*   假设来到第i的位置，
*       ① i为正数，第i的位置的数是此时的最大累乘积
*       ② i为正数，i-1位置时的最大累乘积curmax * i位置的数
*       ③ i为负数，i-1位置时的最小累乘积curmin * i位置的数
*
*   最小累乘积curmin：
*   假设来到第i的位置，
*       ① i为负数，第i的位置的数是此时的最小累乘积
*       ② i为负数，i-1位置时的最大累乘积curmax * i位置的数
*       ③ i为正数，i-1位置时的最小累乘积curmin * i位置的数
*
*
* */
public class Q42_152_乘积最大子数组 {

    public static int maxProduct(int[] nums) {
        int ans = nums[0];
        int min = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int curmin = Math.min(nums[i], Math.min(min * nums[i], max * nums[i]));
            int curmax = Math.max(nums[i], Math.max(min * nums[i], max * nums[i]));
            min = curmin;
            max = curmax;
            ans  = Math.max(ans, max);
        }
        return ans;
    }

}
