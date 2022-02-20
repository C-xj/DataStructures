package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-10  21:10
 */
/*
*   给定两个字符串S和T，返回S的所有子序列中，有多少个子序列的字面值等于T
*
*  动态规划：样本对应模型(情况根据结尾位置划分)
*
*       dp[i][j]:表示字符串S从0...i的子序列，有多少个等于T从0...j这个前缀串
*
*          情况①： 不以i位置的字符生成子序列  dp[i-1][j]
*          情况②： 以i位置的字符生成子序列
*                   当 S[i] != T[j]时,此子序列不等
*                   当 S[i] == T[j]时, dp[i-1][j-1]
*
*       dp[i][j] = 情况①的个数 + 情况②的个数
*
* */
public class Q24_S字符串有多少个子序列等于T字符串 {

    // 暴力递归
    public static int numDistinct1(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        return process(s, t, s.length, t.length);
    }

    public static int process(char[] s, char[] t, int i, int j) {
        if (j == 0) {   // T没有字符可比较，这说明前面都比较成功，是一种解
            return 1;
        }
        if (i == 0) {
            return 0;
        }
        int res = process(s, t, i - 1, j);          // 不以i位置的字符生成子序列
        if (s[i - 1] == t[j - 1]) {                   // 以i位置的字符生成子序列，且最后一个字符相等
            res += process(s, t, i - 1, j - 1);
        }
        return res;
    }

    // S[...i]的所有子序列中，包含多少个字面值等于T[...j]这个字符串的子序列
    // 记为dp[i][j]
    // 可能性1）S[...i]的所有子序列中，都不以s[i]结尾，则dp[i][j]肯定包含dp[i-1][j]
    // 可能性2）S[...i]的所有子序列中，都必须以s[i]结尾，
    // 这要求S[i] == T[j]，则dp[i][j]包含dp[i-1][j-1]
    public static int numDistinct2(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[][] dp = new int[s.length + 1][t.length + 1];
        for (int j = 0; j <= t.length; j++) {
            dp[0][j] = 0;
        }
        for (int i = 0; i <= s.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = 1; j <= t.length; j++) {
                dp[i][j] = dp[i - 1][j] + (s[i - 1] == t[j - 1] ? dp[i - 1][j - 1] : 0);
            }
        }
        return dp[s.length][t.length];
    }

    // 动态规划-优化空间
    public static int numDistinct3(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] dp = new int[t.length + 1];
        dp[0] = 1;
        for (int j = 1; j <= t.length; j++) {
            dp[j] = 0;
        }
        for (int i = 1; i <= s.length; i++) {
            for (int j = t.length; j >= 1; j--) {
                dp[j] += s[i - 1] == t[j - 1] ? dp[j - 1] : 0;
            }
        }
        return dp[t.length];
    }

}
