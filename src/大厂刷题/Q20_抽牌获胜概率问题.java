package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-10  10:57
 */

public class Q20_抽牌获胜概率问题 {

    /*  面值为1~10的牌组成一组
    *   每次你从组里等概率的抽出1~10种的一张，下次抽会换一个新的组，有无限组
    *       当累加和 <17 时，你将一直抽牌；当累加和 >=17 且 <21 时，你将获胜，
    *       当累加和 >=21 时，你将失败
    *   返回获胜的概率
    * */
    public static double f1(){
        return p1(0);
    }

    // 递归函数，当来到cur这个累加和的时候，获胜概率是多少返回！
    public static double p1(int cur){
        if (cur >= 17 && cur < 21){
            return 1.0;
        }
        if (cur >= 21){
            return 0.0;
        }
        double w = 0.0;     // 抽到下一张牌之后的成功概率累加的总点数 除10(10张牌的总量)
        for (int i = 1;i <= 10;i++){
            w += p1(cur + i);
        }
        return w / 10;
    }

    // 拓展
    /*
     * 面值为1~N的牌组成一组
     * 每次你从组里等概率的抽出1~N种的一张，下次抽会换一个新的组，有无限组
     *   当累加和 <a 时，你将一直抽牌；当累加和 >=a 且 <b 时，你将获胜，
     *       当累加和 >=b 时，你将失败
     * 返回获胜的概率，给定的参数为N,a,b
     * */
    public static double f2(int N,int a,int b){
        if (N < 1 || a >= b || a < 0 || b < 0){
            return 0.0;
        }
        if (b - a >= N){
            return 1.0;
        }
        // 所有参数都合法，并且b-a < N
        return p2(0,N,a,b);
    }

    // 递归函数，当来到cur这个累加和的时候，获胜概率是多少返回！
    public static double p2(int cur,int N,int a,int b){
        if (cur >= a && cur < b){
            return 1.0;
        }
        if (cur >= b){
            return 0.0;
        }
        double w = 0.0;     // 抽到下一张牌之后的成功概率累加的总点数 除10(10张牌的总量)
        for (int i = 1;i <= N;i++){
            w += p2(cur + i,N,a,b);
        }
        return w / N;
    }

    // 改进f2
    public static double f3(int N,int a,int b){
        if (N < 1 || a >= b || a < 0 || b < 0){
            return 0.0;
        }
        if (b - a >= N){
            return 1.0;
        }
        // 所有参数都合法，并且b-a < N
        return p3(0,N,a,b);
    }

    // 递归函数，当来到cur这个累加和的时候，获胜概率是多少返回！
    public static double p3(int cur,int N,int a,int b){
        if (cur >= a && cur < b){
            return 1.0;
        }
        if (cur >= b){
            return 0.0;
        }
        if (cur == a - 1){          // 特殊边界情况
            return 1.0 * (b - a) / N;
        }
        // 当来到cur这个累加和的时候，获胜概率的通式
        //   加入当前来到5位置，一共有1-4张牌可选，即可来到6(概率a)、7(b)、8(c)、9(d)四个位置 f(5)概率 = (a + b + c + d) / 4
        //     计算f(4) 即可来到f5(x)、f6(概率a)、f7(b)、f8(c)四个位置
        //     那么 f(4) = [f(5) + f(5) * 4 - f(9)] / 4
        //      f(cur) = [f(cur + 1) + f(cur + 1) * N - f(cur + 1 + N)] / N
        double w =p3(cur + 1,N,a,b) + p3(cur + 1,N,a,b) * N;
        if (cur + 1 + N < b){
            w -= p3(cur + 1 + N,N,a,b);
        }
        return w / N;
    }

    // f3动态规划
    public static double f4(int N,int a,int b){
        if (N < 1 || a >= b || a < 0 || b < 0){
            return 0.0;
        }
        if (b - a >= N){
            return 1.0;
        }
        double[] dp = new double[b];
        for (int i = a; i < b;i++){
            dp[i] = 1.0;
        }
        if (a - 1 >= 0){
            dp[a - 1] = 1.0 * (b - a) / N;
        }
        for (int cur = a - 2;cur >= 0; cur--){
            double w= dp[cur + 1] + dp[cur + 1] * N;
            if (cur + 1 + N < b){
                w -= dp[cur + 1 + N];
            }
            dp[cur] = w / N;
        }
        return dp[0];
    }






}
