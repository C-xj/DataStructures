package Class11_图.算法;

import Class11_图.基本结构.Edge;
import Class11_图.基本结构.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

/* Dijkstra-权值不为负的图
*	给一个出发点，求出发点到所有结点的最小距离
*
* 	思路如下：
* 			① 给定一个出发点，然后，得到它直接到图中其他结点的距离表(distanceMap)。
* 			② 然后将这个点锁定，不再访问，之后选择下一个点，通过它桥接到达其他结点
* 			③ 如果出现到达某个点的距离更小，就更新之前的表。
*			④ 重复②、③操作，直到所有点都被锁定。
* */
// no negative weight
public class Code06_Dijkstra {

	public static HashMap<Node, Integer> dijkstra1(Node head) {
		// 从head出发到所有点的最小距离
		// key：从head出发到达key
		// value: 从head出发到达key的最小距离
		// 如果在表中，没有T的记录，含义是从head出发到T这个点的距离为正无穷
		HashMap<Node, Integer> distanceMap = new HashMap<>();
		distanceMap.put(head, 0);
		// 已经求过距离的结点，存在selectedNodes中，实现锁定点的功能，以后再也不碰,防止重复访问操作。
		HashSet<Node> selectedNodes = new HashSet<>();

		// 得到当前点到没有被锁定点的最小距离的点(选取一个桥接点)
		// 第一次调用得到就是head，作为第一个桥接点 --- head 0
		Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		while (minNode != null) {
			// minNode就是桥连点，获取head到桥接点的距离(是前面/上一轮已经确定的最小距离)
			int distance = distanceMap.get(minNode);

			// 遍历与桥连点相连的边
			for (Edge edge : minNode.edges) {
				// 获得与桥连点同边的另一个点toNode
				Node toNode = edge.to;
				// 如果没有head到toNode的距离，就创建，大小 = head点到minNode的距离 + minNode做桥连点往外跳的距离(即权值)
				if (!distanceMap.containsKey(toNode)) {
					distanceMap.put(toNode, distance + edge.weight);
				}
				// 如果有，取最小值更新
				distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
			}

			// 将minNode锁定，以后不再作为桥接点
			selectedNodes.add(minNode);
			// 选取新的桥接点minNode
			minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
		}
		return distanceMap;
	}

	// 选取桥接点
	public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, 
			HashSet<Node> selectedNodes) {
		Node minNode = null;
		// 先用最大值，后面好作比较
		int minDistance = Integer.MAX_VALUE;
		// 遍历表中每个点，以及head到每个点的距离
		for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
			Node node = entry.getKey();
			int distance = entry.getValue();
			// 如果不是被锁定的点，并且距离最小
			if (!selectedNodes.contains(node) && distance < minDistance) {
				// 更新当前桥接点，并更新当前点到此桥接点的最小距离
				minNode = node;
				minDistance = distance;
			}
		}
		// 返回桥接点
		return minNode;
	}


// ----------------------------Dijkstra的改进-------------------------


	// 实现一个可以改动的小根堆：即临时把某个记录的值改变，还能自己调对
	public static class NodeRecord {
		public Node node;
		public int distance;

		public NodeRecord(Node node, int distance) {
			this.node = node;
			this.distance = distance;
		}
	}

	public static class NodeHeap {
		private Node[] nodes;	// 实际的堆结构
		// key 某一个node，value 上面数组中的位置
		private HashMap<Node, Integer> heapIndexMap;
		// key 某一个结点，value 从源结点出发到该结点的目前最小距离
		private HashMap<Node, Integer> distanceMap;
		private int size;	// 堆上有多少个点

		public NodeHeap(int size) {
			nodes = new Node[size];
			heapIndexMap = new HashMap<>();
			distanceMap = new HashMap<>();
			this.size = 0;
		}

		public boolean isEmpty() {
			return size == 0;
		}

		// 有一个点叫node，现在发现了一个从源结点出发到达node的距离为distance
		// 判断要不要更新，如果需要的话，就更新
		public void addOrUpdateOrIgnore(Node node, int distance) {
			if (inHeap(node)) {
				distanceMap.put(node, Math.min(distanceMap.get(node), distance));
				insertHeapify(node, heapIndexMap.get(node));
			}
			if (!isEntered(node)) {
				nodes[size] = node;
				heapIndexMap.put(node, size);
				distanceMap.put(node, distance);
				insertHeapify(node, size++);
			}
		}

		public NodeRecord pop() {
			NodeRecord nodeRecord = new NodeRecord(nodes[0], distanceMap.get(nodes[0]));
			swap(0, size - 1);
			heapIndexMap.put(nodes[size - 1], -1);
			distanceMap.remove(nodes[size - 1]);
			nodes[size - 1] = null;
			heapify(0, --size);
			return nodeRecord;
		}

		private void insertHeapify(Node node, int index) {
			while (distanceMap.get(nodes[index]) < distanceMap.get(nodes[(index - 1) / 2])) {
				swap(index, (index - 1) / 2);
				index = (index - 1) / 2;
			}
		}

		private void heapify(int index, int size) {
			int left = index * 2 + 1;
			while (left < size) {
				int smallest = left + 1 < size && distanceMap.get(nodes[left + 1]) < distanceMap.get(nodes[left])
						? left + 1 : left;
				smallest = distanceMap.get(nodes[smallest]) < distanceMap.get(nodes[index]) ? smallest : index;
				if (smallest == index) {
					break;
				}
				swap(smallest, index);
				index = smallest;
				left = index * 2 + 1;
			}
		}

		private boolean isEntered(Node node) {
			return heapIndexMap.containsKey(node);
		}

		private boolean inHeap(Node node) {
			return isEntered(node) && heapIndexMap.get(node) != -1;
		}

		private void swap(int index1, int index2) {
			heapIndexMap.put(nodes[index1], index2);
			heapIndexMap.put(nodes[index2], index1);
			Node tmp = nodes[index1];
			nodes[index1] = nodes[index2];
			nodes[index2] = tmp;
		}
	}

	// 改进后的dijkstra算法 —— 使用小根堆
	// 从head出发，所有head能到达的结点，生成到达每个结点的最小路径记录并返回
	public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
		// 创建小根堆
		NodeHeap nodeHeap = new NodeHeap(size);
		// 添加、更新、忽略小根堆数据
		nodeHeap.addOrUpdateOrIgnore(head, 0);
		// 存记录
		HashMap<Node, Integer> result = new HashMap<>();
		while (!nodeHeap.isEmpty()) {
			// 弹出当前结点的最小距离记录
			NodeRecord record = nodeHeap.pop();
			// 将此纪录对应的点作为桥接点cur，源结点head到cur的最小路径
			Node cur = record.node;
			int distance = record.distance;
			// 与此桥接点cur相连的边的点，放入小根堆中
			for (Edge edge : cur.edges) {
				nodeHeap.addOrUpdateOrIgnore(edge.to, edge.weight + distance);
			}
			result.put(cur, distance);
		}
		return result;
	}

}
