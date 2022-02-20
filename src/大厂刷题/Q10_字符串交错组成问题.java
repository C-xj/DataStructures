package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-06  10:12
 */
/*
给定三个字符串s1、s2、s3，请你帮忙验证s3是否是由s1和s2交错组成的。

        两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干非空子字符串：

        s = s1 + s2 + ... + sn
        t = t1 + t2 + ... + tm
        |n - m| <= 1
        交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...
        提示：a + b 意味着字符串 a 和 b 连接。

    动态规划：样本对应模型(情况根据结尾位置划分)

        dp[i][j] 表示s1的前i(下标0...i-1)个与s2的前j(下标0...j-1)个能否交错组成s3的前i+j(下标0...i+j-1)长度

        情况① s3[i+j-1] == s1[i-1]时(最后一个字符是s1贡献时)，还要满足dp[i-1][j]为true
        情况② s3[i+j-1] == s2[j-1]时(最后一个字符是s2贡献时)，还要满足dp[i][j-1]为true

        两种情况满足一个就可以，因此求或
*/


public class Q10_字符串交错组成问题 {

// dp[i][j] 表示s1的前i(下标0...i-1)个与s2的前j(下标0...j-1)个能否交错组成s3的前i+j(下标0...i+j-1)长度
    public boolean isInterleave1(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();

        if (n + m != t) {
            return false;
        }

        boolean[][] f = new boolean[n + 1][m + 1];

        f[0][0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) { // false           false/true
                    f[i][j] = f[i][j] || (f[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j > 0) {
                    f[i][j] = f[i][j] || (f[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }

        return f[n][m];
    }

    // 滚动数组优化
    public boolean isInterleave11(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();

        if (n + m != t) {
            return false;
        }

        boolean[] f = new boolean[m + 1];

        f[0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    f[j] = f[j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    f[j] = f[j] || (f[j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }

        return f[m];
    }




    public boolean isInterleave2(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null){
            return false;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        char[] str3 = s3.toCharArray();
        if (str3.length != str1.length + str2.length){
            return false;
        }

        boolean[][] dp = new boolean[str1.length + 1][str2.length + 1];
        dp[0][0] = true;
        for (int i = 1; i <= str1.length;i++){
            if (str1[i-1] != str3[i-1]){
                break;
            }
            dp[i][0] = true;    // 第一列初始化
        }
        for (int i = 1; i <= str2.length;i++){
            if (str2[i-1] != str3[i-1]){
                break;
            }
            dp[0][i] = true;    // 第一行初始化
        }
        // 其他位置
        for(int i = 1;i <=str1.length;i++){
            for(int j = 1;j <= str2.length;j++){
                if ((str1[i - 1] == str3[i + j - 1] && dp[i - 1][j])
                        || (str2[j - 1] == str3[i + j - 1] && dp[i ][j - 1])){
                    dp[i][j] = true;
                }
            }
        }
        return dp[str1.length][str2.length];
    }

}
