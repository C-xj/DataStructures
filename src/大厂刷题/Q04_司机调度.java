package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-04  20:55
 */
/* 题目4：
 *       现有司机N*2人，调度中心会将所有司机平分给A、B两个区域
 *       第i个司机去A可得收入为income[i][0].
 *       第i个司机去B可得收入为income[i][1]
 *       返回所有调度方案中能使所有司机总收入最高得方案，是多少钱？
 * */
public class Q04_司机调度 {

    // 方法一：暴力递归-全排列
    public static int maxMoney1(int[][] matrix){
        int N = matrix.length;          // 司机数量一定是偶数，所以才能平分
        int[] arr = new int[N];         // 记录第几个司机
        for(int i = 0;i < N; i++){
            arr[i] = i;
        }
        return process1(arr,0 ,matrix);
    }

    // arr[index..]范围上，所有的字符，都可以在index位置上，后续都去尝试
    // arr[0..index-1]范围上，是之前做的选择，已经做好决定
    // index终止位置，arr当前的样子，就是一种结果
    public static int process1(int[] arr,int index,int[][] matrix){
        int money = 0;
        if (index == arr.length){   // 一种排列完成后，让前半部分去A，后半部分去B，算出收入返回
            for(int i = 0;i < arr.length;i++){
                money += i < (arr.length / 2) ? matrix[arr[i]][0] : matrix[arr[i]][1];
            }
        }else {
            for (int i = index;i < arr.length;i++){     // 全排列
                swap(arr,index,i);
                // 保存各种排列得到的最大收入
                money = Math.max(money,process1(arr,index + 1,matrix));
                swap(arr,index,i);      // 恢复现场
            }
        }
        return money;
    }

    public static void swap(int[] arr,int i ,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // 暴力递归
    public static int maxMoney(int[][] income){
        if (income == null || income.length < 2 || (income.length & 1) != 0){
            return 0;
        }
        int N = income.length;  // 司机数量一定是偶数，所以才能平分
        int M = N >> 1;         // 要去A区域的人数

        return process(income,0,M);
    }

    // index...所有司机，往A和B区域分配
    // A区域还有rest个名额
    // 返回index...所有司机平分后的最大收入
    public static int process(int[][] income,int index,int rest){
        if (index == income.length){
            return 0;
        }
        // 还剩下司机
        if (income.length - index == rest){  // 如果剩下的司机等于A区域剩的名额(B区域满了)全都给A
            return income[index][0] + process(income,index + 1,rest - 1);
        }
        if (rest == 0){     // A区域满了，都去B
            return income[index][1] + process(income,index + 1,rest);
        }
        // 可以去A，或去B
        int p1 = income[index][0] + process(income,index + 1,rest-1);
        int p2 = income[index][1] + process(income,index + 1,rest);
        return Math.max(p1,p2);
    }



}
