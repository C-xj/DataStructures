package 大厂刷题;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Chu
 * @create 2022-01-11  21:20
 */

/*
*   你有k个 非递减排列 的整数列表。找到一个 最小 区间，使得k个列表中的每个列表至少有一个数包含在其中。
    我们定义如果b-a < d-c或者在b-a == d-c时a < c，则区间 [a,b] 比 [c,d] 小。

*   例如：
*       [1,6]
*       [2,5,7,13]
*       [1]
*       [5,60]
*   要找到一个最短区间，使上面每个数组都至少有一个数在区间内
*       答案：[1,5]
*
*   例如：
*       [1,1000]
*       [2,1001]
*      答案：[1,2]，当然[1000,1001]也是最短，但取起始值小的
*
*
*   思路：
*       ① 创建一个有序表(加入修正排序)，将每个列表的第一个数放入有序表，得到一个区间；
*       ② 然后将有序表中最小的数弹出，再从此数的列表中找到下一个数加入有序表，又得到一个新的区间，如果比之前的短就替换，；
*       重复②操作，直到有一个列表的数全部使用(即用到一个列表的最后一个元素)
*
*
* */
public class Q28_最小包含区间问题 {


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
