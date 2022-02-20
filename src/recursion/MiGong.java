package recursion;

/**
 * @author Chu
 * @create 2021-04-25  13:51
 */
public class MiGong {
    public static void main(String[] args) {
        //先创建一个二维数组，模拟迷宫
        // 地图
        int[][] map = new int[8][7];
        //使用1表示墙
        // 左右全部置为1
        for (int i=0;i<8;i++){
            map[i][0]=1;
            map[i][6]=1;
        }
        // 上下全部置为1
        for (int j=0;j<7;j++){
            map[0][j]=1;
            map[7][j]=1;
        }

        // 设置挡板 1表示
        map[3][1]=1;
        map[3][2]=1;


        // 输出地图
        // 地图情况
        for (int i=0;i<8;i++){
            for(int j=0;j<7;j++){

                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }



        //使用递归回溯找路
        setWay(map,1,1); //map引用类型，每次递归修改的都是同一个

        // 输出新地图，小球走过并标识过的  即为2
        System.out.println("新地图如下");
        for (int i=0;i<8;i++){
            for(int j=0;j<7;j++){
                // 设置挡板 1表示
                if ((i==3&&j==1)||(i==3&&j==2)){
                    map[i][j]=1;
                }
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }

    }



    // 使用递归回溯来给小球找路
    /*
    * map表示地图
    * i j 从哪个位置开始找
    * 如果找到通路，就返回true，否则返回false
    * 约定：当map[6][5]位置，则说明通路找到
    *  当map[i][j]为0表示该店没有走过，为1表示墙，为2表示通路可以走
    *   为3表示该店已经走过，但是走不通
    * 走迷宫时，需要确定一个策略，下->右->上->左，如果该点走不通，回溯
    * */
    public static boolean setWay(int[][] map,int i, int j){
        if(map[6][5]==2){ // 通路已经找到
            return true;
        }else {
            if(map[i][j]==0){ // 如果当前这个点还没有走过
                //按策略 下->右->上->左 走
                map[i][j]=2; // 假定该店是可以走通的
                if(setWay(map,i+1,j)){ //向下走
                    return true;
                }else if (setWay(map,i,j+1)){
                    return true;
                }else if (setWay(map,i-1,j)){
                    return true;
                }else if (setWay(map,i,j-1)){
                    return true;
                }else {
                    // 说明该点走不通，是死路
                    map[i][j]=3;
                    return false;
                }
            }else { // 如果map[i][j]！=0 可能是1，2，3
                return false;
            }
        }
    }
}
