package Algorithm;

/**
 * @author Chu
 * @create 2021-05-21  16:02
 */
public class dynamic {
    public static void main(String[] args) {
        // 背包问题
        int[] w ={1,4,3}; // 物品的重量
        int[] val ={1500,3000,2000}; // 物品的价值 val[i]就是v[i]
        int m=4; // 背包的容量
        int n=val.length; //物品的个数

        // 为了记录放入商品的情况，定义一个二维数组
        int[][] path=new int[n+1][m+1];

        //创建二维数组
        // v[i][j]表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v= new int[n+1][m+1];

        // 初始化第一行、第一列，不去设置也可以默认为0
        for (int i=0;i<v.length;i++){
            v[i][0]=0;  // 将第一列设置为0
        }
        for (int i=0;i<v[0].length;i++){
            v[0][i]=0;  // 将第一行设置为0
        }

        // 根据前面公式来动态规划处理
        for (int i=1;i<v.length;i++){   //不处理第一行，i从1开始
            for (int j=1;j<v[i].length;j++){  //不处理第一列，j从1开始
                //公式
                if (w[i-1]>j){  // 因为程序i是从1开始的，因此原公式w[i]改为
                                // w[i-1]
                    v[i][j]=v[i-1][j];
                }else {  // i从1开始，按顺序商品价格和重量在数组中val[i-1]、w[i-1]
                    //v[i][j]=Math.max(v[i-1][j],val[i-1]+v[i-1][j-w[i-1]]);
                    if (v[i-1][j]< val[i-1]+v[i-1][j-w[i-1]]){  // 为了记录商品存放到背包的情况，不能简单使用上面的
                        v[i][j]=val[i-1]+v[i-1][j-w[i-1]];
                        // 把当前的情况记录到path数组中
                        path[i][j]=1;   // 此记录的是最优解，i表示第几个商品
                    }else {
                        v[i][j]=v[i-1][j];
                    }

                }
            }
        }

        // 输出一下动态规划后的二维数组
        for(int i=0;i<v.length;i++){
            for (int j=0;j<v[i].length;j++){
                System.out.print(v[i][j]+" ");
            }
            System.out.println();
        }

       // 输出最后放入的哪些商品
        int i= path.length-1; // 行最大下标
        int j= path[0].length-1; // 列最大下标
        while (i>0 && j>0){
            if(path[i][j]==1){ //说明第i个商品放入了容量为j的背包中
                System.out.printf("第%d个商品放入到背包\n",i);
                j-=w[i-1]; // 减去当前i商品的重量w[i-1]
            }
            i--;
        }


    }



}
