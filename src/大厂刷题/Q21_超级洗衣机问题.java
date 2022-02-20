package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-01-10  15:43
 */
/*
* 假设有 n台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的。

    在每一步操作中，你可以选择任意 m (1 <= m <= n) 台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机。

    给定一个整数数组machines 代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的 最少的操作步数 。如果不能使每台洗衣机中衣物的数量相等，则返回 -1 。

*   输入：machines = [1,0,5]
    输出：3
        解释：
        第一步:    1     0 <-- 5    =>    1     1     4
        第二步:    1 <-- 1 <-- 4    =>    2     1     3
        第三步:    2     1 <-- 3    =>    2     2     2

*
*               (！！！以单点去求答案，然后抉择最后答案！！！)
*
*   情况一：假设1、  来到i位置洗衣机，i之前的洗衣机衣服总数少15件，i之后的洗衣机衣服总数多10件，
*                   那么要到平衡最少15轮(i之后的洗衣机给i时，i给i之前的洗衣机)
*          或者2、  i之前多，i之后少
*
*           结论： 如果i之前欠a件，i之后多b件，
*                       至少Math.max(a,b);
*   情况二：    如果i之前多a件，i之后多b件
*                       至少Math.max(a,b);
*   情况三：    如果i之前欠a件，i之后多欠件
*                       至少 a + b;
*
*   算出每个位置最少需要多少轮之后，取最大值就是结果。
*
* */
public class Q21_超级洗衣机问题 {

    // 方法一：分三组
    public static int findMinMoves(int[] arr){
        if (arr == null || arr.length == 0){
            return 0;
        }
        int size = arr.length;
        int sum = 0;        // 统计总共的衣物
        for (int i = 0; i < size;i++){
            sum += arr[i];
        }
        if (sum % size != 0){   // 不能完全整除则表示没有结果
            return -1;
        }
        int avg = sum / size;   // 每个洗衣机应有的件数
        int leftSum = 0;        // i之前的总量
        int ans= 0;         // 记录各位置需要轮数比较后的最大值
        for (int i = 0;i < arr.length;i++){
            int leftRest = leftSum - i * avg;   // i之前多或者欠的件数
            int rightRest = (sum - leftSum - arr[i]) - (size - i - 1) * avg;  // i之后多或者欠的件数
            // 情况三
            if (leftRest < 0 && rightRest < 0){
                ans = Math.max(ans,Math.abs(leftRest) + Math.abs(rightRest));
            }else {     // 情况一、二
                ans = Math.max(ans,Math.max(Math.abs(leftRest),Math.abs(rightRest)));
            }
            // 加上当前位置的件数，准备开始下一位置的判断
            leftSum += arr[i];
        }
        return ans;
    }


    /* 方法二：分两组
    *       首先，每台洗衣机平均值avg，每台洗衣机衣服欠/多的件数为num = 当前位置的件数 - avg
    *           将前i台洗衣机看成一组，记作A，其余洗衣机看成另一组，记作B。
    *           sum记录前i台洗衣机总共欠/多的件数。
    *
    *       分两种情况讨论：
    *           1、A与B两组之间的衣服，最多需要max(0-i) Math.abs(sum)移动
    *           2、组内的某一台洗衣机内的衣服数量过多，需要向左右两侧移出衣服，这最多需要max(0-i)的num
    *
    *       取两者的最大值即为答案，存入ans中，不断对比更新。
    *
    * */
    public int findMinMoves2(int[] machines) {
        int tot = Arrays.stream(machines).sum();
        int n = machines.length;
        if (tot % n != 0) {
            return -1;
        }
        int avg = tot / n;
        int ans = 0, sum = 0;
        for (int num : machines) {
            num -= avg;
            sum += num;
            ans = Math.max(ans, Math.max(Math.abs(sum), num));
        }
        return ans;
    }




}
