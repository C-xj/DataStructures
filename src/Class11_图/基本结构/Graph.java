package Class11_图.基本结构;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Chu
 * @create 2021-12-19  10:34
 */
public class Graph {

    public HashMap<Integer,Node> nodes;
    public HashSet<Edge> edges;


    public Graph() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }
}
