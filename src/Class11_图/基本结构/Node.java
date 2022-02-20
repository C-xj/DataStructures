package Class11_图.基本结构;

import java.util.ArrayList;

/**
 * @author Chu
 * @create 2021-12-19  10:26
 */
public class Node {

    public int value;
    public int in;  // 入度
    public int out; // 出度
    public ArrayList<Node> nexts;  // 邻接结点
    public ArrayList<Edge> edges;

    public Node(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
