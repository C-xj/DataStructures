package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-14  9:16
 */
/*
* 给定两个字符串str1和str2,在str1中寻找一个最短字串，能包含str2的所有字符，
*   字符顺序无所谓，str1的这个最短子串也可以包含多余的字符返回这个最短包含子串。
*
* 思路分析：
*   滑动窗口，str2用一个map存字符以及字符对应的个数，all记录总str1欠的字符个数
*       ① l从0开始为起始点，r从0开始为结束，移动j，
*       ② 字符与map对比，进行还款，字符有存在的则将对应的map值减1，同时减后的值>=0表示有效还款，all减1；
*           当还款使得有字符map对应的值<0时，是无效还款，all不变。继续移动r使得all=0；但不急着做记录。
*       ③ 然后在满足当前l位置字符<0的的前提，不断移动l(改变起始位置)，缩短包含子串，之后记录子串长度。
*       ④ left再后移一位，不再是包含子串，然后进行②③操作。
* */
public class Q33_最小包含子串 {

    public static int minLength(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length()) {
            return Integer.MAX_VALUE;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] map = new int[256]; // map[37] = 4  37  4次    // ascii码到255
        for (int i = 0; i != str2.length; i++) {    // 每个字符的欠账表
            map[str2[i]]++;
        }
        // [L,R) 左闭右开
        int left = 0;                   // 窗口左边界
        int right = 0;                  // 窗口右边界
        int all = str2.length;          // 总欠账
        int minLen = Integer.MAX_VALUE;
        // [left, right)  [left, right-1]    [0,0)
        // R右扩   L ==0  R
        while (right != str1.length) {
            map[str1[right]]--;
            if (map[str1[right]] >= 0) {
                all--;
            }
            if (all == 0) { // 还完了，先不着急存入答案，移动left，寻找尽可能短的子串
                while (map[str1[left]] < 0) {
                    map[str1[left++]]++;
                }
                // [L..R] ，记录当前起始位置的子串，起始位置后移一位，all+1
                minLen = Math.min(minLen, right - left + 1);
                map[str1[left++]]++;
                all++;
            }
            right++;
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        String str1 = "adabbca";
        String str2 = "acb";
        System.out.println(minLength(str1, str2));

    }


    // 题目二：
    //      在str中，每种字符都要保留一个，让最后的结果，字典序最小 ，并返回
    // 贪心
    public static String removeDuplicateLetters1(String str) {
        if (str == null || str.length() < 2) {
            return str;
        }
        int[] map = new int[256];
        for (int i = 0; i < str.length(); i++) {
            map[str.charAt(i)]++;
        }
        int minACSIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            minACSIndex = str.charAt(minACSIndex) > str.charAt(i) ? i : minACSIndex;
            if (--map[str.charAt(i)] == 0) {
                break;
            }
            // 0...break(之前) minACSIndex
            // str[minACSIndex] 剩下的字符串str[minACSIndex+1...] -> 去掉str[minACSIndex]字符 -> s' s'...
        }
        return String.valueOf(str.charAt(minACSIndex)) + removeDuplicateLetters1(
                str.substring(minACSIndex + 1).replaceAll(String.valueOf(str.charAt(minACSIndex)), ""));
    }

    // 无递归版本
    public static String removeDuplicateLetters2(String s) {
        char[] str = s.toCharArray();
        // 小写字母ascii码值范围[97~122]，所以用长度为26的数组做次数统计
        // 如果map[i] > -1，则代表ascii码值为i的字符的出现次数
        // 如果map[i] == -1，则代表ascii码值为i的字符不再考虑
        int[] map = new int[26];
        for (int i = 0; i < str.length; i++) {
            map[str[i] - 'a']++;
        }
        char[] res = new char[26];
        int index = 0;
        int L = 0;
        int R = 0;
        while (R != str.length) {
            // 如果当前字符是不再考虑的，直接跳过
            // 如果当前字符的出现次数减1之后，后面还能出现，直接跳过
            if (map[str[R] - 'a'] == -1 || --map[str[R] - 'a'] > 0) {
                R++;
            } else { // 当前字符需要考虑并且之后不会再出现了
                // 在str[L..R]上所有需要考虑的字符中，找到ascii码最小字符的位置
                int pick = -1;
                for (int i = L; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1 && (pick == -1 || str[i] < str[pick])) {
                        pick = i;
                    }
                }
                // 把ascii码最小的字符放到挑选结果中
                res[index++] = str[pick];
                // 在上一个的for循环中，str[L..R]范围上每种字符的出现次数都减少了
                // 需要把str[pick + 1..R]上每种字符的出现次数加回来
                for (int i = pick + 1; i <= R; i++) {
                    if (map[str[i] - 'a'] != -1) { // 只增加以后需要考虑字符的次数
                        map[str[i] - 'a']++;
                    }
                }
                // 选出的ascii码最小的字符，以后不再考虑了
                map[str[pick] - 'a'] = -1;
                // 继续在str[pick + 1......]上重复这个过程
                L = pick + 1;
                R = L;
            }
        }
        return String.valueOf(res, 0, index);
    }


}
