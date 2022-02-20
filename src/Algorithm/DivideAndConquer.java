package Algorithm;

/**
 * @author Chu
 * @create 2021-05-20  16:31
 */
public class DivideAndConquer {
    public static void main(String[] args) {
        hanoiTower(3,'A','B','C');
    }

    // 汉诺塔移动方法
    // 分治算法
    public static void hanoiTower(int num,char a,char b,char c){
        if (num==1){
            System.out.println("第1个盘从"+a+"->"+c);
        }else {
            // 如果有n>=2情况，看作两个盘，
            // 一个是最下面的盘1，另一个是上面剩余的盘2
            //1、先把上面剩余的所有盘 A->B,移动过程会用到c
            hanoiTower(num-1,a,c,b);
            //2、把最下边的盘 A->C
            System.out.println("第"+num+"个盘从"+ a +"->"+c);
            //3、把B塔所有的盘从B->C,移动过程使用到a
            hanoiTower(num-1,b,a,c);
        }
    }
}
