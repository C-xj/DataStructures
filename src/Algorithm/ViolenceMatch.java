package Algorithm;

import java.util.Map;

/**
 * @author Chu
 * @create 2021-05-21  18:56
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        // 测试暴力匹配算法
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int index=violenceMatch(str1,str2);
        System.out.println("index="+index);
    }

    public static int violenceMatch(String str1,String str2){
        char[] s1=str1.toCharArray();
        char[] s2=str2.toCharArray();

        int i=0,j=0;  //i索引指向s1，j索引指向s2

        while (i<s1.length && j< s2.length){
            if (s1[i]==s2[j]){  // 匹配成功
                i++;        // 继续比较后续字符
                j++;
            }else {
                            // 检查下一个字串
                i=i-(j-1);  // i和j同增，匹配失败，把i退回上次位置的下一下位置
                j=0;
            }
        }
        if(j==s2.length){
            return i-j;   //如果全部匹配（最后匹配成功j++）索引会等于s2.length，匹配成功
        }else {         // 匹配失败
            return 0;
        }
    }
}
