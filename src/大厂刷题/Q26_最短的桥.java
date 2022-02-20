package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-11  11:10
 */
/*
* 在给定的二维二进制数组A中，存在两座岛。（岛是由四面相连的 1 形成的一个最大组。）
    现在，我们可以将0变为1，以使两座岛连接起来，变成一座岛。
    返回必须翻转的0 的最小数目。（可以保证答案至少是 1 。）
* 输入：A = [[0,1],[1,0]]
  输出：1
* 输入：A = [[0,1,0],[0,0,0],[0,0,1]]
  输出：2
*
*   思路：
*       两个岛分别往外逐层广播，然后选择最优位置，相加再 - 3(因为岛内点的位置都是1，有多余计算)
*           在寻找这两座岛时，我们使用深度优先搜索。在向外延伸时，我们使用广度优先搜索。
*
* */
public class Q26_最短的桥 {

    public static int shortestBridge(int[][] m){
        int N = m.length;
        int M = m[0].length;
        int all = N * M;
        int island = 0;                     // 岛的数量
        int[] curs = new int[all];          // 初始队列
        int[] nexts = new int[all];
        int[][] records = new int[2][all];  // 两片1(即两片岛)
        for (int i = 0;i < N;i++){
            for (int j = 0;j < M;j++){
                if (m[i][j] == 1){          // 当前位置发现了1
                    // 把这一片的1都变成2，同时抓上来了，这一片1组成的出是队列curs
                    //      也把这一片的1到自己的距离，都设置成了1，在records中
                    int queueSize = infect(m, i, j, N, M, curs, 0, records[island]);
                    int V = 1;      // 最里的第一层
                    while (queueSize != 0){  // 如果队列不空，V++(层数+1)
                        V++;
                        // 遍历curs里面的点，上下左右，将records[点] = 0的位置存入nexts队列，最后赋给curs，以便下层遍历，返回新的队列长度
                        queueSize = bfs(N,M,all,V,curs,queueSize,nexts,records[island]);
                        int[] tmp = curs;
                        curs = nexts;
                        nexts = tmp;       // 空间复用，此步得到的值，后面进入下一轮bfs，直接被覆盖
                    }
                    island++;       // 岛加1
                }
            }
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0;i < all;i++){
            min = Math.min(min, records[0][i] + records[1][i]);
        }
        return min - 3;
    }


    /* 当前来到m[i][j],总行数时N，总列数是M
    * m[i][j]感染出去(找到这一片岛所有的1)，把每一个1的坐标，放入到int[] curs队列！
    * 1 (a,b) -> curs[index++] = (a * M + b)
    * 1 (c,d) -> curs[index++] = (c * M + d)
    * 二维已经变成一维:  1 (a,b) -> a * M +b   record[a * M +b] = 1
    *
    * 返回1的个数，index
    * */
    public static int infect(int[][] m,int i,int j,int N,int M,int[] curs,int index,int[] record){
        if (i < 0 || i == N || j < 0 || j == M || m[i][j] != 1){
            return index;
        }
        // m[i][j] 不越界，且m[i][j] == 1的情况
        m[i][j] = 2;    // 赋予m[i][j]新的值，不走回头路(即不会一直递归循环)

        int p = i * M + j;
        record[p] = 1;
        // 收集到不同的1，将位置放入队列
        curs[index++] = p;
        // 递归遍历四个方向
        index =infect(m,i - 1,j,N,M,curs,index,record);
        index =infect(m,i + 1,j,N,M,curs,index,record);
        index =infect(m,i,j - 1,N,M,curs,index,record);
        index =infect(m,i,j + 1,N,M,curs,index,record);
        return index;
    }

    /*  二维原始矩阵中，N总行数，M总列数
    *   all总数  all = M * N
    *   V 要生成的是第几层 curs是V-1层，nexts是V层
    *   record里面拿距离
    * */
    private static int bfs(int N, int M, int all, int V, int[] curs, int queueSize, int[] nexts, int[] record) {
        int nexti = 0;
        for (int i = 0;i < queueSize;i++){
            // curs[i] -> 一维坐标判断四个方向
            int up = curs[i] < M ? -1 : curs[i] - M;
            int down = curs[i] + M >= all ? -1 : curs[i] + M;
            int left = curs[i] % M == 0 ? -1 : curs[i] - 1;
            int right = curs[i] % M == M - 1 ? -1 : curs[i] + 1;
            // 如果此点存在且，没来过，则设置层数(即到此点距离)；同时将此点加入队列中
            if (up != -1 && record[up] == 0){
                record[up] = V;
                nexts[nexti++] = up;
            }
            if (down != -1 && record[down] == 0){
                record[down] = V;
                nexts[nexti++] = down;
            }
            if (left != -1 && record[left] == 0){
                record[left] = V;
                nexts[nexti++] = left;
            }
            if (right != -1 && record[right] == 0){
                record[right] = V;
                nexts[nexti++] = right;
            }
        }
        return nexti;      // 返回新一层队列的长度，然后开始下一轮
    }


}
