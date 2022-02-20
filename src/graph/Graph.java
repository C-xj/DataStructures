package graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @author Chu
 * @create 2021-05-19  10:30
 */
public class Graph {

    private ArrayList<String> vertexList;  // 存储顶点集合
    private int[][] edges;  //存储图对应的邻接矩阵
    private int numOfEdges; // 记录边的个数


    //定义数组boolean[]，记录某个结点是否被访问
    private boolean[] isVisited;

    // 定义一个队列，记录顶点出入--因为LinkedList有两个方法故用它来替代队列
    private LinkedList queue;


    public static void main(String[] args) {


        //测试图是否创建
        int n=8; // 结点的个数
        String Vertexs[]={"A","B","C","D","E","F","G","H"};
        // 创建图对象
        Graph graph=new Graph(n);
        // 循环的添加顶点
        for (String Vertex:Vertexs){
            graph.insertVertex(Vertex);
        }

        //添加边
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0,1,1);//A-B
        graph.insertEdge(0,2,1);//A-C
        graph.insertEdge(1,2,1);//B-C
        graph.insertEdge(2,3,1);//B-D
        graph.insertEdge(1,4,1);//B-E

        // 上下是个非连通子图

        graph.insertEdge(5,6,1);//F-G
        graph.insertEdge(5,7,1);//F-H
        graph.insertEdge(6,7,1);//G-H

        /* 添加其他边，测试深度和广度是否正确
        graph.insertEdge(0,1,1);
        graph.insertEdge(0,4,1);
        graph.insertEdge(1,5,1);
        graph.insertEdge(5,2,1);
        graph.insertEdge(5,6,1);
        graph.insertEdge(2,6,1);
        graph.insertEdge(2,3,1);
        graph.insertEdge(6,7,1);
        graph.insertEdge(3,7,1);
        graph.insertEdge(6,3,1);
        */

        //显示邻接矩阵
        graph.showGraph();

        // 测试dfs遍历
        System.out.println("测试深度遍历");
        graph.dfsTraverse(graph.isVisited);

        // 测试bfs遍历
        System.out.println("测试广度遍历");
        graph.bfsTraverse(graph.isVisited);

    }

    //构造器
    public Graph(int n) {  // n表示传入的顶点个数
        // 初始化矩阵和vertexList
        edges=new int[n][n];
        vertexList=new ArrayList<String>(n);
        numOfEdges=0;

        isVisited=new boolean[n];

        queue=new LinkedList();
    }

    //插入结点（顶点）
    public void  insertVertex(String vertex){
        vertexList.add(vertex);
    }

    //添加边
    /*
    * v1表示顶点的下标
    * v2表示顶点的下标
    * weight表示边的权值 0/1  矩阵内元素的数值
    * */
    public void insertEdge(int v1,int v2,int weight){
        edges[v1][v2]=weight;
        edges[v2][v1]=weight;
        numOfEdges++;
    }

    // 图中常用的方法
    // 返回顶点的个数
    public int getNumOfvertex(){
       return vertexList.size();
    }

    // 得到边的数目
    public int getNumOfEdges(){
       return numOfEdges;
    }

    // 返回结点i（下标）对应的数据
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    //返回v1和v2对应边的权值
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    // 显示图对应的矩阵
    public void  showGraph(){
        for (int[] link:edges){
            System.out.println(Arrays.toString(link));
        }
    }


    //图的遍历的前期准备
    /*
    * 传入当前顶点v
    * 如果有与v相连的，则返回第一个邻接顶点w，否则返回-1
    * */
    public  int getFirstNeighbor(int v){
        for (int w=0;w<vertexList.size();w++){
            if (edges[v][w]>0){  // 两顶点边值>0；说明两顶点有连接
                return w;    // 返回与v相连的第一个邻接顶点w
            }
        }
        return -1;
    }

    //根据前一个邻接结点的下标来获取下一个邻接结点
    /*
    * 传入当前顶点v，以及与v相连的第一个邻接顶点w
    * 如果有与v相连且在w之后的另一个邻接顶点j则返回，否则返回-1
    * */
    public int getNextNeighbor(int v,int w){
        for (int j=w+1;j<vertexList.size();j++){  // w与v相连，从w+1开始遍历
            if (edges[v][j]>0){  // 两顶点边值>0；说明两顶点有连接
                return j;     // 返回与v相连的w之后的下一个顶点j
            }
        }
        return -1;
    }


    // 解决深度遍历非连通图不能遍历的问题
    public void dfsTraverse(boolean [] isVisited){
        for (int v=0;v<vertexList.size();++v){
            isVisited[v]=false;  // 初始化访问标记
        }
        for (int v=0;v<vertexList.size();++v){
            if (!isVisited[v]){  //如果没被访问
                dfs(isVisited,v);
            }
        }
    }


    // 深度优先遍历算法
    // v第一次就是0，集合中i=0下对应的顶点“A”
    public void dfs(boolean[] isVisited,int v){
        //首先访问该顶点，输出
        System.out.print(getValueByIndex(v)+"->");
        //将该顶点设置为已经访问
        isVisited[v]=true;

        // 遍历
        for (int w=getFirstNeighbor(v);w>=0;w=getNextNeighbor(v,w)){

                if (!isVisited[w]){  // 如果w对应的顶点没有被访问，
                    // 递归访问与w相连的第一个邻接顶点
                    dfs(isVisited,w);
                }
                //否则，获取v的下一个邻接顶点,继续判断访问
        }
    }


    // 解决广度遍历非连通图不能遍历的问题
    public void bfsTraverse(boolean [] isVisited){
        for (int v=0;v<vertexList.size();++v){
            isVisited[v]=false;  // 初始化访问标记
        }
        for (int v=0;v<vertexList.size();++v){
            if (!isVisited[v]){  //如果没被访问
                bfs(isVisited,v,queue);
            }
        }
    }


    // 广度优先遍历算法
    public void bfs(boolean[] isVisited,int v,LinkedList queue){
        //首先访问该顶点，输出
        System.out.print(getValueByIndex(v)+"->");
        //将该顶点设置为已经访问
        isVisited[v]=true;

        //从尾部加入
        queue.addLast(v);
        while (!queue.isEmpty()){  //队列非空
            // 去出队列的头结点下标并删除
            queue.removeFirst();

            for (int w=getFirstNeighbor(v);w>=0;w=getNextNeighbor(v,w)) {

                if (!isVisited[w]) {  // 如果w对应的顶点没有被访问，则访问输出
                    System.out.print(getValueByIndex(w) + "->");
                    isVisited[w] = true; //设为被访问过的状态
                    queue.addLast(w);  //加入到队列中
                }
                //否则，获取v的下一个邻接顶点,继续判断访问
            }
        }
    }

}
