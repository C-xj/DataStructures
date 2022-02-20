package Class08_打表技巧和矩阵处理技巧;

/**
 * @author Chu
 * @create 2021-12-16  10:08
 */
/* 打表题目二：
    * 给定一个正整数N,表示有N份青草统一堆放在仓库里
        有一只牛和一只羊，牛先吃，羊后吃，它俩轮流吃草
        不管是牛还是羊，每一轮能吃的草量必须是
        1,4,16,64...(4的某次方)
        谁最先把草吃完，谁获胜
        假设牛和羊都绝顶聪明，都想，都会做出理性的决定
        根据唯一的参数N,返回谁会赢
    * */
public class Code02_EatGrass {

    // n份草放一堆
    public static String winner1(int n){
        // 0  1  2  3  4
        // 后 先 后 先 先
        if (n < 5){
           return (n == 0 || n == 2) ? "后手" : "先手";
        }
        // n >= 5 时
        int base = 1; // 当前先手决定吃的草数
        // 当前时先手在选
        while (base <= n){
            // 当前一共n份草，先手吃掉base份，n-base是留给后手的
            // 母过程先手
            // 在子过程里是后手
            if (winner1(n - base).equals("后手")){ // 下一个调用中的后手，就是当前函数中的先手，-一个人
                return "先手";
            }
            if (base > n / 4){  // 防止base*4之后溢出
                break;
            }
            base *= 4;
        }
        return "后手";
    }


    // 打表找规律
    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++){
            System.out.println(i + " : "+ winner1(i));
        }
    }


    // 利用规律输出
    public static String winner2(int n){
        if (n % 5 == 0 || n % 5 == 2){
            return "后手";
        }else {
            return "先手";
        }
    }

}
