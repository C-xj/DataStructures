package recursion;

/**
 * @author Chu
 * @create 2021-04-25  15:19
 */
public class Queue8 {

    //定义一个max表示多少个皇后
    int max=8;
    //定义数组array，保存皇后放置位置的结果，比如arr={0，4，7，5，2，6，1，3}
    int[] array=new int[max];

    // 统计8皇后多少种方法
    static int count=0;

    // 统计判断冲突的次数
    static int judgeCount=0;

    public static void main(String[] args) {
        //测试 8皇后是否正确
        Queue8 queue8=new Queue8();
        queue8.check(0);  //从0 即第一个皇后开始放 内部递归增加n的值
        System.out.printf("8皇后问题一共有%d种解法",count);
        System.out.printf("8皇后问题一共判断%d次冲突",judgeCount);

    }

    //编写方法，放置第n个皇后
    // 特别注意：
    // check是每次递归，进入check中都有for（int i=0；i<max；i++）
    // 因此会有回溯 ，第一个解出来后，就退回上层循环，开始回溯所有解
    // 回溯过程：从循环从小递增看出，先输出的解法，每行的列不断增加的这种
    // 循环外层行（行数小）的列先不动，循环内层行（行数大）的列先增
    private void check(int n){
        if (n==max){ // n=8,其实8个皇后已然放好
            show();  // 放好8个皇后把这一种方法输出
            return;
        }
        // 依次放入皇后，并判断是否冲突
        for (int i=0;i<max;i++){
            // 先把当前这个皇后n，放到该行的第1列
            array[n]=i; //i初始为0
            // 判断当前放置的第n个皇后到i列时，是否冲突
            if(judge(n)){ //不冲突
                // 接着放n+1个皇后 ，即开始递归
                check(n+1);  //
            }

            // 如果冲突，就继续执行循环中array[n]=i；  循环存在i++
            // 即把第n个皇后，放置在本行的 后移的一个位置
        }
    }

    //查看当我们放置的第n个皇后，就去检测该皇后是否和前面的皇后冲突
    private boolean judge(int n){
        judgeCount++;  // 每调用一次冲突，统计+1
        for (int i=0;i<n;i++){
            // 说明
            // 1、array[i]==array[n] 判断第n个皇后是否和前面n-1个皇后同一列
            // 2、Math.abs(n-i)==Math.abs(array[n]-array[i])
            //    表示判断第n个皇后是否和第i皇后是否再同一斜线
            // 3、判断是否再同一行，没有必要，n每次都在递增
            if(array[i]==array[n] ||Math.abs(n-i)==Math.abs(array[n]-array[i])){
                // 判断是否同一列 是否同一斜线（斜率为1 等腰）
                return false;
            }
        }
        return true;
    }

    //写一个方法，可以将皇后摆放的位置打印出来
    private void show(){
        count++; // 没show一次，就输出了一种摆放方法，count+1
        for (int i=0;i<array.length;i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }

}
