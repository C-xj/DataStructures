package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-10  9:50
 */
/*
* 给定一个布尔表达式和一个期望的布尔结果 result，布尔表达式由 0 (false)、1 (true)、& (AND)、 | (OR) 和 ^ (XOR) 符号组成。
*       实现一个函数，算出有几种可使该表达式得出 result 值的括号方法
*   注意：长度为奇数，0、2、4...是0/1; 1、3、5...是逻辑符号
*
*   输入: s = "1^0|0|1", result = 0
    输出: 2
    解释: 两种可能的括号方法是
        1^(0|(0|1))
        1^((0|0)|1)

*   思路：以1位置逻辑符号最后结算的总方法数 + 以3位置... + 以5位置...
*           然后根据位置的逻辑符号，以及需要的结果，来分情况讨论此位置两边的结果
* */
public class Q19_布尔表达式的期待方法数 {

    public static class Info{
        public int t;   // 为true的方法数
        public int f;   // 为false的方法数

        public Info(int t, int f) {
            this.t = t;
            this.f = f;
        }
    }

    // 递归 -> 记忆化搜索
    public static int countEval0(String express,int desired){
        if (express == null || express.equals("")){
            return 0;
        }
        char[] exp = express.toCharArray();
        // 记忆化搜索
        int N = exp.length;
        Info[][] dp = new Info[N][N];

        Info allInfo = func(exp, 0, exp.length - 1,dp);
        return desired == 1 ? allInfo.t : allInfo.f;
    }

    /* 限制：
    *   L..R上，一定有奇数个字符
    *   L位置的字符和R位置的字符，非0即1，不能是逻辑符号！
    *   返回str[L..R]这一段，为true的方法数，和false的方法数
    * */
    public static Info func(char[] str,int L,int R,Info[][] dp){
        if (dp[L][R] != null){      // 之前算过就取到返回
            return dp[L][R];
        }
        int t = 0;
        int f = 0;
        if (L == R){                // 只有一个字符时
            t = str[L] == '1' ? 1 : 0;
            f = str[L] == '0' ? 1 : 0;
            return new Info(t,f);
        }else { // 不止一个字符L..R >= 3
            // 每一种都是逻辑符号，split枚举的东西
            // 都要试试最后的结果数
            for (int split = L + 1;split < R; split += 2){
                Info leftInfo = func(str,L,split - 1,dp);  // 此符号左侧true/false的结果数
                Info rightInfo = func(str,split + 1,R,dp); // 此符号右侧结果数
                int a= leftInfo.t;
                int b= leftInfo.f;
                int c= rightInfo.t;
                int d= rightInfo.f;

                switch (str[split]){    // 以当前位置为最后一个计算的逻辑符号
                    case '&':
                        t += a * c;
                        f += b * c + b * d + a * d;
                        break;
                    case '|':
                        t += a * c + a * d + b * c;
                        f += b * d;
                        break;
                    case '^':
                        t += a * d + b * c;
                        f += a * c + b * d;
                        break;
                }
            }

        }
        dp[L][R] = new Info(t,f);       // 加入记忆化搜索
        return dp[L][R];
    }

    // 动态规划，慢
/*    1、状态定义：
    dp[i][j][result=0/1]表示第i到j个数字计算结果为result的方案数。
            2、状态转移：
    枚举区间分割点，根据分割点的情况讨论左右区间计算结果，方案数增量为左右方案数相乘。
    分割点为 '&':
    结果为0 有三种情况： 0 0, 0 1, 1 0
    dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0];

    结果为1 有一种情况： 1 1
    dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][1];

    分割点为 '|':
    结果为0 有一种情况： 0 0
    dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];

    结果为1 有三种情况： 0 1, 1 0, 1 1
    dp[i][j][1] += dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];

    分割点为 '^':
    结果为0 有两种情况： 0 0, 1 1
    dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];

    结果为1 有两种情况： 0 1, 1 0
    dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1];

            3、base case：
    数字位初始化为1，即dp[i][i][0/1] = 1*/
    public int countEval(String s, int result) {
        //特例
        if (s.length() == 0) {
            return 0;
        }
        if (s.length() == 1) {
            return (s.charAt(0) - '0') == result ? 1 : 0;
        }
        char[] ch = s.toCharArray();
        //定义状态
        int[][][] dp = new int[ch.length][ch.length][2];
        //base case
        for (int i = 0; i < ch.length; i++) {
            if (ch[i] == '0' || ch[i] == '1') {
                dp[i][i][ch[i] - '0'] = 1;
            }
        }
        //套区间dp模板
        //枚举区间长度len，跳步为2，一个数字一个符号
        for (int len = 2; len <= ch.length; len += 2) {
            //枚举区间起点，数字位，跳步为2
            for (int i = 0; i <= ch.length - len; i += 2) {
                //区间终点，数字位
                int j = i + len;
                //枚举分割点，三种 '&','|', '^'，跳步为2
                for (int k = i + 1; k <= j - 1; k += 2) {
                    if (ch[k] == '&') {
                        //结果为0 有三种情况： 0 0, 0 1, 1 0
                        //结果为1 有一种情况： 1 1
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][1];
                    }
                    if (ch[k] == '|') {
                        //结果为0 有一种情况： 0 0
                        //结果为1 有三种情况： 0 1, 1 0, 1 1
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0];
                        dp[i][j][1] += dp[i][k - 1][0] * dp[k + 1][j][1] + dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];
                    }
                    if (ch[k] == '^') {
                        //结果为0 有两种情况： 0 0, 1 1
                        //结果为1 有两种情况： 0 1, 1 0
                        dp[i][j][0] += dp[i][k - 1][0] * dp[k + 1][j][0] + dp[i][k - 1][1] * dp[k + 1][j][1];
                        dp[i][j][1] += dp[i][k - 1][1] * dp[k + 1][j][0] + dp[i][k - 1][0] * dp[k + 1][j][1];
                    }
                }
            }
        }
        return dp[0][ch.length - 1][result];
    }

}


