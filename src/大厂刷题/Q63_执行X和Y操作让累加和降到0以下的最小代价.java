package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-02-17  10:39
 */
// 来自微软面试
// 给定一个正数数组arr长度为n、正数x、正数y
// 你的目标是让arr整体的累加和<=0
// 你可以对数组中的数num执行以下三种操作中的一种，且每个数最多能执行一次操作 :
//   1）不变
//   2）可以选择让num变成0，承担x的代价
//   3）可以选择让num变成-num，承担y的代价
// 返回你达到目标的最小代价
// 数据规模 : 面试时面试官没有说数据规模
public class Q63_执行X和Y操作让累加和降到0以下的最小代价 {

    // 动态规划
    public static int minOpStep1(int[] arr, int x, int y) {
        int sum = 0;
        for (int num : arr) {   // 所有数累加和
            sum += num;
        }
        return process1(arr, x, y, 0, sum);
    }

    // arr[i...]自由选择，每个位置的数可以执行三种操作中的一种！
    // 执行变0的操作，x操作，代价 -> x
    // 执行变相反数的操作，y操作，代价 -> y
    // 还剩下sum这么多累加和，需要去搞定！
    // 返回搞定了sum，最低代价是多少？
    public static int process1(int[] arr, int x, int y, int i, int sum) {
        if (sum <= 0) {     // 当累加和<=0时，不用操作，返回0的代价
            return 0;
        }
        // sum > 0 没搞定
        if (i == arr.length) {  // 没搞定，但来到最后一个位置，返回系统最大值
            return Integer.MAX_VALUE;
        }
        // 第一选择，什么也不干！ --- 来到下一个位置，累加和不变
        int p1 = process1(arr, x, y, i + 1, sum);
        // 第二选择，执行x的操作，变0 x + 后续
        int p2 = Integer.MAX_VALUE;
        int next2 = process1(arr, x, y, i + 1, sum - arr[i]);
        if (next2 != Integer.MAX_VALUE) {   // 如果后续next2返回系统最大值说明无效
            p2 = x + next2;                 // next2有效时 p2 = x + next2
        }
        // 第三选择，执行y的操作，变相反数 x + 后续 7变-7 -14 减少两倍arr[i], arr[i] << 1
        int p3 = Integer.MAX_VALUE;
        int next3 = process1(arr, x, y, i + 1, sum - (arr[i] << 1));
        if (next3 != Integer.MAX_VALUE) {   // 如果后续next3返回系统最大值说明无效
            p3 = y + next3;                 // next3有效时 p3 = y + next3
        }
        // 返回三种选择中最小的代价
        return Math.min(p1, Math.min(p2, p3));
    }

