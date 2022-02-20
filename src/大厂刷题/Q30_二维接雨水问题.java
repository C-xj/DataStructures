package 大厂刷题;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Chu
 * @create 2022-01-12  13:02
 */
/*
*   给你一个 m x n 的矩阵，其中的值均为非负整数，代表二维高度图每个单元的高度，请计算图中形状最多能接多少体积的雨水。
*
*   输入: heightMap = [[3,3,3,3,3],[3,2,2,2,3],[3,2,1,2,3],[3,2,2,2,3],[3,3,3,3,3]]
    输出: 10
*
*   思路：
*       一个变量max = 0。
*       ① 将最外一圈的数压入小根堆；弹出来的数(同时也要记住二维位置)就是当前圈的薄弱点(流出水的地方)；
*       ② 判断此数是否比之前的max大，来替换max，然后将此数周围的没有进过堆的都加入堆，比max小的差值就是该点存的水量，再弹出堆中最小的数。
*       重复②
*
*
* */
public class Q30_二维接雨水问题 {


    public static class Node {
        public int value;   // 当前值的大小
        public int row;     // 行    记录位置
        public int col;     // 列

        public Node(int v, int r, int c) {
            value = v;
            row = r;
            col = c;
        }

    }

    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value - o2.value;
        }

    }

    public static int trapRainWater(int[][] heightMap) {
        if (heightMap == null || heightMap.length == 0 || heightMap[0] == null || heightMap[0].length == 0) {
            return 0;
        }
        int N = heightMap.length;
        int M = heightMap[0].length;
        // isEnter[i][j] == true  之前进过
        //  isEnter[i][j] == false 之前没进过
        boolean[][] isEnter = new boolean[N][M];
        // 创建一个小根堆，将最外一圈的数都加入，并记录它们各自的位置
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
        for (int col = 0; col < M - 1; col++) {
            isEnter[0][col] = true;
            heap.add(new Node(heightMap[0][col], 0, col));
        }
        for (int row = 0; row < N - 1; row++) {
            isEnter[row][M - 1] = true;
            heap.add(new Node(heightMap[row][M - 1], row, M - 1));
        }
        for (int col = M - 1; col > 0; col--) {
            isEnter[N - 1][col] = true;
            heap.add(new Node(heightMap[N - 1][col], N - 1, col));
        }
        for (int row = N - 1; row > 0; row--) {
            isEnter[row][0] = true;
            heap.add(new Node(heightMap[row][0], row, 0));
        }

        int water = 0; // 每个位置的水量，累加到water上去
        int max = 0; // 每个node在弹出的时候，如果value更大，更新max，否则max的值维持不变
        while (!heap.isEmpty()) {
            Node cur = heap.poll();             // 弹出的最外层的薄弱点
            max = Math.max(max, cur.value);     // 判断是否更新max
            int r = cur.row;                    // 记录位置，
            int c = cur.col;
            // 判断上述弹出点位置四周的元素是否进入过堆，
            // 没进过，则计算当前位置与max基准的差值带来的水位(可能为负，那就记0)，然后标记为已进堆true，创建点并加入堆中。
            if (r > 0 && !isEnter[r - 1][c]) {
                water += Math.max(0, max - heightMap[r - 1][c]);
                isEnter[r - 1][c] = true;
                heap.add(new Node(heightMap[r - 1][c], r - 1, c));
            }
            if (r < N - 1 && !isEnter[r + 1][c]) {
                water += Math.max(0, max - heightMap[r + 1][c]);
                isEnter[r + 1][c] = true;
                heap.add(new Node(heightMap[r + 1][c], r + 1, c));
            }
            if (c > 0 && !isEnter[r][c - 1]) {
                water += Math.max(0, max - heightMap[r][c - 1]);
                isEnter[r][c - 1] = true;
                heap.add(new Node(heightMap[r][c - 1], r, c - 1));
            }
            if (c < M - 1 && !isEnter[r][c + 1]) {
                water += Math.max(0, max - heightMap[r][c + 1]);
                isEnter[r][c + 1] = true;
                heap.add(new Node(heightMap[r][c + 1], r, c + 1));
            }
        }
        return water;
    }

}
