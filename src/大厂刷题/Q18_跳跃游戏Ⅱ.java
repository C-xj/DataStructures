package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-09  20:47
 */
/*
* 给你一个非负整数数组nums ，你最初位于数组的第一个位置。

    数组中的每个元素代表你在该位置可以跳跃的最大长度。

    你的目标是使用最少的跳跃次数到达数组的最后一个位置。

    假设你总是可以到达数组的最后一个位置。
* */
public class Q18_跳跃游戏Ⅱ {


    public int jump(int[] nums) {
        if(nums.length == 1) {
            return 0;
        }
        int len = nums.length;
        int ans = 0;    // 最远可到达的索引位置
        int step = 0;   // 记录走的步数
        int end = 0;    // 记录上一步 step 跳的最远距离

        // 在遍历数组时，我们不访问最后一个元素，这是因为在访问最后一个元素之前，我们的边界一定大于等于最后一个位置，否则就无法跳到最后一个位置了。如果访问最后一个元素，在边界正好为最后一个位置的情况下，我们会增加一次「不必要的跳跃次数」，因此我们不必访问最后一个元素。
        for(int i = 0;i < len - 1; i++) {  // i表示当前位置
            ans = Math.max(ans,i + nums[i]);

            if(i == end){
                step++;
                end = ans;
            }
        }
        // 此贪心，end记录的是前一步最远
        /*for(int i = 0;i < len ; i++) {  // i表示当前位置

            if(end < i){
                step++;
                end = ans;
            }
            ans = Math.max(ans,i + nums[i]);
        }*/

        return step;
    }

}
