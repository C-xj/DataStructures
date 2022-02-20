package Class08_打表技巧和矩阵处理技巧;

/**
 * @author Chu
 * @create 2021-12-16  10:32
 */
/*打表题目三：

    定义一种数：可以表示成若干(数量>1)连续正数和的数
    比如
    5=2+3,5就是这样的数
    12=3+4+5,12就是这样的数
    1不是这样的数，因为要求数量大于1个、连续正数和
    2=1+1,2也不是，因为等号右边不是连续正数
    给定一个参数N,返回是不是可以表示成若干连续正数和的数
*
* */
public class Code03_MSumToN {

    public static boolean isMSum1(int num){
        for (int i = 1; i <= num ; i++){
            int sum = i;
            for (int j = i + 1;j <= num ; j++){
                if (sum + j > num){
                    break;
                }
                if (sum + j == num){
                    return true;
                }
                sum += j;
            }
        }
        return false;
    }

    // 暴力打表找规律
    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++){
            System.out.println(i + " : "+ isMSum1(i));
        }
    }


    // 使用规律
    public static boolean isMSum2(int num){
        if (num < 3){
            return false;
        }
        // (num & (num -1)) == 0 是2的某次方
        return (num & (num -1)) != 0; // 如果不是2的次幂就返回true，否则返回false
    }

}

