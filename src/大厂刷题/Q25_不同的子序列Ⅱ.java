package 大厂刷题;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Chu
 * @create 2022-01-11  9:49
 */
/*
* 给定一个字符串Str，返回Str的所有子序列中有多少不同的字面值
*       因为结果可能很大，所以返回答案需要对 10^9 + 7 取余 。
*
*   举例：   1,2,1
*   来到第一个1：{}、{1}
*                   all(1)1 == 2        表示第一个1时的长度为2
*
*   来到2时:{}、{1}、{2}、{1、2}
*                   all(2)1 == 4
*
*       解释：先让之前的每一个加2：新出现的{2}、{1,2},然后加上之前的{}、{1}
*
*   来到第二个1：{}、{1}、{2}、{1、2}、{1,1}、{2,1}、{1,2,1}
*       解释：先让之前的每一个加1：新出现的{1}、{1,1}、{2,1}、{1,2,1},然后加上之前的{}、{1}、{2}、{1、2}
*          但要有个修正过程，剔除添加重复的(即是，上一次出现1的时候，不算{}且以1结尾的结果就是重复的---{1})。
*                   all(1)2 == all(2)1 + 新出现 - [all(1)1 - 1]
*                         新出现(再all(2)1基础上加一个数，个数与其相等)
*                           --> 4 + 4 - (2 - 1) = 7
*
*   如果动态规划：
*       即在计算 dp[k] 时，首先会将 dp[k - 1] 对应的子序列的 末尾 添加 S[k] 得到额外的 dp[k - 1] 个子序列，并减去重复出现的子序列数目，这个数目即为上一次添加 S[k] 之前的子序列数目 dp[last[S[k]] - 1]。
*
*           即： dp[k] = 2 * dp[k - 1] - dp[last[S[k]] - 1]
*
*
* */

public class Q25_不同的子序列Ⅱ {

    public static int distinctSubSeqⅡ(String s){
        if (s == null || s.length() == 0){
            return 0;
        }
        int m = 1000000007;     // 因为数太大，要模一下
        char[] str = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<>();
        int all = 1; // 一个字符也没遍历时，有空集
        for (char x : str){
            int newAdd = all;
            //int curAll = all + newAdd - (map.containsKey(x) ? (map.get(x)) : 0);
            int curAll = all;
            curAll =(curAll + newAdd) % m;
            curAll = (curAll - (map.containsKey(x) ? (map.get(x)) : 0) + m) % m;
            all = curAll;
            map.put(x,newAdd);   // 将以刚刚字符结尾的新增存入，以便后面出现重复字符减去
        }
        return all - 1;  // 减去空集
    }


    // 动态规划
    public static int distinctSubseqII(String S) {
        int MOD = 1_000_000_007;
        int N = S.length();
        int[] dp = new int[N+1];
        // dp[i] 表示 S[0..i) 中有多少个**包含空序列**的不重复子序列

        dp[0] = 1; // 表示空字符串中有一个子序列：""

        int[] last = new int[26];
        Arrays.fill(last, -1);

        for (int i = 0; i < N; ++i) {
            int x = S.charAt(i) - 'a';
            dp[i+1] = dp[i] * 2 % MOD; // 对于上一个集合中的每个子序列，加上当前的字符得到新的序列
            if (last[x] >= 0)   // 当是重复出现的字符，再减去上次遇到该字母以其为结尾集合的大小
                dp[i+1] -= dp[last[x]];
            dp[i+1] %= MOD;
            last[x] = i; // 记录上一次出现字符 x 的索引
        }

        dp[N]--; // 减去空序列
        if (dp[N] < 0) dp[N] += MOD;
        return dp[N];
    }


    public static void main(String[] args) {
        String s = "bcacbdbsadbabs";
        System.out.println(distinctSubSeqⅡ(s));
        System.out.println(distinctSubseqII(s));
    }


}
