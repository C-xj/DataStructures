package Class06_二叉树的基本算法;

import java.util.HashMap;
import java.util.LinkedList;

// 求二叉树最大宽度 ---层序遍历
//    方式一：使用Map
//	  方式二：只用queue
public class Code03_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 方式一：使用map实现
	public static int getMaxWidthMap(Node head) {
		if (head == null) {
			return 0;
		}

		LinkedList<Node> queue = new LinkedList<>();
		queue.add(head);
		// key  在哪一层 value  ---当前结点在哪一层
		HashMap<Node, Integer> levelMap = new HashMap<>();
		// head头结点在第一层
		levelMap.put(head, 1);
		int maxWidth = 0;	// 最大宽度
		int curWidth = 0;	// 当前层curLevel层，宽度目前是多少
		int curLevel = 0;	// 当前正在统计的那一层

		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			// 获取当前结点所在层curNodeLevel
			int curNodeLevel = levelMap.get(cur);
			if (cur.left != null) {
				levelMap.put(cur.left, curNodeLevel + 1);
				queue.add(cur.left);
			}
			if (cur.right != null) {
				levelMap.put(cur.right, curNodeLevel + 1);
				queue.add(cur.right);
			}
			// 当前结点所在层如果大于正在统计的上一层，说明上一层已经统计完毕
			if (curNodeLevel > curLevel) {
				curWidth = 0;
				curLevel = levelMap.get(cur);
			} else {
				curWidth++;
			}
			// 更新记录最大宽度，由于最后一层可能没有全部叶子结点，因此，不一定就是最后一层最宽
			maxWidth = Math.max(maxWidth, curWidth);
		}
		return maxWidth;
	}

	// 方式二：不使用map实现
	public static int getMaxWidthNoMap(Node head) {
		if (head == null) {
			return 0;
		}
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(head);
		Node curEnd = head;		// 当前层，最右结点是谁
		Node nextEnd = null;	// 下一层，最右结点是谁
		int max = 0;
		int curLevelNodes = 0;  // 当前层的结点数
		while (!queue.isEmpty()) {
			Node cur = queue.poll();

			if (cur.left != null) {
				queue.add(cur.left);
				nextEnd = cur.left;
			}
			if (cur.right != null) {
				queue.add(cur.right);
				// 如果右结点存在，再次修改下层的最右结点
				nextEnd = cur.right;
			}
			curLevelNodes++;
			// 如果当前结点是当前层的最后一个结点
			if (cur == curEnd){
				// 记录最大宽度
				max = Math.max(max, curLevelNodes);
				// 初始化参数，以便下层统计：因为要开始统计下层，把当前层下层的最右结点赋过去，开始统计下层
				curLevelNodes = 0;
				curEnd = nextEnd;
			}
		}
		return max;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
