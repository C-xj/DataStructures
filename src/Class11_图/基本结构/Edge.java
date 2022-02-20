package Class11_图.基本结构;

/**
 * @author Chu
 * @create 2021-12-19  10:32
 */
public class Edge {

    public int weight;
    public Node from;
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
