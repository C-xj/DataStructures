package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-05  20:28
 */

/*
*  n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     你需要按照以下要求，给这些孩子分发糖果：
        每个孩子至少分配到 1 个糖果。
        相邻两个孩子评分更高的孩子会获得更多的糖果。

    请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
*
*   思路：left数组，从左看数组，第一个为1，比前一个数大+1，相等或小于就为1
*        right数组，从右看数组，第一个为1，比前一个数大+1，相等或小于就为1
*   例如：评分是[1,3,3,2,1,4,2,2,5,2,1]
*        left [1,2,1,1,1,2,1,1,2,1,1]   表示坡度
*        right[1,1,3,2,1,2,1,1,3,2,1]   表示坡度
*       各个位置求max(坡度大的为准)就是其分的糖果
*
* 进阶：评分一样的孩子，分的糖一样；
*       left数组，从左看数组，第一个为1，比前一个数大+1，相等就继承、小于就为1
*       right数组，从右看数组，第一个为1，比前一个数大+1，相等就继承、小于就为1
* */
public class Q09_分糖果问题及进阶问法 {

    // 原始优良解，时间O(N)，空间O(N)
    public int candy1(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        for (int i = 0; i < n; i++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                left[i] = left[i - 1] + 1;
            } else {
                left[i] = 1;
            }
        }
        int right = 0, ret = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1 && ratings[i] > ratings[i + 1]) {
                right++;
            } else {
                right = 1;
            }
            ret += Math.max(left[i], right);
        }
        return ret;
    }

    // 最优解，时间O(N)，空间O(1)
    public int candy2(int[] ratings) {
        int n = ratings.length;
        int ret = 1;
        int inc = 1, dec = 0, pre = 1;
        for (int i = 1; i < n; i++) {
            if (ratings[i] >= ratings[i - 1]) {
                dec = 0;
                pre = ratings[i] == ratings[i - 1] ? 1 : pre + 1;
                ret += pre;
                inc = pre;
            } else {
                dec++;
                if (dec == inc) {
                    dec++;
                }
                ret += dec;
                pre = 1;
            }
        }
        return ret;
    }

}
