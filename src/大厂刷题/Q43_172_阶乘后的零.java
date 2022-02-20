package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  16:10
 */
/*
* 给定一个整数 n ，返回 n! 结果中尾随零的数量。
    提示 n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1
    *
    * 0的个数就是有多少个10，上面阶乘中每个数的分解因子，总的因子2，一定比5多，
    *   所以此题变成有多少个5的因子出现
    *   ① n/5   每5个中出现1个5的因子  (n /= 5 一共可以分多少组，即多少个)
    *   ② n/25  每25个中又会多出1个
    *   ③ n/125 每125个中又会多出1个
    *   ... ... 到n/x  (n除某个数的值为0截至)
* */
public class Q43_172_阶乘后的零 {

    public int trailingZeroes(int n) {
        int zeroCount = 0;
        long currentMultiple = 5;
        while (n > 0) {
            n /= 5;
            zeroCount += n;
        }
        return zeroCount;
    }

}
