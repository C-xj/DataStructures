package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-09  20:26
 */
/*
* 定义何为step sum
*   比如680，680 + 68 + 6 = 754，680的step sum叫754
*   给定一个正数num，判断它是不是某个数的step sum
*
*  假设一个步骤和(step sum)，那么它的原始数一定是再0-step sum之间，
*       使用二分法进行查找，如果没有一个数是则表示其不是步骤和。
* */
public class Q17_判断一个数字是不是stepsum {

    // 二分查找
    public static boolean isStepSum(int stepSum){
        int L = 0;
        int R = stepSum;
        int M = 0;
        int cur = 0;    // 记录当前数的步骤和
        while (L <= R){
            M = L + ((R - L) >> 1);
            cur = stepSum(M);   // 调用函数计算当前数的步骤和
            if (cur == stepSum){
                return true;
            }else if (cur < stepSum){
                L = M + 1;
            }else {
                R = M - 1;
            }
        }
        return false;
    }

    public static int stepSum(int num){
        int sum = 0;
        while (num != 0){
            sum += num;
            num /= 10;
        }
        return sum;
    }
}
