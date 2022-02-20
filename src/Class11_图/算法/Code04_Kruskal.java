package Class11_图.算法;

import Class11_图.基本结构.Edge;
import Class11_图.基本结构.Graph;
import Class11_图.基本结构.Node;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/* 最小生成树算法之Kruskal  ---并查集应用
	1) 总是从权值最小的边开始考虑，依次考察权值依次变大的边
	2) 当前的边要么进入最小生成树的集合，要么丢弃
	3) 如果当前的边进入最小生成树的集合中不会形成环，就要当前边
	4) 如果当前的边进入最小生成树的集合中会形成环，就不要当前边
	5) 考察完所有边之后，最小生成树的集合也就得到了
	// 适合有向图；当跑无向图时，边会少一半
* */

//undirected graph only
public class Code04_Kruskal {

	// Union-Find Set 并查集
	public static class UnionFind {
		// key表示某一个结点，value表示key结点往上的结点
		private HashMap<Node, Node> fatherMap;
		// key表示某一个集合的代表结点，value表示key所在集合的结点个数
		private HashMap<Node, Integer> rankMap;

		public UnionFind() {
			fatherMap = new HashMap<Node, Node>();
			rankMap = new HashMap<Node, Integer>();
		}

		private Node findFather(Node n) {
			Node father = fatherMap.get(n);
			if (father != n) {
				father = findFather(father);
			}
			fatherMap.put(n, father);
			return father;
		}

		public void makeSets(Collection<Node> nodes) {
			fatherMap.clear();
			rankMap.clear();
			for (Node node : nodes) {
				fatherMap.put(node, node);
				rankMap.put(node, 1);
			}
		}

		public boolean isSameSet(Node a, Node b) {
			return findFather(a) == findFather(b);
		}

		public void union(Node a, Node b) {
			if (a == null || b == null) {
				return;
			}
			Node aFather = findFather(a);
			Node bFather = findFather(b);
			if (aFather != bFather) {
				int aFrank = rankMap.get(aFather);
				int bFrank = rankMap.get(bFather);
				if (aFrank <= bFrank) {
					fatherMap.put(aFather, bFather);
					rankMap.put(bFather, aFrank + bFrank);
				} else {
					fatherMap.put(bFather, aFather);
					rankMap.put(aFather, aFrank + bFrank);
				}
			}
		}
	}

	// 根据边的权值排序
	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	public static Set<Edge> kruskalMST(Graph graph) {
		// 实现一个并查集
		UnionFind unionFind = new UnionFind();
		// 将所有点各自放到一个集合中
		unionFind.makeSets(graph.nodes.values());
		// 创建根据权值边排序的小根堆
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());

		for (Edge edge : graph.edges) {	// M条边
			priorityQueue.add(edge);
		}
		// 保证边不重复
		Set<Edge> result = new HashSet<>();
		while (!priorityQueue.isEmpty()) {
			// 依次弹出边
			Edge edge = priorityQueue.poll();
			// 如果边的两侧不是一个集合
			if (!unionFind.isSameSet(edge.from, edge.to)) {
				// 要这条边，并把两侧结点放入一个集合
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}
}
