package 大厂刷题;

import java.util.HashMap;

/**
 * @author Chu
 * @create 2022-02-15  14:37
 */
/* 来自京东
*  把一个01字符串切成多个部分，要求每一部分的0和1比例一样，同时要求尽可能多的划分
*
*   比如：01010101
*   01 01 01 01 这是一种切法，0和1比例为 1:1
*   0101 0101 也是一种切法，0和1比例为 1:1
*   两种切法都符合要求，但是那么尽可能多的划分为第一种切法，部分数为4
*
*   比如：00001111
*   只有一种切法就是00001111整体作为一块，那么尽可能多的划分，部分数为1
*
*   给定一个01字符串str，假设长度为N，要求返回一个长度为N的数组ans
*   其中ans[i] = str[0...i]这个前缀串，要求每一部分的0和1比例一样，同时要求尽可能多的划分下，部分数是多少？
*   输入：str = "010100001"   ，第一个位置只有0，0和1的比例为1:0
*   输出：ans = [1,1,1,2,1,2,1,1,3]
*
*   关键点：
*       每个前缀串：  部分比例关系 == 整体比例关系
*
*   直线同点：
*       map中：key用分数记录比例关系，value记录前缀出现多少次相同比例
*
* 此题：
*   map{key,map} map嵌套
*      {3,{7 ,5}}  ---  3:7的关系前缀一共有5个
*
*   注意：要的比例关系，要求最大公约数
*
*
* */
public class Q55_149_直线最多的点数_最大公约数_前缀01串切成等比例的最大部分数 {

    public static int[] split(int[] arr){

        // key：分子
        // value：属于key的分母表  每一个分母，及其 分子/分母 这个比例。多少个前缀拥有
        HashMap<Integer, HashMap<Integer, Integer>> pre = new HashMap<>();
        int n = arr.length;
        int[] ans = new int[n];     // 记录每个前缀的最大部分数
        int zero = 0;               // 0出现的次数
        int one = 0;                // 1出现的次数
        for (int i = 0;i < n;i++){  // i表示每个前缀串的尾部
            if (arr[i] == 0){
                zero++;
            }else {
                one++;
            }
            if (zero == 0 || one == 0){         // 如果当前前缀串只有0或1一种,
                ans[i] = i + 1;                 // 那每一个就是一部分，一共 i + 1 个部分数
            }else {                             // 0和1都有数量 -> 最简分数(比例关系)
                int gcd = gcd(zero,one);        // gcd函数是求最大公约数的函数
                int a = zero / gcd;             // 分子
                int b = one / gcd;              // 分母

                /*  每个前缀串：  部分比例关系 == 整体比例关系  */
                // a / b 比例(当前前缀串整体01比例关系)，在当前前缀串中，之前有多少前缀拥有
                if (!pre.containsKey(a)){       // 没有就去建表
                    pre.put(a,new HashMap<>());
                }
                if (!pre.get(a).containsKey(b)){
                    pre.get(a).put(b,1);
                }else {                         // 有，则 + 1
                    pre.get(a).put(b,pre.get(a).get(b) + 1);
                }
                ans[i] = pre.get(a).get(b);
            }
        }
        return ans;
    }


    /* 辗转相除法(欧几里得算法)求最大公约数:
    *          gcd(a,b)=gcd(b,a mod b).
    *
    * 两个整正数a和b(假设a>b)，它们的最大公约数等于a除以b的余数c和b之间的最大公约数
    *    举例来说，25和10的最大公约数，（25%10=5） 等于5和10的最大公约数
    * */
    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


}
