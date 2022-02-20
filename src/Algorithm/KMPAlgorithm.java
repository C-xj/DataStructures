package Algorithm;

/**
 * @author Chu
 * @create 2021-05-21  19:53
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1="BBC ABCDAB ABCDABCDABDE";
        String str2="ABCDABD";

        //int[] next=kmpNext(str2);

        //int index =kmpSearch(str1,str2,next);
        //System.out.println("index="+index);


        // 测试 甩手掌柜凡三岁
        int index = IndexKmp(str1, str2);
        System.out.println("index="+index);
    }

    // 尚硅谷求Next数组
    /*// 获取到一个字符串（str2的字串的部分匹配值）
    public static int[] kmpNext(String dest){
        // 创建一个next数组保存部分匹配值
        int[] next = new int[dest.length()];
        next[0]=0;  // 如果在字符串长度为1（即在匹配时第一个位置就失败），
                            // 部分匹配值就是0
        for (int i=1,j=0;i<dest.length();i++){
            // 当dest.charAt(i)！=dest.charAt(j)时，需要从next[j-1]获取
            //  新的j直到发现有dest.charAt（i）==dest.charAt(j)成立退出
            while (j>0 && dest.charAt(i)!=dest.charAt(j)){
                // 这是KMP算法的核心点
                j=next[j-1];
            }

            // 当dest.charAt(i)==dest.charAt(j)满足时，部分匹配值就+1
            if (dest.charAt(i)==dest.charAt(j)){
                j++;
            }
            next[i]=j;
        }
        return next;
    }

    //kmp算法
    *//*
    * str1主字符串
    * str2 模式串
    * next部分匹配表，是模式串得到部分匹配表
    * 返回-1就是没有匹配到。否则返回第一个匹配的位置
    * *//*
    public static int kmpSearch(String str1, String str2, int[] next) {
        // 遍历
        for (int i=0,j=0;i<str1.length();i++){
            //需要处理str1.charAt(i)!=str2.charAt(j)，去调整j的大小
            // KMP算法核心点
            while (j>0 && str1.charAt(i)!=str2.charAt(j)){
                j=next[j-1];
            }

            if(str1.charAt(i)==str2.charAt(j)){
                j++;
            }
            if (j==str2.length()){  // 匹配成功
                return i-(j-1);
            }

        }
        return -1;
    }
*/

    /*// 正月点灯笼求Next数组  与尚硅谷道理相同
    public static int[] prefixTable(String dest){
        int[] next = new int[dest.length()];
        next[0]=0;
        int len=0; // 前一个子串最后位置匹配失败的最大重合长度
        int i=1;
        while (i<dest.length()){
            if (dest.charAt(i)==dest.charAt(len)){
                len++;
                next[i]=len;
                i++;
            }
            else {
                if(len>0){
                    len=next[len-1];
                }
                else {
                    next[i]=len;
                    i++;
                }
            }
        }
        return next;
    }

    public void movePrefixTable(int next[],int n){
        int i;
        for (i=n-1;i>0;i--){
            next[i]=next[i-1];
        }
        next[0]=-1;
    }*/


    // 甩手掌柜凡三岁 求next数组   运用原理与上面两者不同
    public static int[] KmpNext(String str){    // 求模式串的Next数组
        int[] next = new int[str.length()];   // 创建一个模式串长度的next数组
        int j=-1;         //初始模式串子串前后缀最大重合长度为-1
        int i=0;            // 从模式串第一个元素（i=0）开始遍历
        next[0]=-1;         // 定义模式串i=0位置元素之前的子串的前后缀最大重合长度为-1
        while (i<str.length()-1){ // 相当于上面两种的移位，故最后一个的不需要
            if (j==-1 || str.charAt(i)==str.charAt(j)){  // i从0开始，这里j的值第一次出现是i前一位对应的前后缀最大重合长度
                next[++i] = ++j;
            }else {             //如果不相等 把j移回到next[j]位置，
                                // 再进循环判断，
                j=next[j];
            }
        }
        return next;
    }

    // 求next数组优化
    public static int[] KmpNext2(String str){    // 求模式串的Next数组
        int[] next = new int[str.length()];   // 创建一个模式串长度的next数组
        int j=-1;         //初始模式串子串前后缀最大重合长度为-1
        int i=0;            // 从模式串第一个元素（i=0）开始遍历
        next[0]=-1;         // 定义模式串i=0位置元素之前的子串的前后缀最大重合长度为-1
        while (i<str.length()-1){ // 相当于上面两种的移位，故最后一个的不需要
            if (j==-1 || str.charAt(i)==str.charAt(j)){  // i从0开始，这里j的值第一次出现是i前一位对应的前后缀最大重合长度
                i++;j++;
                if(str.charAt(i)==str.charAt(j)){
                    next[i]=next[j];
                }
                else{
                    next[i]=j;
                }
            }else {             //如果不相等 把j移回到next[j]位置，
                // 再进循环判断，
                j=next[j];
            }
        }
        return next;
    }


    // KMP算法
    public static int IndexKmp(String str1,String str2){
        char[] s1=str1.toCharArray();
        char[] s2=str2.toCharArray();

        int i=0,j=0;  //i索引指向s1，j索引指向s2

        int [] next=KmpNext2(str2); //获取模式串str2的next数组
        while (i< s1.length && j< s2.length){
            if (j==-1 || s1[i]==s2[j]){
                i++;
                j++;
            }else {
                j=next[j];    // 匹配失败j移回到其对应的next数组应该的位置
            }
        }
        if (j == s2.length){   // 匹配到最后一个（s2.length-1）也成功j++后
                            // j==s2.length  全部匹配成功
            return i-j;   // 返回第一次匹配成功的位置
        }else {
            return 0;    // 返回0则匹配失败
        }
    }

}
