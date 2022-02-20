package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-05  10:50
 */
/* 求一个字符串中，最长无重复字符子串长度
*       以i字符结尾，往前推的最大无重复子串长度
*
* 动态规划：i往前的值有两个因素影响：  哪个离当前字符近(即，最小的距离)，就是多长
*            ① i位置的字符上次出现的位置
*            ② i-1位置最长无重复字符子串的长度
*       压缩空间
* */
public class Q05_最长无重复字符的子串长度 {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        char[] str = s.toCharArray();
        // map[i] = v  表示i这个ascii码的字符，上次出现在v位置
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;        // 初始化
        }

        int N = str.length;
        int ans = 1;
        int pre = 1;        // 上一个位置，向左推了多长
        map[str[0]] = 0;    // 0位置的字符记录在0位置
        for (int i = 1;i < N;i++){ // ①        ②
            pre = Math.min(i - map[str[i]],pre + 1);  // 将当前i位置的长度赋给pre
            ans = Math.max(ans,pre);       // 和之前的对比保存下最大长度
            map[str[i]] = i;               // 此字符来到当前i位置
        }
        return ans;
    }
}
