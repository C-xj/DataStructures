package Class13_暴力递归到动态规划;

/**
 * @author Chu
 * @create 2021-12-22  14:40
 */
/* 常见的4种尝试模型
 *   1）从左往右的尝试模型
 *   1）范围上的尝试模型
 *   1）多样本位制全对应的尝试模型
 *   4）寻找业务限制的尝试模型
 * */

/* 从左往右的尝试模型2 --- 背包问题
 * 		给定两个长度都为N的数组weights和values，
 * 		weights[i]和values[i]分别代表i号物品的重量和价值。
 * 		给定一个正数bag，表示一个载重bag的袋子，
 * 		你装的物品不能超过这个重量。
 * 		返回你能装下最多的价值是多少？
 *
 * 	要当前货，不要当前货两种选择
 * */
public class Code03_Knapsack动态规划 {

    // 暴力递归
    private static int process(int[] w, int[] v, int index, int alreadyW, int bag) {
        if (alreadyW > bag) {
            return -1;		// 返回-1表示没有方案，认为返回的值是真实价值
        }
        // 重量没超，但是没货了，方案有效，后续价值为0
        if (index == w.length) {
            return 0;
        }

        // 不要
        int p1 = process(w, v, index + 1, alreadyW, bag);

        // 想要
        int p2next = process(w, v, index + 1, alreadyW + w[index], bag);

        // 可能这个东西要之后超了总容量，所以要有判断
        int p2 = -1;
        if (p2next != -1) {				//  成功要了后面的货
            p2 = v[index] + p2next;		//  要当前货总价值=当前货的价值 + 要了当前货后面货的总价值
        }
        return Math.max(p1, p2);		// 返回两种可能的最大值
    }

    // 动态规划
    public static int dpWay(int[] w,int[] v,int bag){
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        // dp[N][...] = 0 初始化默认已经是了，不用重新设置
        // 从下面的行依次推导上面的行
        for (int index = N - 1;index >= 0;index--){
            // 剩余空间从1到bag
            for(int rest = 0;rest <= bag;rest++){
               /* 写法方式一：
                // 不要这个物品
                int p1 = dp[index+1][rest];
                int p2 = -1;
                // 可以要这个物品
                if (rest - w[index] >= 0){
                    p2 = v[index] + dp[index+1][rest - w[index]];
                }
                dp[index][rest] = Math.max(p1,p2);*/

                // 写法方式二：
                dp[index][rest] = dp[index + 1][rest];  //不要当前物品
                if (rest >= w[index] ){  // 要或不要的最大值
                    dp[index][rest] = Math.max(dp[index][rest],v[index] + dp[index+1][rest - w[index]]);
                }
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args){
        int[] weights = {3,2,4,7};
        int[] values = {5,6,3,19};
        int bag = 11;
        System.out.println(process(weights,values,0,0,bag));
        System.out.println(dpWay(weights,values,bag));
    }

}
