package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-14  19:44
 */
/*
* 一个字符串，随意一个子串的字符种类不能超过k是达标子串，返回最长的达标子串长度
*
*   滑动窗口：单调性(窗口变大，子串的字符种类只能相等或增加，没有减少的可能)
*
*
* */
public class Q51_340_至少包含k个不同字符 {

    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k < 1) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] count = new int[256];     // 统计当前窗口中每个字符出现的次数
        int diff = 0;                   // 记录当前窗口中不同字符的个数
        int R = 0;
        int ans = 0;                    // 记录最长达标子串长度
        for (int i = 0; i < N; i++) {   // i是窗口的左边界
            // R 窗口的右边界               字符种类达到k，但右边界新的字符是之前存在的
            while (R < N && (diff < k || (diff == k && count[str[R]] > 0))) {
                diff += count[str[R]] == 0 ? 1 : 0;     // 如果是新字符，记录就+1
                count[str[R++]]++;                      // 此在当前窗口此字符出现次数+1，窗口向右拓展一位
            }
            // R 来到违规的第一个位置
            ans = Math.max(ans, R - i);         // 比较之前窗口的最长达标长度与当前达标窗口的大小
            diff -= count[str[i]] == 1 ? 1 : 0; // 如果左边界字符在窗口中出现的次数 == 1，则减去该字符种类(-1)，!=则不-1
            count[str[i]]--;                    // 左边界字符次数-1，此次循环结束i++(左边界右移一位)，开始新的窗口判断
        }
        return ans;
    }

}
