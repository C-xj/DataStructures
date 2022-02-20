package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  10:43
 */
/*
* 实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n ）
*       n有可能是负数
*
* 例如10^75次方：
*       75 = 64 + 8 + 2 + 1   转为二进制  1001011
*
*   准备一个t = 10^1 , res = 1;
*   然后 根据二进制从右向左计算，
*       res = res * t   (当此时二进制位置为1时，执行一次) 共计算4次(4个1)
*       t = t * t       (二进制每移动一位，执行一次) 共计算6次(从最右边移动到最高位要6次)
*
*
* */
public class Q39_50_Pow {

    public static int myPow1(int x,int n){
        int ans = 1;
        int t = x;
        // n == 100101100
        while (n != 0){
            if ((n & 1) != 0){  // 得到最低位(即最右的一位)不为0，为1
                ans *= t;
            }
            t *= t; // x^2 x^4 x^8...
            n >>= 1;
        }
        return ans;
    }

    public static double myPow2(double x, int n) {
        if (n == 0) {       // 任何数的0次方都是1
            return 1D;
        }
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n); // 系统最小转二进制
        double t = x;
        double ans = 1D;
        while (pow != 0) {
            if ((pow & 1) != 0) {
                ans *= t;
            }
            t = t * t;
            pow >>= 1;
        }
        if (n == Integer.MIN_VALUE) {
            ans *= x;
        }
        return n < 0 ? (1D / ans) : ans;    // n为负的是倒数
    }

    public static void main(String[] args) {
        System.out.println("world shut up!");
        int a = Integer.MIN_VALUE;
        int b = -a;
        System.out.println(b);

        System.out.println("==============");

        double test = 1.00000001D;
        int N = Integer.MIN_VALUE;
        System.out.println(test == 1D);
        System.out.println(test + "的" + N + "次方，结果：");
        System.out.println(Math.pow(test, (double) N));
        System.out.println(myPow2(test, N));
    }


}
