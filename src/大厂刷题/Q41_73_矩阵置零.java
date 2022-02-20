package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-13  14:31
 */
/*
* 给定一个 m x n 的矩阵，如果一个元素为 0 ，则将其所在行和列的所有元素都设为 0 。
*
*   请使用 原地 算法。O(1)
*
*   O(m+n)是创建两个数组记录每行每列是否要变0
*   public void setZeroes(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m];
        boolean[] col = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = col[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || col[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

*
*   边遍历边更新是不行的，
*
* */
public class Q41_73_矩阵置零 {

    // O(1)
    // 用第0行和第0列，记录哪一行哪一列需要变0
    public static void setZeroes1(int[][] matrix) {
        boolean row0Zero = false;   //  第0行最后是否要全变为0
        boolean col0Zero = false;   //  第0列最后是否要全变为0

        for (int i = 0; i < matrix[0].length; i++) {
            if (matrix[0][i] == 0) {
                row0Zero = true;     // 如果第0行有0记录下来，最后再全变为0
                break;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0Zero = true;     // 如果第0列有0记录下来，最后再全变为0
                break;
            }
        }
        // 遍历每个位置，如果当前位置0，将其行的第一个数，其列的第一个数置为0(记录此行此列要都置为0)
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        // 再次遍历，将0对应的行和列的位置置为0
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        // 最后判断第0行和第0列是否需要全变为0
        if (row0Zero) {
            for (int i = 0; i < matrix[0].length; i++) {
                matrix[0][i] = 0;
            }
        }
        if (col0Zero) {
            for (int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // O(1)
    /*
    * 只使用一个标记变量记录第一列是否原本存在 0。这样，第一列的第一个元素即可以标记第一行是否出现 0。
    *   但为了防止每一列的第一个元素被提前更新，我们需要从最后一行开始，倒序地处理矩阵元素。
    * */
    public static void setZeroes2(int[][] matrix) {
        boolean col0 = false;

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][0] == 0) {
                col0 = true;
            }
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = matrix[0][j] = 0;
                }
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
            if (col0) {
                matrix[i][0] = 0;
            }
        }
    }

}
