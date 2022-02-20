package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-14  13:35
 */
/*
* 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。
*       同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
            给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
* */
public class Q47_213_打家劫舍Ⅱ {

    public static int rob(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        // 情况1:第一家被抢，最后一家不被抢0..n-2
        int pre2 = nums[0];
        int pre1 = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length - 1; i++) {
            int tmp = Math.max(pre1, nums[i] + pre2);
            pre2 = pre1;
            pre1 = tmp;
        }
        // 情况2:第一家不被抢，最后一家被抢1..n-1
        int ans1 = pre1;
        pre2 = nums[1];
        pre1 = Math.max(nums[1], nums[2]);
        for (int i = 3; i < nums.length; i++) {
            int tmp = Math.max(pre1, nums[i] + pre2);
            pre2 = pre1;
            pre1 = tmp;
        }
        int ans2 = pre1;
        return Math.max(ans1, ans2);
    }

}
