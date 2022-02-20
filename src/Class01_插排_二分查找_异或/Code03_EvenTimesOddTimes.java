package Class01_插排_二分查找_异或;


/**
 * @author Chu
 * @create 2021-11-12  15:48
 */
public class Code03_EvenTimesOddTimes {


    public static void main(String[] args) {
        int[] arr1 = new int[]{1,2,2,3,3,4,1};
        int[] arr2 = new int[]{1,2,2,3,3,4,1,5};
        // 输出数组中只有一个奇数次的数
        printOddTimesNum1(arr1);
        // 输出数组中只有两个奇数次的数
        printOddTimesNum2(arr2);
        // 输出一个数的二进制1的个数
        System.out.println(bit1counts(5));

    }

    // 题目一：arr中，只有一种数，出现奇数次
    public static void printOddTimesNum1(int[] arr){
        int eor = 0;
        for(int i = 0 ;i < arr.length ;i++){
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    //          **************注意***********：
    //            N & ((~N)+1),取出N最右边的1
    /*
        例如：
    *              N = 0 0 1 1 0 1 0 1 0 0 0
    *             ~N = 1 1 0 0 1 0 1 0 1 1 1
    *           ~N+1 = 1 1 0 0 1 0 1 1 0 0 0
    *
    *   N & ((~N)+1) = 0 0 0 0 0 0 0 1 0 0 0
    * */


    // 题目二：一个数组中有两种数出现了奇数次，其他数都出现偶次，
    //          找到并打印出这两个数
    /*
    * eor = a ^ b != 0
    * 假设eor结果的第n为1(a的第n位与b的第n位不等，异或的结果)，
    *   则分成两组：假设，第n位为1的(a在的组)；第n位为0的(b在的组)
    *       两组再分别eor’操作，找出a、b的值。
    * 下面找eor中最右边的1
    * */
    public static void printOddTimesNum2(int[] arr){
        int eor = 0;
        for(int i = 0 ;i < arr.length ;i++){
            eor ^= arr[i];
        }

        // 提取出最右的1(命名为：n位为1)
        int rightOne = eor & (~eor + 1);

        int onlyOne = 0; // eor'
        for(int i =0 ;i < arr.length ;i++){
            if (((arr[i] & rightOne)) != 0 ){ // n位为1的组
                onlyOne ^= arr[i];  // n位为1的组的奇数a
            }
        }
        // eor ^ onlyOne   n位为0的组的奇数b(异或两次a，剩下就是b)
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }


    /*
    * 题目三：统计出一个数二进制1的个数
    *
    *             N = 011011010000
    *  N & ((~N)+1) = 000000010000  → rightOne
    *  N ^ rightOne = 011011000000  消去最右边的1
    * */

    public static int bit1counts(int N){
        int count = 0;

        while (N != 0){
            // 得到一次新的右边的1记录一次
            int rightOne = N & ((~N)+1);
            count++;
            // 消去最右边的1，正数可以减，负数就只能用这种
            N ^= rightOne;
        }
        return count;
    }

}
