package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-02-15  16:15
 */
/*来自腾讯
*   给定一个数组arr，当拿走某个数a的时候，其他所有的数都+a
*   请返回最终所有数都拿走的最大分数
*
*   贪心：
*       ① 一共n个数，排序后，
*       ② 先拿走最大的a
*       ③ 重复②，直到所有数拿出，
* */
public class Q56_选择拿取方式获得最大分数 {

    // 最优解
    public static int pick(int[] arr){
        Arrays.sort(arr);
        int ans = 0;
        for (int i = arr.length - 1;i >= 0;i--){
            ans = (ans << 1) + arr[i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int  arr[]= new  int []{ 1 , 2 , 3 , 4 , 5 };
        System.out.println(pick(arr));
    }

}
