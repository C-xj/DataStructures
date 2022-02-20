package 大厂刷题;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Chu
 * @create 2022-01-24  14:21
 */
/*
* 三个数x,y,z。最小的假设为a，最大的为c，三个数的分别相减绝对值的和为2*(c-a)
*
*   解题思路：
*       转变为找最短区间Q28题，让每个数组至少有一个数在此区间里，绝对值和为2*区间长
*
* */
public class Q34_三个有序数组选三个数彼此相减绝对值相加最小 {


    public static class Node {
        public int value;   // 当前点的值
        public int arrid;   // 当前点所在的行(即第几个列表)
        public int index;   // 当前点在其列表中的索引

        public Node(int v, int ai, int i) {
            value = v;
            arrid = ai;
            index = i;
        }
    }

    // 两点不相等，值的大小排序；两点相等，值所在的行(即第几个列表)排序
    public static class NodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.value != o2.value ? o1.value - o2.value : o1.arrid - o2.arrid;
        }

    }

    public static int[] smallestRange(List<List<Integer>> nums) {
        int N = nums.size();
        TreeSet<Node> orderSet = new TreeSet<>(new NodeComparator());   // 有序表
        for (int i = 0; i < N; i++) {       // 将第一列的点创建出来，加入有序表
            orderSet.add(new Node(nums.get(i).get(0), i, 0));
        }
        boolean set = false;        // 此标志：为了让下面第一次if判断成功
        int a = 0;                  // 记录最短区间的左边
        int b = 0;                  // 记录最短区间的右边
        while (orderSet.size() == N) {
            Node min = orderSet.first();
            Node max = orderSet.last();
            if (!set || (max.value - min.value < b - a)) {  // 如果比之前的短，就进行标识，并替换区间左右值
                set = true;
                a = min.value;
                b = max.value;
            }
            min = orderSet.pollFirst();             // 弹出最小的值
            int arrid = min.arrid;                  // 获取弹出值所在的列表
            int index = min.index + 1;              // 指向此数后的下一个位置
            if (index != nums.get(arrid).size()) {  // 如果没有到当前列表的最后，则将对应的数创建为点加入有序表
                orderSet.add(new Node(nums.get(arrid).get(index), arrid, index));
            }
        }
        return new int[] { a, b };                  // 创建以左右值为边界的一个区间(最短)。
    }

}
