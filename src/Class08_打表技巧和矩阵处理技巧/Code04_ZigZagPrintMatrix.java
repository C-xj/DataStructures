package Class08_打表技巧和矩阵处理技巧;

/**
 * @author Chu
 * @create 2021-12-16  11:36
 *//* 矩阵处理技巧
 *   1）zigzag打印矩阵
 *   2）转圈打印矩阵
 *   3）原地旋转正方形矩阵
 * 核心技巧：找到coding上的宏观调度
 *
 * */
    // 题目一：zigzag打印矩阵
//           取两个0,0点 T、D，每次各移动一个位置，锁定每一条线，完成对应的打印
public class Code04_ZigZagPrintMatrix {

    public static void printMatrixZigZag(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;     // 是不是从右上往左下打印
        while (tR != endR + 1) {
            // 告诉斜线的两端T和D，方向也告诉，可以去打印
            printLevel(matrix, tR, tC, dR, dC, fromUp);
            // T点先右移(数组的列数)，到最后位置，再向下移
            tR = tC == endC ? tR + 1 : tR;
            tC = tC == endC ? tC : tC + 1;
            // D点先下移(数组的行数)...
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
                                  boolean f) {
        if (f) {
            while (tR != dR + 1) {
                System.out.print(m[tR++][tC--] + " ");
            }
        } else {
            while (dR != tR - 1) {
                System.out.print(m[dR--][dC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        printMatrixZigZag(matrix);

    }
}
