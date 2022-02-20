package Class08_打表技巧和矩阵处理技巧;

// 题目三：3）原地旋转正方形矩阵 90°
public class Code06_RotateMatrix {

	public static void rotate(int[][] matrix) {
		// a\b是 T  c/d是 D
		int a = 0;
		int b = 0;
		int c = matrix.length - 1;
		int d = matrix[0].length - 1;
		while (a < c) {		// 因为是正方形，行或列判断一个越界条件即可
			// 每打印完一圈，T移动到右下角，D移动大左上角，开始下一圈的打印。
			rotateEdge(matrix, a++, b++, c--, d--);
		}
	}

	// 每一圈按四条边上的数划分小组，然后小组内交换
	public static void rotateEdge(int[][] m, int a, int b, int c, int d) {
		int tmp = 0;
		// d-b-1(当前圈每行个数-1)是小组个数
		for (int i = 0; i < d - b; i++) {
			// 四条边上，每个小组内数值进行交换(每个小组的元素有四个，即是四个边上的数)
			tmp = m[a][b + i];
			m[a][b + i] = m[c - i][b];
			m[c - i][b] = m[c][d - i];
			m[c][d - i] = m[a + i][d];
			m[a + i][d] = tmp;
		}
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
		printMatrix(matrix);
		rotate(matrix);
		System.out.println("=========");
		printMatrix(matrix);

	}

}
