package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-06  18:43
 */
/*
* 例如：ab1c -> abc
*       ① 保留操作 - 代价 0
*       ① 删除操作 - 代价 d
*       ① 添加操作 - 代价 a
*       ① 替换操作 - 代价 r
*   上述例子最小编辑距离为 d
*
*   动态规划：样本模型(行列模型)
*   dp[i][j]   表示str1前缀取i个(下标0...i-1)，转变成str2前缀j个(下标0...j-1)的最小编辑距离
*       dp[i][j]四种可能： 取最小值
*            ① dp[i-1][j] + d   表示str1的前i-1个就编辑为str2的前j的编辑距离,然后删除str1多余的一个i位置的字符(代价为d)
*            ② dp[i][j-1] + a   表示str1的前i个编辑为str2的前j-1个的编辑距离,然后str1尾部添加一个i位置的字符(代价为a)
*            ③ 当str1[i-1] == str2[j-1]时  dp[i-1][j-1]       表示str1的第i个与str2的第j个字符相等,因此编辑距离就继承 dp[i-1][j-1]
*            ④ 当str1[i-1] != str2[j-1]时  dp[i-1][j-1] + r   表示str1的第i个与str2的第j个字符不相等,因此编辑距离就为 dp[i-1][j-1] 和修改最后一位的字符(代价为r)
* */
public class Q12_编辑距离问题 {

    // 动态规划
    public static int minCost1(String str1, String str2, int ac, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        int row = chs1.length + 1;
        int col = chs2.length + 1;
        int[][] dp = new int[row][col];
        // dp[0][0] = 0;
        for (int i = 1; i < row; i++) {     // 第一列初始化：删除操作
            dp[i][0] = dc * i;
        }
        for (int j = 1; j < col; j++) {     // 第一行初始化：添加操作
            dp[0][j] = ac * j;
        }
        // 普通位置
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {     // 可能性③和④只能二选一，选出的和后面比大小
                dp[i][j] = chs1[i - 1] == chs2[j - 1] ? dp[i - 1][j - 1] :  dp[i - 1][j - 1] + rc;
                dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + ac);   // 选出来的与可能性②比大小
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + dc);   // 选出来的与可能性①比大小
            }
        }
        return dp[row - 1][col - 1];
    }


    // 优化：空间压缩
    public static int minCost2(String str1, String str2, int ac, int dc, int rc) {
        if (str1 == null || str2 == null) {
            return 0;
        }
        char[] chs1 = str1.toCharArray();
        char[] chs2 = str2.toCharArray();
        char[] longs = chs1.length >= chs2.length ? chs1 : chs2;
        char[] shorts = chs1.length < chs2.length ? chs1 : chs2;
        if (chs1.length < chs2.length) {
            int tmp = ac;
            ac = dc;
            dc = tmp;
        }
        int[] dp = new int[shorts.length + 1];
        for (int i = 1; i <= shorts.length; i++) {
            dp[i] = ac * i;
        }
        for (int i = 1; i <= longs.length; i++) {
            int pre = dp[0];
            dp[0] = dc * i;
            for (int j = 1; j <= shorts.length; j++) {
                int tmp = dp[j];
                if (longs[i - 1] == shorts[j - 1]) {
                    dp[j] = pre;
                } else {
                    dp[j] = pre + rc;
                }
                dp[j] = Math.min(dp[j], dp[j - 1] + ac);
                dp[j] = Math.min(dp[j], tmp + dc);
                pre = tmp;
            }
        }
        return dp[shorts.length];
    }

    public static void main(String[] args) {
        String str1 = "ab12cd3";
        String str2 = "abcdf";
        System.out.println(minCost1(str1, str2, 5, 3, 2));
        System.out.println(minCost2(str1, str2, 5, 3, 2));

        str1 = "abcdf";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 3, 2, 4));
        System.out.println(minCost2(str1, str2, 3, 2, 4));

        str1 = "";
        str2 = "ab12cd3";
        System.out.println(minCost1(str1, str2, 1, 7, 5));
        System.out.println(minCost2(str1, str2, 1, 7, 5));

        str1 = "abcdf";
        str2 = "";
        System.out.println(minCost1(str1, str2, 2, 9, 8));
        System.out.println(minCost2(str1, str2, 2, 9, 8));

    }
}
