package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  10:20
 */
public class Q38_37_解析数独 {

    public static void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] bucket = new boolean[9][10];
        // initMaps哪些位置已经填好
        initMaps(board, row, col, bucket);
        // 递归深度优先遍历
        process(board, 0, 0, row, col, bucket);
    }

    public static void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int bid = 3 * (i / 3) + (j / 3);
                if (board[i][j] != '.') {
                    int num = board[i][j] - '0';
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
    }

    // 当前来到(i,j)这个位置，如果已经有数字，跳到下一个位置上(先从左往右，再新的一行从左往右)
    //                     如果没有数字，尝试1~9，不能和row、col、bucket冲突
    public static boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
        if (i == 9) {      // 说明来到了终止位置
            return true;
        }
        // 当离开(i，j),应该去哪(nexti,nextj) 下一个位置上(先从左往右，再新的一行从左往右)
        int nexti = j != 8 ? i : i + 1;
        int nextj = j != 8 ? j + 1 : 0;
        if (board[i][j] != '.') {
            return process(board, nexti, nextj, row, col, bucket);
        } else {
            int bid = 3 * (i / 3) + (j / 3);
            for (int num = 1; num <= 9; num++) {    // 尝试每一个数字1~9
                if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
                    // 可以尝试num
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                    // 把尝试的数加入board中，
                    board[i][j] = (char) (num + '0');

                    // 然后深度优先遍历
                    if (process(board, nexti, nextj, row, col, bucket)) {
                        return true;
                    }

                    // 恢复现场，不影响下一个数字的判断(即开始此位置的下一个可用的num赋值，深度遍历)
                    row[i][num] = false;
                    col[j][num] = false;
                    bucket[bid][num] = false;
                    board[i][j] = '.';
                }
            }
            return false;
        }
    }

}
