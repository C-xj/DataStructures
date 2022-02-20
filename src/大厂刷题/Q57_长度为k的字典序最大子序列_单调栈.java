package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-15  21:06
 */
/* 来自腾讯
*   给定一个字符串str，和一个正数k
*   返回长度为k的所有子序列中，字典序最大的子序列(非连续，与子串不同，子串是连续的)
*
*   单调栈：(栈内元素具有单调性) ---此题是字符字典序从大到小单调
*
*       例如：bcdcabc...  一共n个字符       k=5
*         压入栈前提：后进来的字符只能压在 >= 自己的字符上，否则要弹出   因此上述压入dcc...
*           满足压栈条件后：
*               情况① 可以来到str的最后，压入的字符数量 > k ;
*                                   则取前 k 个
*               情况② 假设来到判断的位置i及其之后的字符总数b(n - i)个，
*                       i位置的字符要压入栈中，满足压栈条件后(即把小的都弹出了)，
*                           此时栈中还有a个字符，剩下的 a + b == k ;
*                                   则取 a + b 个
*
* */
public class Q57_长度为k的字典序最大子序列_单调栈 {

    public static String maxString(String s,int k){
        if (k <= 0 || s.length() < k){      // 如果k的要求<=0，或整个字符串长度<k，则返回"";
            return "";
        }
        char[] str = s.toCharArray();
        int n = str.length;
        char[] stack = new char[n];         // 模拟单调栈：后面的可以覆盖之前的字符(模拟弹出和新压入)
        int size = 0;                       // 记录栈内字符个数
        for (int i = 0; i < n; i++){
            // 不满足压栈条件，弹出栈顶元素
            //      新压入的字符str[i] 要小于(<) 之前栈顶的字符stack[size - 1]，否则要弹出
            while (size > 0 && stack[size - 1] < str[i] && size + n - i > k){
                size--;
            }
            // 当前i位置字符满足压栈条件下：情况②
            if (size + n - i == k){
                // 返回栈中字符，以及i位置及其后续所有字符
                return String.valueOf(stack,0,size) + s.substring(i);
            }
            // 当前i位置字符满足压栈条件下：情况①
            stack[size++] = str[i];
        }
        // 返回前k个
        return String.valueOf(stack,0,k);
    }


}
