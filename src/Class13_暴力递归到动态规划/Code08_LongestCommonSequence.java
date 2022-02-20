package Class13_暴力递归到动态规划;

/* 题目三：
* 	 两个字符串的最长公共子序列问题
* 		比如字符串1：BDCABA；字符串2：ABCBDAB
		则这两个字符串的最长公共子序列长度为4，最长公共子序列是：BCBA
* */
public class Code08_LongestCommonSequence {

    /* 动态规划
    *       ① 创建两个字符串长度的二维数组，每个位置的值dp[i][j]表示其两个字符串的最长公共子序列的长度
    *       ② 然后给确定的位置赋值，0,0；第一行；第一列
    *       ③ 给其他位置赋值，每个位置有四种情况讨论,然后取---最大值，
    *               1）既不以str1[i]也不以str2[j]结尾    dp[i][j] = dp[i - 1][j - 1]   大小不用参与比较，已经比4）小，4）和其他的比就可以
    *               2）以str1[i]结尾，不以str2[j]结尾    dp[i][j] = dp[i][j - 1]
    *               3）以str2[j]结尾，不以str1[i]结尾    dp[i][j] = dp[i - 1][j]
    *               4）即以str1[i]结尾，也以str2[j]结尾  dp[i][j] = dp[i - 1][j - 1] + 1
    *           已知前提：
    *               可能性2)在可能性1)下面，一定不会比1)小；同理3)在可能性1)右边，也不会比1)小;
    *               可能性4)一定比可能性1)大1
    *
    * */
    public static int lcse(char[] str1, char[] str2) {

        // 创建两个字符串长度的二维数组，每个位置的值dp[i][j]表示其两个字符串的最长公共子序列的长度
        int[][] dp = new int[str1.length][str2.length];

        // 如果都只有1个字符，相等赋值1，否则为0
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;

        // 给第一列赋值，第一个出现相等赋为1，后面也都是1
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], str1[i] == str2[0] ? 1 : 0);
        }

        // 给第一行赋值，第一个出现相等赋为1，后面也都是1
        for (int j = 1; j < str2.length; j++) {
            dp[0][j] = Math.max(dp[0][j - 1], str1[0] == str2[j] ? 1 : 0);
        }

        // 给其他位置赋值
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                // 先决出可能性 2)、3)的大小
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

                // 可能性4)的前提是str1[i] == str2[j]，
                // 可能性1)大小不用参与比较，已经比4)小，4)和其他的比就可以，可能性4)一定比可能性1）大1。
                if (str1[i] == str2[j]) {
                    // 再决出上面获胜者再与可能性4)进行比较，选出最大者
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }/*else {
                    // 可能性2)在可能性1)下面，一定不会比1)小；同理3)在可能性1)右边，也不会比1)小;
                    // 因此与可能性1)对比可以省略
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1]);
                }*/
            }
        }
        return dp[str1.length - 1][str2.length - 1];
    }
}

