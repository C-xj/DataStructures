package Class12_暴力递归;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *给定一个非空的字符串s，一个包含单词的数组arr，判断s是否可以被arr种的单词拼装组成，arr中的单词可以重复使用
 *      例如：
 *          s = "leetcode",arr = ["leet","code"]
 *          返回：true
*           s = "catsandog",arr = ["cats"."dog","sand","and","cat"]
 *          返回：false
 */

//-------------------------------暴力递归------------------------------
public class Code09_WordBreak {

    public static List<String> wordBreak1(String s,List<String> wordDict){
        HashSet<String> set = new HashSet<>(wordDict);
        LinkedList<String> path = new LinkedList<>();
        ArrayList<String> res = new ArrayList<>();
        process(s,0,set,path,res);
        return res;
    }

    // 从s的i位置出发，s[i...]这一段字符串，被字典所有组成的方案返回
    // 沿途做的决定放在path里
    // 如果找到某个组成方式，把它加入到结果序列中
    public static void process(String s,int i,HashSet<String> set,LinkedList<String> path,ArrayList<String> res){
        if (i == s.length()){   // 到终止位置，收集到答案
            res.add(getString(path));
        }else {
            // s[i..k]
            for(int k = i ; k < s.length();k++){
                // 如果i到k这一段可以被字典中的搞定，就加入路径，然后递归分析后面
                if(set.contains(s.substring(i,k+1))){   // [i,k+1]
                    path.add(s.substring(i,k+1));
                    process(s,k+1,set,path,res);
                    path.pollLast();    // 弹出，恢复现场，以免影响后面的选择
                }
            }
        }
    }

    public static String getString(LinkedList<String> path){
        StringBuilder res = new StringBuilder();
        for (String str : path){
            res.append(str + " ");
        }
        // 不要最后一个空格
        return res.substring(0,res.length()-1);
    }

}
