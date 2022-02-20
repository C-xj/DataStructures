package 大厂刷题;


import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-02-14  12:26
 */
/*
*   一个arr数组，arr[i] = j,表示第i号试题的难度为j。给定一个非负数M。
*       想出一张卷子，对于任何相邻的两道题目，前一题的难度不能超过后一题的难度+M
*       返回所有可能的卷子种树。
*
    *   要出一套卷子，要求此卷子，任意两个相邻的题目
    *       前一道题的难度 <= 后一道题难度 + M
*
*   IndexTree:
*          add(index,+v): 指定下标位置的值 + v
*          sum(index):1 ~ index 值的和
*
*   从左到右的模型：
*       数组从小到大排序，0~i有多少合法的卷子
*
*       假如0~i-1有a套合法的卷子： i位置有多少套合法卷子
*               情况① i位置的数排在最后：
*                                   a套
*               情况② i位置的数插在某个数值前面：
*                       前面每一套合法的试卷中有多少个 >= arr[i] - M
*                           的数值，就表示这一套试卷可以拓展出多少试卷(假设拓展出b)。
*                                   a * b套(因为a中每一套的排列不同，但 >= arr[i] - M 的数量相同)
*       综上所述一共：a * (b + 1)套
* */
public class Q46_出卷子的方法数 {

    // 纯暴力方法，生成所有排列，一个一个验证
    public static int ways1(int[] arr, int m){
        if(arr == null || arr.length == 0){
            return 0;
        }
        return process(arr,0,m);
    }

    public static int process(int[] arr,int index, int m){
        if (index == arr.length){       // 索引到最后，验证排列是否合法
            for (int i = 1;i < index;i++){
                if (arr[i - 1] > arr[i] + m){
                    return 0;
                }
            }
            return 1;
        }
        int ans = 0;
        for (int i = index;i < arr.length;i++){
            swap(arr,index,i);      // 得到当前index位置交换后的各种排列
            ans += process(arr,index + 1,m);    //  开始下一个位置的交换排列
            swap(arr,index,i);      // 恢复现场
        }
        return ans;
    }

    public static void swap(int[] arr,int i,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }



    // 方式二：O(N * logV)
    // 从左往右的动态规划 + IndexTree(找到>= arr[i] - M的数) / 因为排序了也可以用二分查找
    public static int ways2(int[] arr,int m){
        if(arr == null || arr.length == 0){
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for (int num : arr){
            max = Math.max(max,num);
            min = Math.min(min,num);
        }
        IndexTree itree = new IndexTree(max - min + 2);
        Arrays.sort(arr);
        int a = 0;
        int b = 0;
        int all = 1;    // 0~i-1 有多少合法
        itree.add(arr[0] - min + 1,1);
        for (int i = 1; i < arr.length;i++){
            a = arr[i] - min + 1;
            b = i - (a - m - 1 >= 1 ? itree.sum(a - m - 1) : 0);   // 拓展出多少套
            all = all * (b + 1);
            itree.add(a,1);
        }
        return all;
    }

    // 注意开始下标是1，不是0
    public static class IndexTree{

        private int[] tree;
        private int N;

        public IndexTree(int size){
            N = size;
            tree = new int[N + 1];
        }

        public int sum(int index){
            int ret = 0;
            while (index > 0){
                ret += tree[index];
                index -= index & -index;
            }
            return ret;
        }

        public void add(int index,int d){
            while (index <= N){
                tree[index] += d;
                index += index & -index;
            }
        }
    }

}
