package sparseArray;

import java.io.*;

/**
 * @author Chu
 * @create 2021-04-12  14:21
 */
public class sparseArray {
    public static void main(String[] args) throws Exception {
        // 创建一个原始的二维数组 11*11
        // 0：表示没有棋子，1：表示黑子，2：表示蓝子
        int[][] chessArr = new int[11][11];
        chessArr[1][2]=1;
        chessArr[2][3]=2;
        // 输出原书的二维数组
        System.out.println("原始二维数组---");
        for(int [] row:chessArr){
            for(int data:row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
        // 将二维数组转为稀疏数组
        // 1、先遍历二维数组非0个数
        int sum=0;
        for(int i=0;i<11;i++){
            for(int j=0;j<11;j++){
                if(chessArr[i][j]!=0){
                    sum++;
                }
            }
        }
        // 2、创建稀疏数组
        int[][] sparseArr = new int[sum+1][3];
        // 给稀疏数组赋值
        sparseArr[0][0]=11;
        sparseArr[0][1]=11;
        sparseArr[0][2]=sum;

        // 遍历二维数组，将非0的值存放到sparseArr中
        int count=0;
        for(int i=0;i<11;i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr[i][j];
                }
            }
        }
        // 输出稀疏数组的形式
        System.out.println();
        System.out.println("得到的稀疏数组为----");
        for(int i=0;i<sparseArr.length;i++){
            System.out.printf("%d\t%d\t%d\t\n",sparseArr[i][0],sparseArr[i][1],sparseArr[i][2]);
        }
        System.out.println();

        BufferedWriter br = new BufferedWriter(new FileWriter("sparseArr.txt"));
        for(int i=0;i<sparseArr.length;i++){
            for (int j=0;j<3;j++){
                String str = sparseArr[i][j]+"";
                br.write(str);
            }
        }
        br.close();




        // 将稀疏数组恢复成二维数组
        int[][] Arr1 = new int[sparseArr[0][0]][sparseArr[0][1]];

        for(int i=1;i<sparseArr.length;i++){
            Arr1[sparseArr[i][0]][sparseArr[i][1]]=sparseArr[i][2];
        }
        // 输出恢复后的二维数组
        System.out.println("恢复后的二维数组---");
        for(int [] row:Arr1){
            for(int data:row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }
    }


}
