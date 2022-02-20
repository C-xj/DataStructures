package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-01-05  14:30
 */
/*
*   给定一个正数数组arr，代表若干人的体重
*   在给定一个正数limit，表示所有船共同拥有的载重量，每艘船最多坐两个人，且不能超过载重
*   想让所有的人同时过河，并且用最好的分配方法让船尽量少，返回最少的船数。
*
*   1）排序，过滤掉一个人就超过limit的人员
*
*      2）之后贪心：来到limit / 2值的位置，L在左，R在右，
*               ① 不能凑一船,L向左移动到刚好可以凑一船，其中不能凑一船的为止都记录为❌，
*               ② 然后，R向右移动到最后一个可以凑一船的位置(记录一共可以有几个)，
*               ③ 然后L向左移动对应的个数，把这些数记录已选用✔，即凑到了几艘船。
*               然后重复上述操作①②③，直到移到一个边界为止。
*           最后limit / 2 的❌统计出来 / 2 (两两可以凑一船)
*      最后船的数量：✔/2 + ❌/2(向上取整) + 右侧省几个数(单独一个船)
* */
public class Q07_救生艇_最多装两个人的船同时过河问题 {

    // 请保证arr有序
    public static int minBoat(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        Arrays.sort(arr);
        if(arr[N - 1] > limit) {    // 如果最重的一个超过了limit，表示不能全部坐船，返回-1
            return -1;
        }
        int lessR = -1;     // 记录<= limit / 2  数中最右的位置
        // 所有的人体重都不超过limit，继续讨论,  倒序找到 <= limit / 2 的最右的位置(即倒序的第一个数)
        for (int i = N  - 1; i >= 0; i--) {
            if (arr[i] <= (limit / 2)) {
                lessR = i;
                break;
            }
        }
        // lessR无变化，表示所有数都是大于limit / 2，则需要的船数为N(每个人一只船)
        if (lessR == -1) {
            return N;
        }
        //  有 <= limit / 2的数时
        int L = lessR;      // L来到此数，之后左移
        int R = lessR + 1;  // R来到次数右侧，之后右移
        int noUsed = 0; // 画X的数量统计，画对号的量(加工出来的)
        while (L >= 0) {
            int solved = 0; // 此时的[L]，让R画过了几个数
            while (R < N && arr[L] + arr[R] <= limit) { // R向右移动到最后一个可以凑一船的位置(记录一共可以有几个)
                R++;
                solved++;
            }
            // 如果，R来到又不达标的位置，则左移L，并记录当前位置为❌(无使用+1)
            if (solved == 0) {
                noUsed++;
                L--;
            } else { // 此时的[L]，让R画过了solved（>0）个数
                L = Math.max(-1, L - solved);
            }
        }
        int all = lessR + 1;// 左半区总个数  <= limit /2 的区域
        int used = all - noUsed; // 画对号√的量
        int moreUnsolved = (N - all) - used; // > limit/2 区中，没搞定的数量
        return used + ((noUsed + 1) >> 1) + moreUnsolved;
    }


    /*  力扣——救生艇  贪心
    * 一个直观的想法是：由于一个船要么载两人要么载一人，在人数给定的情况下，为了让使用的总船数最小，要当尽可能让更多船载两人，即尽可能多的构造出数量之和不超过 limit的二元组。
    *    先对 people 进行排序，然后使用两个指针 l 和 r 分别从首尾开始进行匹配：
    *
    *    如果 people[l]+people[r]<=limit，说明两者可以同船，此时船的数量加一，两个指针分别往中间靠拢；
    *    如果 people[l]+people[r]>limit，说明不能成组，由于题目确保人的重量不会超过limit，此时让people[r] 独立成船，船的数量加一，r 指针左移。
    * */
/*    class Solution {
        public int numRescueBoats(int[] people, int limit) {
            int ans = 0;
            Arrays.sort(people);
            int light = 0, heavy = people.length - 1;
            while (light <= heavy) {
                if (people[light] + people[heavy] <= limit) {
                    ++light;
                }
                --heavy;
                ++ans;
            }
            return ans;
        }
    }*/


}
