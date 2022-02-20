package Class09_贪心算法;

import java.util.Comparator;
import java.util.PriorityQueue;
/*	输入：正数数组 costs、正数数组 profits、正数K、正数M
	costs表示号项目的花费
	profits表示i号项目在扣除花费之后还能挣到的钱（利润
	K表示你只能串行的最多做k个项目
	M表示你初始的资金
	说明：每做完一个项目，马上获得的收益，可以支持你去做下一个项目。不能并行的做项目。
	最后获得的最大钱数

 * 思路：根据花费建立小根堆，然后将小于资金的项目弹入以利润建立的大根堆，选择大根堆的堆顶作为当前要做的项目
 *
 * */
public class Code05_IPO {

	public static class Node {
		public int p;
		public int c;

		public Node(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	// 根据资金的小根堆比较器
	public static class MinCostComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.c - o2.c;
		}

	}

	// 根据利润的大根堆比较器
	public static class MaxProfitComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o2.p - o1.p;
		}

	}

	public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
		PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		// 所有项目扔到被锁池中, 花费组织的小根堆
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Node(Profits[i], Capital[i]));
		}
		for (int i = 0; i < k; i++) { // 进行K轮
			// 能力所及的项目，全解锁
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			// 无项目可做，即提前结束
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;
		}
		return W;
	}

}
