package Class12_暴力递归;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/*
*   题目一：打印一个字符串的全部子序列（与字串不同---字串通过两个for循环可以实现）
*       一个字符有两种选择的可能，然后来到下一个字符进行选择，如果index来到了str中的终止位置，把沿途路径形成的答案，放入ans中
*
*   题目二：打印一个字符串的全部子序列，要求不要出现重复字面值的子序列
*       使用HashSet防止重复字面值出现
* */
public class Code03_PrintAllSubsquences {

    // 题目一：
    public static List<String> getAllSubs(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();

        process(chars, 0, ans, path);

        return ans;
    }

    /*
    * str固定，不变
    * index此时来到的位置，要 or 不要
    * 如果index来到了str中的终止位置，把沿途路径形成的答案，放入ans中
    * 之前做出的选择，就是path
    * */
    public static void process(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 不要这个字符
        process(str, index + 1, ans, path);

        // 要这个字符
        process(str, index + 1, ans, path + str[index]);
    }


    // 题目二：使用HashSet防止重复字面值
    public static List<String> subsNoRepeat(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();

        process2(chars, 0, set, path);

        // 如果想要list类型的输出，转换一次
        List<String> ans = new ArrayList<>();
        for (String cur : set){
            ans.add(cur);
        }

        return ans;
    }

    public static void process2(char[] str, int index, HashSet<String> set, String path) {
        if (index == str.length) {
            set.add(path);
            return;
        }
        // 不要这个字符
        process2(str, index + 1, set, path);

        // 要这个字符
        process2(str, index + 1, set, path + str[index]);
    }

    public static void main(String[] args) {
        String test = "abc";
        List<String> ans = getAllSubs(test);

        for (String str : ans) {
            System.out.println(str);
        }
    }
}
