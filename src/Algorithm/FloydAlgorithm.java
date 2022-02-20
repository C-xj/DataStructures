package Algorithm;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-28  11:00
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        //测试图的创建
        char[] vertex={'A','B','C','D','E','F','G'};
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N =999;
        matrix[0]=new int[]{0,5,7,N,N,N,2};
        matrix[1]=new int[]{5,0,N,9,N,N,3};
        matrix[2]=new int[]{7,N,0,N,8,N,N};
        matrix[3]=new int[]{N,9,N,0,N,4,N};
        matrix[4]=new int[]{N,N,8,N,0,5,4};
        matrix[5]=new int[]{N,N,N,4,5,0,6};
        matrix[6]=new int[]{2,3,N,N,4,6,0};

        //创建FloydGraph对象
        FloydGraph floydGraph = new FloydGraph(vertex.length, matrix, vertex);

        // 调用floyd
        floydGraph.floyd();

        floydGraph.show();
    }
}


class FloydGraph{
    private char[] vertex; //存放顶点的数组
    private int[][] dis;  // 保存从各个顶点出发到其他顶点的距离，最后结果保留在该数组
    private int[][] pre;  // 保存到达目标顶点的前驱顶点

    // length 顶点个数  matrix 邻接矩阵  vertex 顶点数组
    public FloydGraph(int length,int[][] matrix, char[] vertex) {
        this.vertex = vertex;
        this.dis = matrix;
        this.pre = new int[length][length];

        //对pre数组初始化，注意存放的是前去顶点的下标
        for (int i=0;i<length;i++){
            Arrays.fill(pre[i],i);
        }
    }

    // 显示pre数组和dis数组
    public void show() {
        for (int k = 0; k < dis.length; k++) {
            // 输出pre每行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(vertex[pre[k][i]] + "   ");
            }
            System.out.println();
        }
        System.out.println("====================");

        for (int k = 0; k < dis.length; k++) {
            // 输出dis每行
            for (int i = 0; i < dis.length; i++) {
                System.out.printf(" %2d ",dis[k][i] );
            }
            System.out.println();
        }

    }

    // 弗洛伊德算法
    public void floyd(){
        //从中转顶点遍历，k就是中转顶点的坐标
        for(int k=0;k<dis.length;k++){    		// 考虑以Vk作为中转点
            for(int i=0;i<dis.length;i++){		// 遍历整个矩阵，i为行号，j为列号
                for(int j=0;j<dis.length;j++){
                    if(dis[i][j]>dis[i][k]+dis[k][j]){  // 以VK为中转点的路径更短
                        dis[i][j]=dis[i][k]+dis[k][j];	// 更新最短路径长度
                        pre[i][j]=pre[k][j]; 				// 前驱表对应的更新为中转点
                    }
                }
            }
        }
    }

}