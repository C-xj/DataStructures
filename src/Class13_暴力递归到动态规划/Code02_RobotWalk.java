package Class13_暴力递归到动态规划;
/*
 * 假设有排成一行的N个位置，记为1~N，N一定大于或等于2
 * 开始时机器人在其中的M位置上(M一定时1~N中的一个)
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能来到N-1位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * 规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * 给定四个参数N、M、K、P，返回方法数
 *
 * */
public class Code02_RobotWalk {

    // 暴力递归 ---存在重复计算
    public static int ways1(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 总共N个位置，从M点出发，还剩K步，返回最终能达到P的方法数
        return walk(N, M, K, P);
    }

    // N : 位置为1 ~ N，固定参数
    // cur : 当前在cur位置，可变参数
    // rest : 还剩res步没有走，可变参数
    // P : 最终目标位置是P，固定参数
    // 该函数的含义：只能在1~N这些位置上移动，当前在cur位置，走完rest步之后，停在P位置的方法数作为返回值返回
    public static int walk(int N, int cur, int rest, int P) {
        // 如果没有剩余步数了，当前的cur位置就是最后的位置
        // 如果最后的位置停在P上，那么之前做的移动是有效的
        // 如果最后的位置没在P上，那么之前做的移动是无效的
        if (rest == 0) {    // 没有步数后，来到目标位置返回1(表示一种方法)，否则返回0
            return cur == P ? 1 : 0;
        }
        // 如果还有rest步要走，而当前的cur位置在1位置上，那么当前这步只能从1走向2
        // 后续的过程就是，来到2位置上，还剩rest-1步要走
        if (cur == 1) {
            return walk(N, 2, rest - 1, P);
        }
        // 如果还有rest步要走，而当前的cur位置在N位置上，那么当前这步只能从N走向N-1
        // 后续的过程就是，来到N-1位置上，还剩rest-1步要走
        if (cur == N) {
            return walk(N, N - 1, rest - 1, P);
        }

        // 如果还有rest步要走，而当前的cur位置在中间位置上，那么当前这步可以走向左，也可以走向右
        // 走向左之后，后续的过程就是，来到cur-1位置上，还剩rest-1步要走
        // 走向右之后，后续的过程就是，来到cur+1位置上，还剩rest-1步要走
        // 走向左、走向右是截然不同的方法，所以总方法数要都算上
        return walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
    }


    // 优化 ---递归加缓存(动态规划)
    public static int waysCache(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 当前位置可以到达的地方 和 剩下多少步  组成的一个二维数组
        int[][] dp = new int[N + 1][K + 1];
        for (int row = 0; row <= N ;row++){
            for (int col = 0; col <= K ;col++){
                dp[row][col] = -1;
            }
        }

        return walkCache(N, M, K, P,dp);
    }

    // 把所有cur和rest的组合，返回的结果，加入到缓存里
    public static int walkCache(int N, int cur, int rest, int P,int[][] dp) {
        if (dp[cur][rest] != -1){  // 表示之前出现过这种情况
            return dp[cur][rest];
        }

        if (rest == 0) {
            dp[cur][rest] = cur == P ? 1 : 0;
            return dp[cur][rest];
        }
        if (cur == 1) {
            dp[cur][rest] = walk(N, 2, rest - 1, P);
            return dp[cur][rest];
        }
        if (cur == N) {
            dp[cur][rest] = walk(N, N - 1, rest - 1, P);
            return dp[cur][rest];
        }
        dp[cur][rest] = walk(N, cur + 1, rest - 1, P) + walk(N, cur - 1, rest - 1, P);
        return dp[cur][rest];
    }


    /** 动态规划  ---把参数组合玩成一个结构化的缓存
     * 画出二维数组，推导出每个位置依赖关系，
     * @param N 总共有多少个位置 1...N 7
     * @param M 初始所在的位置 4
     * @param K 必须要走多少步 9
     * @param P 最终到达的位置 5
     * @return 返回所有的方法数
     */
    public static int ways2(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        // 剩下多少步 和 当前位置可以到达的地方 组成的一个二维数组
        int[][] dp = new int[K + 1][N + 1];

        // 剩下0步，当前位置是P是一种走法
        dp[0][P] = 1;

        // 剩下多少步
        for (int i = 1; i <= K; i++) {
            // 当前位置
            for (int j = 1; j <= N; j++) {
                if (j == 1) {
                    // 当前位置是1 只能往2走
                    dp[i][j] = dp[i - 1][2];
                } else if (j == N) {
                    // 当前位置是N ，只能往N-1走
                    dp[i][j] = dp[i - 1][N - 1];
                } else {
                    // 当前位置在中间
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j + 1];
                }
            }
        }
        return dp[K][M];
    }

    // 动态规划 2 ,二维数组的行列交换
    public static int ways5(int N, int start, int K, int end) {
        // 参数无效直接返回0
        if (N < 2 || end < 1 || end > N || start < 1 || start > N || K < 0) {
            return 0;
        }
        // 当前位置 -> 剩下多少步
        int[][] dp = new int[N + 1][K + 1];

        // 第0列
        // dp[][0];
        dp[end][0] = 1;
        for (int col = 1; col <= K; col++) {
            dp[1][col] = dp[2][col - 1];
            dp[N][col] = dp[N - 1][col - 1];
            for (int row = 2; row < N; row++) {
                dp[row][col] = dp[row - 1][col - 1] + dp[row + 1][col - 1];
            }
        }

        return dp[start][K];
    }


    public static int ways3(int N, int M, int K, int P) {
        // 参数无效直接返回0
        if (N < 2 || K < 1 || M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        int[] dp = new int[N + 1];
        dp[P] = 1;
        for (int i = 1; i <= K; i++) {
            int leftUp = dp[1];// 左上角的值
            for (int j = 1; j <= N; j++) {
                int tmp = dp[j];
                if (j == 1) {
                    dp[j] = dp[j + 1];
                } else if (j == N) {
                    dp[j] = leftUp;
                } else {
                    dp[j] = leftUp + dp[j + 1];
                }
                leftUp = tmp;
            }
        }
        return dp[M];
    }

    public static void main(String[] args) {
        System.out.println(ways1(7, 4, 9, 5));
        System.out.println(ways2(7, 4, 9, 5));
        System.out.println(ways3(7, 4, 9, 5));
        System.out.println(ways5(7, 4, 9, 5));
    }

}
