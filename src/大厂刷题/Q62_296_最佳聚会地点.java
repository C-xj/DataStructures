package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-17  10:07
 */
/*
* 一个只由0/1组成的二维数组，所有的1去到(1不能斜着走)一个位置聚会(这个位置可以是0/1)，总距离最小是多少？
*
* 思路：
*     ① 先找出所有的1到某一行(m)最优，
*     ② 再找出所有的1到某一列(n)最优，
*     最终位置就是(m,n)
*
*   判断某行/某列最优：
*      ① 双指针到第一行和最后一行，统计每行有多少个1，
*      ② 两行中1少的那个肯定不是最优解，此指针向中间移动
*      ③ 刚刚移动指针之前行的1累加到一起与之前1多的再进行对比，1少的继续移动指针
*      ④ 重复③ 直到两指针指到一行(得到最优解！！！)
*
* */
public class Q62_296_最佳聚会地点 {

    public static int minTotalDistance(int[][] grid) {
        int N = grid.length;
        int M = grid[0].length;
        int[] iOnes = new int[N];           // 统计行上有几个1
        int[] jOnes = new int[M];           // 统计列上有几个1
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (grid[i][j] == 1) {
                    iOnes[i]++;
                    jOnes[j]++;
                }
            }
        }
        int total = 0;      // 记录距离
        int i = 0;          // 第0行
        int j = N - 1;      // 第N-1行
        int iRest = 0;
        int jRest = 0;
        while (i < j) {
            // 哪个指针要移动就把其行上的1加入到距离中(可以理解为移动到下一行)
            if (iOnes[i] + iRest <= iOnes[j] + jRest) {
                total += iOnes[i] + iRest;
                iRest += iOnes[i++];
            } else {
                total += iOnes[j] + jRest;
                jRest += iOnes[j--];
            }
        }
        i = 0;              // 第0列
        j = M - 1;          // 第M-1列
        iRest = 0;
        jRest = 0;
        while (i < j) {
            // 哪个指针要移动就把其列上的1加入到距离中(可以理解为移动到下一列)
            if (jOnes[i] + iRest <= jOnes[j] + jRest) {
                total += jOnes[i] + iRest;
                iRest += jOnes[i++];
            } else {
                total += jOnes[j] + jRest;
                jRest += jOnes[j--];
            }
        }
        return total;
    }

}
