package Class11_图.算法;

import Class11_图.基本结构.Graph;
import Class11_图.基本结构.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
/* 图的拓扑排序算法·
	* 1）在图中找到所有入度为0的点输出
	* 2）把所有入度为0的点在图中删除，继续找入读为0的点输出，周而复始
	* 3）图的所有点都被删除后，依次输出的顺序就是拓扑排序
	* // 入度为0表示其不依赖前面的(前面的都已完成)
	* 要求：有向无环图
	* 应用，时间安排、编译顺序
* */

public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		// key：某一个node
		// value:剩余的入度
		HashMap<Node, Integer> inMap = new HashMap<>();
		// 入度为0的点，才能进这个队列
		Queue<Node> zeroInQueue = new LinkedList<>();

		// 遍历图结构中点(map)的values(即node每个结点)
		for (Node node : graph.nodes.values()) {
			// 存入
			inMap.put(node, node.in);
			// 如果入度为0，就也存入队列
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		// 拓扑排序的结果，依次加入result
		List<Node> result = new ArrayList<>();

		while (!zeroInQueue.isEmpty()) {
			// 弹出入度为0的点，并加入结果集
			Node cur = zeroInQueue.poll();
			result.add(cur);
			// 遍历其所有邻接结点，将它们的入度减1，如果为0加入队列。
			for (Node next : cur.nexts) {
				inMap.put(next, inMap.get(next) - 1);
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
