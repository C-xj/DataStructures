package Class08_打表技巧和矩阵处理技巧;


/**
 * @author Chu
 * @create 2021-12-16  9:40
 */
/* 打表找规律：
*   1）某个面试题，输入参数类型简单，并且只有一个实际参数
*   2）要求的返回值类型也简单，并且只有一个
*   3）用暴力方法，把输入参数对应的返回值，打印出来看看，进而优化code
*
* 题目一：
*   小虎去买苹果，商店只提供两种类型的塑料袋，每种类型都有任意数量
    ① 能装下6个苹果的袋子
    ② 能装下8个苹果的袋子
    小虎可以自由使用两种袋子来装苹果，但是小虎有强迫症，他要求自己使用的袋子数量必须最少，且使用的每个袋子必须装满。
    给定一个正整数N,返回至少使用多少袋子。如果N无法让使用的每个袋子必须装满，返回-1
*
* */
public class Code01_AppleMinBags {

    public static int minBags(int apple){
        if(apple < 0){
            return -1;
        }
        int bag6 = -1;
        int bag8 = apple / 8;
        int rest = apple - 8 * bag8;
        while (bag8 >= 0 && rest < 24){ // 因为24最大公约数
            int restUse6 = minBagBase6(rest);
            if (restUse6 != -1){
                bag6 = restUse6;
                break;
            }
            rest = apple - 8 * (--bag8);
        }
        return bag6 == -1 ? -1 : bag6 +bag8;
    }


    // 如果剩余苹果rest可以被装6各苹果的袋子搞定，返回袋子数量
    // 不能则返回-1
    public static int minBagBase6(int rest){
        return rest % 6 == 0 ? (rest / 6) : -1;
    }


    // 打表找规律
    public static void main(String[] args) {
        for (int apple = 1; apple <= 100; apple++){
            System.out.println(apple + " : "+minBags(apple));
        }
    }


    // 利用规律输出
    public static int minBagAwesome(int apple){
        if ((apple & 1) != 0){ // 如果是奇数，返回-1
            return -1;
        }
        if (apple < 18){
            return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
                    :  (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
        }
        return (apple - 18) / 8 + 3;

    }


}
