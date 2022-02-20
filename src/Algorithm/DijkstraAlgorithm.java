package Algorithm;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-27  15:33
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A','B','C','D','E','F','G'};
        //邻接矩阵
        int[][] matrix =new int[vertex.length][vertex.length];
        final int N =666; //表示不可连接
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};
        // 创建Graph对象
        Graph graph = new Graph(vertex,matrix);
        //显示图
        graph.showGraph();

        graph.dsj(6);
        graph.showDijkstra();


    }
}

class Graph{
    private char[] vertex;     //顶点数组
    private int[][] matrix;     //邻接矩阵
    private VisitedVertex visitedVertex; // 已经访问过顶点的集合

    public Graph(char[] vertex,int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }
    // 显示图
    public void showGraph(){
        for(int[] link:matrix){
            System.out.println(Arrays.toString(link));
        }
    }

    //显示最后结果
    public void showDijkstra(){
        visitedVertex.show();
    }

    //迪杰斯特拉算法
    public void dsj(int index){
        visitedVertex = new VisitedVertex(vertex.length, index);
        update(index);  // 更新index顶点到周围顶点的距离和前驱顶点

        for (int j=1;j<vertex.length;j++){
            index=visitedVertex.updateArr(); //选择并返回新的访问顶点
            update(index);          // 更新index顶点到周围顶点的距离和前驱顶点
        }
    }

    // 更新index下标顶点到周围顶点的距离和周围顶点的前驱顶点
    private void update(int index){
        int len=0;
        //根据遍历邻接矩阵对应当前顶点的matrix[index]行
        for (int j=0;j<matrix[index].length;j++){
            // len：出发顶点到index顶点的距离+从index顶点到j顶点的距离的和
            //      (即：出发顶点到j距离)
            len=visitedVertex.getDis(index) + matrix[index][j];
            // 如果j顶点没有被访问过，并且len小于出发顶点到j顶点的距离，就需要更新
            if(!visitedVertex.in(j) && len<visitedVertex.getDis(j)){
                visitedVertex.updatePre(j,index); // 当前访问的index顶点就是j的前驱
                visitedVertex.updateDis(j,len);  // 把顶点到j的距离就更新为len
            }
        }
    }

}

// 已访问顶点集合
class VisitedVertex{
    //记录各个顶点是否访问过，1表示访问过，0表示未访问，会动态更新
    public int[] already_arr;

    //每个下标对应的值为前一个顶点下标（前驱），会动态更新
    public int[] pre_visited;

    //记录出发顶点到其他所有顶点的距离，比如G为出发顶点，就会记录G到其他顶点的距离，
    // 会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    //构造器
    /*
    * length:表示顶点个数
    * index：出发顶点对应的下标，比如G顶点，下标就是6
    * */
    public VisitedVertex(int length,int index){
        this.already_arr=new int[length];
        this.pre_visited=new int[length];
        this.dis=new int[length];

        //初始化dis
        Arrays.fill(dis,666);
        this.already_arr[index]=1;//设置出发顶点被访问
        this.dis[index]=0; //设置出发顶点到自身的访问距离为0
    }

    //判断index顶点是否被访问过
    //如果访问过，就返回true，否则返回false
    public boolean in(int index){
        return already_arr[index] == 1;
    }

    // 更新出发顶点到index顶点的距离
    public void updateDis(int index ,int len){
        dis[index]=len;
    }

    // 更新顶点pre的前驱顶点为index结点
    public void updatePre(int pre,int index){
        pre_visited[pre]=index;
    }

    // 返回出发顶点到index顶点的距离
    public int getDis(int index){
        return dis[index];
    }

    // 继续选择并返回新的访问顶点，
    // 比如这里的G被访问完后，就是A点作为新的访问顶点（注意不是出发顶点）
    public int updateArr(){
        int min =666,index = 0;
        for (int i=0;i<already_arr.length;i++){    // 广度优先遍历+贪心
            if (already_arr[i]==0 && dis[i]<min){
                            // i顶点未被访问，且i与当前顶点距离小于666（即无穷）
                min=dis[i]; // 循环中找到一个最小距离的  （贪心）
                index=i;
            }
        }
        // 更新index顶点被访问,返回此顶点
        already_arr[index]=1;
        return index;
    }


    //显示最后的结果 三个数组的输出
    public void show(){
        System.out.println("======================");
        // 输出already_arr
        for (int i:already_arr){
            System.out.print(i+" ");
        }
        System.out.println();
        // 输出pre_visited
        for (int i:pre_visited){
            System.out.print(i+" ");
        }
        System.out.println();
        // 输出dis
        for (int i:dis){
            System.out.print(i+" ");
        }

        System.out.println();

        //为了看出最短路径距离
        char[] vertex = {'A','B','C','D','E','F','G'};
        int count=0;
        for (int i:dis){
            if (i!=666){
                System.out.print(vertex[count]+"("+i+")");
            }else {
                System.out.println("N ");
            }
            count++;
        }
        System.out.println();
    }
}