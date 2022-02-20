package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-14  15:00
 */
/*
* 给你一个整数 n ，返回 和为 n 的完全平方数的最少数量 。
    完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。

    * 任何一个数，拆成平方数的项不会超过4项。
* */
public class Q49_279_完全平方数 {

    // 暴力解
    public static int numSquares1(int n) {
        int res = n, num = 2;
        while (num * num <= n) {
            int a = n / (num * num), b = n % (num * num);
            res = Math.min(res, a + numSquares1(b));
            num++;
        }
        return res;
    }

    /* 动态规划 f[i] 表示最少需要多少个数的平方来表示整数 i。
    *   同时因为计算 f[i]时所需要用到的状态仅有 f[i-j^2]，必然小于i，因此我们只需要从小到大地枚举 i 来计算f[i]即可。
    * */
    public int numSquares(int n) {
        int[] f = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int minn = Integer.MAX_VALUE;
            for (int j = 1; j * j <= i; j++) {
                minn = Math.min(minn, f[i - j * j]);
            }
            f[i] = minn + 1;
        }
        return f[n];
    }

    // 1 : 1, 4, 9, 16, 25, 36, ...
    // 4 : 7, 15, 23, 28, 31, 39, 47, 55, 60, 63, 71, ...
    // 规律解
    // 规律一：个数不超过4
    // 规律二：出现1个的时候，显而易见
    // 规律三：任何数 % 8 == 7，一定是4个
    // 规律四：任何数消去4的因子之后，剩下rest，rest % 8 == 7，一定是4个
    public static int numSquares2(int n) {
        int rest = n;
        while (rest % 4 == 0) {
            rest /= 4;
        }
        if (rest % 8 == 7) {
            return 4;
        }
        int f = (int) Math.sqrt(n);
        if (f * f == n) {
            return 1;
        }
        for (int first = 1; first * first <= n; first++) {
            int second = (int) Math.sqrt(n - first * first);
            if (first * first + second * second == n) {
                return 2;
            }
        }
        return 3;
    }

    // 数学解
    // 1）四平方和定理
    // 2）任何数消掉4的因子，结论不变
    public static int numSquares3(int n) {
        while (n % 4 == 0) {
            n /= 4;
        }
        if (n % 8 == 7) {
            return 4;
        }
        for (int a = 0; a * a <= n; ++a) {
            int b = (int) Math.sqrt(n - a * a);
            if (a * a + b * b == n) {
                return (a > 0 && b > 0) ? 2 : 1;
            }
        }
        return 3;
    }

    public static void main(String[] args) {
        numSquares1(10);
//		for (int i = 1; i < 1000; i++) {
//			System.out.println(i + " , " + numSquares1(i));
//		}
    }

}
