package Algorithm;

import graph.Graph;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-22  22:06
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试图是否创建成功
        char[] data =new char[]{'A','B','C','D','E','F','G'};
        int verxs=data.length;
        // 邻接矩阵(边)关系使用二维数组表示
        int[][] weight=new int[][]{   //999表示不连通，其他的表示连通的权
                {999,5,7,999,999,999,2},
                {5,999,999,9,999,999,3},
                {7,999,999,999,8,999,999},
                {999,9,999,9,999,4,999},
                {999,999,8,999,999,5,4},
                {999,999,999,4,5,999,6},
                {2,3,999,999,4,6,999},
        };

        // 创建MGraph对象
        MGraph graph = new MGraph(verxs);
        // 创建一个MinTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph,verxs,data,weight);
        // 显示
        minTree.showGraph(graph);

        //测试prim
        minTree.prim(graph,0);
    }
}

// 先创建村庄的图->再求出最小生成树
class MinTree {
    // 创建图的邻接矩阵
    /*
     *  graph  图对象
     *  verxs  图对应的顶点个数
     *  data   图的各个顶点的值
     *  weight 图的邻接矩阵
     * */
    public void createGraph(MGraph graph, int verxs, char data[], int[][] weight) {
        int i,j;
        for(i=0;i<verxs;i++){
            graph.data[i]=data[i];                 //存入顶点
            for (j=0;j<verxs;j++){
                graph.weight[i][j]=weight[i][j];  //存入边
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph(MGraph graph){
        for (int[] link:graph.weight){
            System.out.println(Arrays.toString(link));
        }
    }


    // 编写prim算法，得到最小生成树
    /*
    *  graph 村庄图
    *   v表示从图的第几个顶点开始生成'A'->0 'B'->1。。。
    * */
    public void prim(MGraph graph, int v) {
        // visited[]标记结点（顶点）是否被访问过
        int[] visited = new int[graph.verxs];
        //visited[]初始默认都为0，表示没有被访问
        /*for (int i=0;i< graph.verxs;i++){
            visited[i]=0;
        }*/

        // 把当前这个顶点标记为已访问
        visited[v]=1;
        // 最后h1和h2记录的是当前权值最小边连接的两个顶点的下标
        int h1=-1;
        int h2=-1;
        int minWeight=999;  // 将minWeight初始成一个大数，后面遍历过程会被替换
        for (int k=1;k<graph.verxs;k++){ //找到graph.verxs-1条边
                        // 普里姆算法结束时，边的个数比顶点少1，故从1开始
            // 确定每一次生成的子图，距离最近
            for (int i=0;i<graph.verxs;i++){    // 遍历已经访问过的顶点
                if(visited[i]==1){
                    for (int j=0;j<graph.verxs;j++){  //遍历没有访问的顶点
                        if( visited[j]==0 && graph.weight[i][j]<minWeight){
                            // 替换minWeight
                            // 寻找已访问过的结点和为访问过的结点间的权值最小的边
                            minWeight=graph.weight[i][j];
                            h1=i;
                            h2=j;
                        }
                    }
                }
            }
            // 找到一条边最小
            System.out.println("边<"+graph.data[h1]+","+graph.data[h2]+"> 权值："+minWeight);
            // 将当前的这个结点标记为已经访问
            visited[h2]=1;
            // 重置minWeight=999；
            minWeight=999;
        }
    }

}

// 图类
class MGraph{
    int verxs; // 表示图的结点个数
    char[] data; //存放结点数据
    int[][] weight;// 存放带权边，就是邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];

    }
}