    // 贪心1：加入二分（最优解）
    /*
    * 从大到小排序(排序不影响每个数的操作)，
    *       ① 当X >= Y时，(只执行取反)从右到左执行取反操作：Y，直到累加和 < 0。
    *       ②
    *
    * */
    public static int minOpStep2(int[] arr, int x, int y) {
        Arrays.sort(arr);           // 小 -> 大
        int n = arr.length;
        for (int l = 0, r = n - 1; l <= r; l++, r--) {  // 逆序 从大 -> 小
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        // arr 大 -> 小
        if (x >= y) {           // 没有任何必要执行x操作
            int sum = 0;
            for (int num : arr) {
                sum += num;
            }
            int cost = 0;       // 记录代价
            for (int i = 0; i < n && sum > 0; i++) {    // 什么时候sum < 0，就结束循环
                sum -= arr[i] << 1;                     // sum减去两倍(因为是取反)
                cost += y;                              // 累加代价
            }
            return cost;
        } else {                // 两种操作都有
            // ① 当来到一个位置不再执行Y的操作，那么以后的位置都不会有Y的操作(因为想取反，肯定要在前面大数的时候取反)
            // ② 当来到一个位置不再执行X的操作，那么以后的位置都不会有X的操作

            /* 方式一(无代码)：不断迭代执行Y操作次数m(长度)和X操作次数n(长度)的组合，找出最小代价，
                                从0次Y操作，arr.length次X操作开始
                                    每个成功组合的条件是：(前m次Y操作的累加和) + (剩余arr.length - m - n个数的累加和) < 0           */

            /* 方式二：① 二分查找
            *   来到i索引(i+1)个执行Y操作，结果假设为-500,
            *       从右向左找到后缀和<=500的最左的位置,假设为j
            *           那么0~i执行的Y操作，i~j执行X操作，j~arr.length-1不变
            *               记录代价保存，并与后面作比较！
            * */
            for (int i = n - 2; i >= 0; i--) {
                arr[i] += arr[i + 1];           // 记录每个位置的后缀和
            }
            int benefit = 0;
            // 注意，可以不二分，用不回退的方式！
            // 执行Y操作的数，有0个的时候
            int left = mostLeft(arr, 0, benefit);
            int cost = left * x;
            for (int i = 0; i < n - 1; i++) {
                // 0..i 这些数，都执行Y
                benefit += arr[i] - arr[i + 1];
                left = mostLeft(arr, i + 1, benefit);    // 二分找到最左位置
                cost = Math.min(cost, (i + 1) * y + (left - i - 1) * x);
            }
            return cost;
        }
    }

    // arr是后缀和数组， arr[l...]中找到值<=v的最左位置
    public static int mostLeft(int[] arr, int l, int v) {
        int r = arr.length - 1;
        int m = 0;
        int ans = arr.length;
        while (l <= r) {
            m = (l + r) / 2;
            if (arr[m] <= v) {
                ans = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return ans;
    }

    // 贪心2：不回退
    public static int minOpStep3(int[] arr, int x, int y) {
        // 系统排序，小 -> 大
        Arrays.sort(arr);
        int n = arr.length;
        // 如何变成 大 -> 小
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
        }
        if (x >= y) {
            int sum = 0;
            for (int num : arr) {
                sum += num;
            }
            int cost = 0;
            for (int i = 0; i < n && sum > 0; i++) {
                sum -= arr[i] << 1;
                cost += y;
            }
            return cost;
        } else {        // 两种操作都有
            // 方式二：② 不回退
            // 0个数执行Y
            int benefit = 0;
            // 全部的数都需要执行x，才能让累加和<=0
            int cost = arr.length * x;
            int holdSum = 0;

            // yRight：执行Y操作的最右边的位置，假设累加和-500； holdLeft：右向左找到后缀和<=500的最左的位置
            for (int yRight = 0, holdLeft = n; yRight < holdLeft - 1; yRight++) {
                benefit += arr[yRight];
                while (holdLeft - 1 > yRight && holdSum + arr[holdLeft - 1] <= benefit) {
                    holdSum += arr[holdLeft - 1];
                    holdLeft--;
                }
                // 0...yRight x holdLeft....
                cost = Math.min(cost, (yRight + 1) * y + (holdLeft - yRight - 1) * x);
            }
            return cost;
        }
    }


    // 为了测试
    public static int[] randomArray(int len, int v) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * v) + 1;
        }
        return arr;
    }

    // 为了测试
    public static int[] copyArray(int[] arr) {
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int n = 12;
        int v = 20;
        int c = 10;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * n);
            int[] arr = randomArray(len, v);
            int[] arr1 = copyArray(arr);
            int[] arr2 = copyArray(arr);
            int[] arr3 = copyArray(arr);
            int x = (int) (Math.random() * c);
            int y = (int) (Math.random() * c);
            int ans1 = minOpStep1(arr1, x, y);
            int ans2 = minOpStep2(arr2, x, y);
            int ans3 = minOpStep3(arr3, x, y);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("出错了!");
            }
        }
        System.out.println("测试结束");

    }

}
