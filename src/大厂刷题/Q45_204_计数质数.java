package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  16:54
 */
/*
* 给定整数 n ，返回 所有小于非负整数 n 的质数的数量 。
*
*   质数2：2*2 2*3 2*4... 不是质数
*   质数3：3*3 3*5 3*7... 不是质数
*   质数5：5*5 5*7 3*9... 不是质数
*
* */
public class Q45_204_计数质数 {

    public static int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        // j已经不是素数了，f[j] = true;
        boolean[] f = new boolean[n];
        int count = n / 2;      // 所有偶数都不要，还剩几个数，以后在遇到不是质数count--
        // 从质数3开始，每次加2
        for (int i = 3; i * i < n; i += 2) {    // 天然的跳过了偶数的判断
            if (f[i]) {         // 不是素数，跳过此数循环，下走
                continue;
            }
            // 3 ->
            for (int j = i * i; j < n; j += 2 * i) {
                if (!f[j]) {    // 把当前质数的倍数都不是质数给减去，同时将此数设置为true
                    --count;
                    f[j] = true;
                }
            }
        }
        return count;
    }

}
