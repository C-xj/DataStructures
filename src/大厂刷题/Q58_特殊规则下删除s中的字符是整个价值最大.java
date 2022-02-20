package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-15  21:59
 */
/* 来自腾讯
*   给定一个只有0和1组成的字符串S，假设下标从1开始，规定i位置的字符价值V[i]计算方式如下：
*   1）i == 1时，V[i] = 1
*   2）i > 1时，如果S[i] != S[i - 1], V[i] = 1
*   3）i > 1时，如果S[i] == S[i - 1], V[i] = V[i] + 1
*   你可以随意删除S中的字符，返回整个S的最大价值
*   字符串长度<=5000
*
*   从左往右的尝试模型：
*       ① 要当前字符：
*       ② 不要当前字符：
*
* */
public class Q58_特殊规则下删除s中的字符是整个价值最大 {

    public static int max1(String s){
        if (s == null || s.length() == 0){
            return 0;
        }
        char[] str = s.toCharArray();
        int[] arr = new int[str.length];
        for (int  i = 0;i < arr.length;i++){
            arr[i] = str[i] == '0' ? 0 : 1;     // arr记录索引位置是0/1
        }
        return process1(arr,0,0,0);
    }

    // 递归含义：
    /*
    * 目前再str[index...]上做选择，str[index...]的左边，最近的数字是lastNum
    * 并且lastNum所带的价值，已经拉高到baseValue
    * 返回在str[index...]上做选择，最终获得的最大价值
    * index -> 0 ~ 4999
    * lastNum -> 0 or 1
    * baseValue -> 1 ~ 5000
    * 5000 * 2 * 5000 -> 5 * 10^7(过！)
    * */
    public static int process1(int[] arr,int index,int lastNum,int baseValue){
        if (index == arr.length){
            return 0;
        }
        // 判断来的位置index是否与前一个数字相等，相等时：当前价值在原基础上+1，否则为1
        int curValue = lastNum == arr[index] ? (baseValue + 1) : 1;
        // 情况①：要当前位置字符
        int next1 = process1(arr,index + 1,arr[index],curValue);
        // 情况②：不要当前位置字符
        int next2 = process1(arr,index + 1,lastNum,baseValue);
        // 返回最大价值，curValue + next1是保留了当前位置的字符，其当前价值curValue基础上 + 后面返回的next1
        return Math.max(curValue + next1,next2);
    }


    // 动态规划：

}
