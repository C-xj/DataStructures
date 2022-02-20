package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-02-15  13:49
 */
/*
* 两地调度问题---同类型题：
*   公司计划面试 2n 人。给你一个数组 costs ，其中 costs[i] = [aCosti, bCosti] 。第 i 人飞往 a 市的费用为 aCosti ，飞往 b 市的费用为 bCosti 。
        返回将每个人都飞到 a 、b 中某座城市的最低费用，要求每个城市都有 n 人抵达。

* 思路：
*   我们这样来看这个问题，公司首先将这 2N 个人全都安排飞往 B 市，再选出 N 个人改变它们的行程，让他们飞往 A 市。如果选择改变一个人的行程，那么公司将会额外付出 price_A - price_B 的费用，这个费用可正可负。

      因此最优的方案是，选出 price_A - price_B 最小的 N个人，让他们飞往 A 市，其余人飞往 B 市。

        算法：
            按照 price_A - price_B 从小到大排序；
            将前 N 个人飞往 A 市，其余人飞往 B 市，并计算出总费用。


* 来自小红书
* 魔法石头过河问题：
*   [0,4,7]： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
*   [1,X,X]： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
*   [2,X,X]： 1表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
*   颜色只可能是0，1，2，代价一定>=0
*   给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
*   如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
*
*       贪心：
*           ① 将没有颜色的石头的个数统计出来n(为了使两种颜色相等，其中 红a，蓝b; a+b=n)，并记录两种颜色代价差值value，
*           ② 将没有颜色石头n全部转成红色，得到总代价cost。
*           ③ 然后cost - 从大到小排序value(b个)    ---即以最小的代价将b个转为蓝色(换种说法就是这b个是转红色代价最高的，因此前面转为红色剩下的的就是以最小代价将a个转为红色)
*
* */
public class Q54_1029_魔法石头过河问题 {


    // 两地调度问题：
    class Solution {
        public int twoCitySchedCost(int[][] costs) {
            int res = 0;
            int[] temp = new int[costs.length];

            for (int i = 0; i < costs.length; i++) {
                temp[i] = costs[i][1] - costs[i][0]; //计算人员到A城与到B城的消耗差
                res += costs[i][0];     //加上所有人到A城的费用
            }
            Arrays.sort(temp);
            for (int i = 0; i < temp.length/2; i++) {
                res += temp[i];     //减去应到B城的人员的额外消耗
            }

            return res;
        }
    }


}


