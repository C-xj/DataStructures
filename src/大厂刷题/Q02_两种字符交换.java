package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-04  17:00
 */
/* 题目4
*    一个字符数组/字符串中只有两种字符'G'和'B'，想让所有的G都放在左侧，所有的B都放在右侧,或者所有的B都放在左侧，所有的G都放在右侧
*       但是只能在相邻字符之间进行交换操作，返回至少需要交换几次
*
*   贪心，滑动窗口：第0个G(即第一个出现的G)直放在数组下标0的位置... 即，后面的G没有必要跑到前面的G之前
*       定义一个gi(记录G要换到的位置)，一个i(寻找G)，gi先不动，i找G，之后进行交换(索引相减是交换的次数)
*           然后gi + 1，i继续寻找G......
* */
public class Q02_两种字符交换 {

    public static int minSteps1(String s){
        if (s == null || s.equals("")){
            return 0;
        }
        char[] str = s.toCharArray();

        int step1 = 0;
        int gi = 0;
        for(int i = 0;i < str.length;i++){
            if (str[i] == 'G'){
                step1 += i - (gi++);
            }
        }

        int step2 = 0;
        int bi= 0;
        for(int i = 0;i < str.length;i++){
            if (str[i] == 'B'){
                step2 += i - (bi++);
            }
        }
        // 取两种方式的最小交换次数
        return Math.min(step1,step2);
    }


    public static int minSteps2(String s){
        if (s == null || s.equals("")){
            return 0;
        }
        char[] str = s.toCharArray();
        int step1 = 0;
        int gi = 0;
        int step2 = 0;
        int bi= 0;
        for(int i = 0;i < str.length;i++){
            if (str[i] == 'G'){
                step1 += i - (gi++);
            }else {
                step2 += i - (bi++);
            }
        }
        return Math.min(step1,step2);
    }



}


