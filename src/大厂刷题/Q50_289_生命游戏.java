package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-14  15:33
 */
/*
* 给定一个包含 m × n个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态： 1 即为 活细胞 （live），或 0 即为 死细胞 （dead）。
*   每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：

        1、如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
        2、如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
        3、如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
        4、如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
    下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。

*   原地算法解决本题吗？请注意，面板上所有格子需要同时被更新：你不能先更新某些格子，然后使用它们的更新后的值再更新其他格子。

*
* */
public class Q50_289_生命游戏 {
    // 利用int 32位
    public static void gameOfLife(int[][] board) {
        int N = board.length;
        int M = board[0].length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int neighbors = neighbors(board, i, j);
                if (neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) { // 存活
                    board[i][j] |= 2;   // 从右边数把第二位(00000010 -> 2)的状态标1(与2做或运算)，表示存活 ，死亡不做任何操作
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] >>= 1;      // 右移一位，之前第二位的移动到第一位，表示现在的生死情况
            }
        }
    }

    // board[i][j] 这个位置的数，周围有几个1
    public static int neighbors(int[][] board, int i, int j) {
        int count = 0;
        count += ok(board, i - 1, j - 1);
        count += ok(board, i - 1, j);
        count += ok(board, i - 1, j + 1);
        count += ok(board, i, j - 1);
        count += ok(board, i, j + 1);
        count += ok(board, i + 1, j - 1);
        count += ok(board, i + 1, j);
        count += ok(board, i + 1, j + 1);
        return count;
    }

    // board[i][j]上面有1，就返回1，上面不是1，就返回0
    public static int ok(int[][] board, int i, int j) {
        return (i >= 0 && i < board.length && j >= 0 && j < board[0].length && (board[i][j] & 1) == 1) ? 1 : 0;
    }

}
