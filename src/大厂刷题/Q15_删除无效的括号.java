package 大厂刷题;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chu
 * @create 2022-01-07  14:43
 */
/*
* 给你一个由若干括号和字母组成的字符串 s ，删除最小数量的无效括号，使得输入的字符串有效。
    返回所有可能的结果。答案可以按 任意顺序 返回。
* */
public class Q15_删除无效的括号 {

    // 剪枝
    public static List<String> removeInvalidParentheses(String s){
        ArrayList<String> ans = new ArrayList<>();
        remove(s,ans,0,0,new char[] {'(',')'});
        return ans;
    }

    /*
    * modifyIndex <= checkIndex
    * 只查s[checkIndex...]的部分，因为之前的一定已经调整对了
    * 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
    * 比如：
    *   ( ( ) ( ) ) )...
    *   0 1 2 3 4 5 6
    *  一开始当然checkIndex = 0，modifyIndex = 0
    * 当查到6的时候，发现不对了，然后可以去掉2位置、4位置的 )，都可以
    * 然后可以去掉2位置的 ),那么下一步就是
    *   ( ( ( ) ) )...
    *   0 1 2 3 4 5 6
    * checkIndex = 6,modifyIndex = 2;
    * 如果去掉4位置的 )，那么下一步就是
    *   ( ( ) ( ) )...
    *   0 1 2 3 4 5 6
    * checkIndex = 6,modifyIndex = 4
    * 也就是说
    * checkIndex和modifyIndex，分别表示查的开始和调的开始，之前的都不用管了
    * */
    public static void remove(String s,List<String> ans,int checkIndex,int modifyIndex,char[] par){
        for(int stack = 0,i = checkIndex; i < s.length(); ++i){
            if (s.charAt(i) == par[0]){     // stack
                stack++;
            }
            if (s.charAt(i) == par[1]){
                stack--;
            }
            // 只要有违规就把前缀调整对，而且没有其他分支
            // i是stack小于0的第一个位置(也是当前检查到的位置) （默认右括号比左括号多）
            if (stack < 0){
                for(int j = modifyIndex;j <= i; ++j){
                    //  删除操作，
                    //      ① 来到 ')' 且 是可以删除位置
                    //      ② 来到 ')' 且 与上一个字符是'('
                    if (s.charAt(j) == par[1] && (j == modifyIndex || s.charAt(j - 1) != par[1])){
                        // s.substring(0,j) + s.substring(j + 1,s.length()) 字符串重新组合---删除j位置字符
                        remove(s.substring(0,j) + s.substring(j + 1,s.length()),ans,i ,j,par);
                    }
                }
                return;     //(i是stack小于0的第一个位置(也是当前检查到的位置),到此就return返回了，只检查到i,剩余的交给上面递归子函数)
            }
        }
        // 当左括号比右括号多的时候，整个字符串逆序，遍历时par[0] == ')'右括号++，左括号--，删掉多余的左括号
        String reversed = new StringBuilder(s).reverse().toString();
        if (par[0] == '('){
            remove(reversed,ans,0,0,new char[] {')','('});
        }else {     // 左括号多执行后是，par[0] == ')'
            // 右括号多和左括号多，两种情况都已经考虑完了，可以添加进答案集中。
            ans.add(reversed);
        }
    }


}
