package Class11_图.算法;

import Class11_图.基本结构.Node;

import java.util.HashSet;
import java.util.Stack;

public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		// 栈，记录深度优先遍历的路径
		Stack<Node> stack = new Stack<>();
		// set保证不重复
		HashSet<Node> set = new HashSet<>();
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			// 例如 a→b/c/d （b→e）
			// 遍历弹出结点a的邻接结点b/c/d
			for (Node next : cur.nexts) {
				// 邻接结点b没进入过set(即第一次出现)
				if (!set.contains(next)) {
					// 再次放入刚刚的结点a(用于后续遍历其另一条支路c/d)
					stack.push(cur);
					// 放入其第一个邻接结点b，并加入set
					stack.push(next);
					set.add(next);
					// 打印其邻接结点b
					System.out.println(next.value);
					// 结束循环，开始考虑此邻接结点b它的邻接结点e（即先完成深度）
					break;
				}
			}
		}
	}

}
