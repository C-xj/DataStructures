package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  11:19
 */
/*
* x开根号，向下取整  --- 二分
* */
public class Q40_69_sqrt {

    // x一定非负，输入可以保证
    public static int mySqrt(int x) {
        if (x == 0) {
            return 0;
        }
        if (x < 3) {
            return 1;
        }
        long ans = 1;   // 记录值
        long L = 1;
        long R = x;
        long M = 0;
        while (L <= R) {
            M = (L + R) / 2;
            if (M * M <= x) {  // 如果当前中间值的平方小于x，更新记录。往大的走
                ans = M;
                L = M + 1;
            } else {    // 如果当前中间值的平方大于x，往小的走
                R = M - 1;
            }
        }
        return (int) ans;
    }

}
