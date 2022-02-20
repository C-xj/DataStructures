package Class12_暴力递归;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/*
 *   题目一：打印一个字符串的全部排列
 *          如果是abc，
 *              索引0第一个位置三种选择，索引0位置的a来到0位置，索引1位置的b来到0位置(即a、b交换)，索引2位置的c来到0位置(即a、c交换)
 *              索引1第二个位置两种选择，索引1位置... ...
 *              索引2第三个位置一种选择
 *   题目二：打印一个字符串的全部排列，要求不要出现重复的排列
 *       使用HashSet防止重复排列出现
 * */
public class Code04_PrintAllPermutations {


    public static ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }
        char[] chs = str.toCharArray();
        //process(chs, 0, res);   // 题目一
        process1(chs, 0, res);  // 题目二
        return res;
    }

    // 题目一：
    // str[i..]范围上，所有的字符，都可以在i位置上，后续都去尝试
    // str[0..i-1]范围上，是之前做的选择，已经做好决定
    // i终止位置，str当前的样子，就是一种结果 -> res
    // 请把所有的字符串形成的全排列，加入到res里去
    public static void process(char[] str, int i, ArrayList<String> res) {
        if (i == str.length) {
            res.add(String.valueOf(str));
            return;
        }
        // 如果i没有中止，i... 都可以来到i位置
        for (int j = i; j < str.length; j++) {  // j 在表示i后面所有的字符都有机会
                swap(str, i, j);
                process(str, i + 1, res);
                swap(str, i, j);    // 交换回来，恢复现场，以免影响其他分支(不然其他分支会在此基础上进行交换，出现错误)
        }
    }


    // 题目二： 方式① 使用HashSet自动去重，同子序列方式一样（略）
    //         方式② 分支限界，没有必要的分支在源头杀死
    public static void process1(char[] str, int i, ArrayList<String> res) {
        if (i == str.length) {
            res.add(String.valueOf(str));
            return;
        }
        // 使用分支限界，表示某个字符在当前位置有没有使用过，代替hash表
        boolean[] visit = new boolean[26]; // visit[0 1 .. 25]
        // 如果i没有中止，i... 都可以来到i位置
        for (int j = i; j < str.length; j++) {  // j 在表示i后面所有的字符都有机会
            // 这种字符在此位置没出现，才走此支路
            if (!visit[str[j] - 'a']) {
                visit[str[j] - 'a'] = true;
                swap(str, i, j);
                process1(str, i + 1, res);
                swap(str, i, j);    // 交换回来，恢复现场，以免影响其他分支(不然其他分支会在此基础上进行交换，出现错误)
            }
        }
    }

    // 交换两个位置的字符
    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }


    // 题目二：方式③
    public static List<String> getAllC(String s) {
        List<String> ans = new ArrayList<>();
        ArrayList<Character> set = new ArrayList<>();
        for (char cha : s.toCharArray()) {
            set.add(cha);
        }
        process(set, "", ans);
        return ans;
    }

    public static void process(ArrayList<Character> list, String path, List<String> ans) {
        if (list.isEmpty()) {
            ans.add(path);
            return;
        }

        HashSet<Character> picks = new HashSet<>();

        for (int index = 0; index < list.size(); index++) {
            if (!picks.contains(list.get(index))) {
                picks.add(list.get(index));
                String pick = path + list.get(index);
                ArrayList<Character> next = new ArrayList<>(list);
                next.remove(index);
                process(next, pick, ans);
            }
        }
    }

    public static void main(String[] args) {
        String s = "aca";
        List<String> ans = getAllC(s);
        for (String str : ans) {
            System.out.println(str);
        }
    }

}

