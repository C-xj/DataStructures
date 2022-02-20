package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  10:07
 */
/*
* 九行\九列\九个桶，a[i][j]表示第i行j这个数字有没有出现，同理b[i][j] c[i][j]
* 一个有效的数独（部分已被填充）不一定是可解的。只需要根据以上规则，验证已经填入的数字是否有效即可。空白格用 '.' 表示。
* */
public class Q37_36_有效的数独 {

    public static boolean isValidSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] bucket = new boolean[9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {

                // (i,j) 表示每一个格子
                // bid 表示桶的编号
                int bid = 3 * (i / 3) + (j / 3);
                if (board[i][j] != '.') {
                    // '1' ~ '9'
                    int num = board[i][j] - '0';
                    // 如果有一个出现当前数字就无效
                    if (row[i][num] || col[j][num] || bucket[bid][num]) {
                        return false;
                    }
                    row[i][num] = true;
                    col[j][num] = true;
                    bucket[bid][num] = true;
                }
            }
        }
        return true;
    }

}
