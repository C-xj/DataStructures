package Class11_图.算法;

import Class11_图.基本结构.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Chu
 * @create 2021-12-19  12:05
 */
public class Code01_BFS {

    // 从node出发，进行宽度优先遍历
    public static void bfs(Node node){
        if (node == null){
            return;
        }
        // 队列
        Queue<Node> queue = new LinkedList<>();
        // set保证不重复
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.println(cur.value);
            // 当前结点的直接邻居没有进过set的才进set、进queue
            for (Node next : cur.nexts) {
                if (!set.contains(next)) {
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }

}
