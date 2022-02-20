package 大厂刷题;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2022-01-05  12:30
 */
/*
*   给定一个数组arr，代表每个人的能力值。在给定一个非负数k如果两个人能力差值正好为k，
*   那么可以凑在一起比赛，一局比赛只有两个人，返回最多可以同时有多少场比赛
*
*   排序后，滑动窗口
*       起始位置L、R相同，先固定L，然后移动R，找到可以比赛的，将R此时位置标定为(已用)，
*           然后同时后移一位，L如果来到的位置已用，就再移，直到来到没有使用过的位置，
*           如果此时L >= R，则移动R
*
* */
public class Q06_能同时比赛的最大场次 {

    // 方法一：暴力解,全排列
    public static int maxPairNum1(int[] arr,int k){
        if (k < 0){
            return -1;
        }
        return process1(arr,0,k);
    }

    // arr[index..]范围上，所有的字符，都可以在index位置上，后续都去尝试
    // arr[0..index-1]范围上，是之前做的选择，已经做好决定
    // index终止位置，arr当前的样子，就是一种结果
    private static int process1(int[] arr, int index, int k) {
        int ans = 0;
        if (index == arr.length){  // 一种排列结束后，遍历相邻两个是否可以组成一场比赛
            for (int i = 1;i < arr.length;i += 2){
                if (arr[i] - arr[i - 1] == k){
                    ans++;
                }
            }
        }else {
            for (int r = index; r < arr.length;r++){    // 数组全排列 (后面遍历的r来到index的排列，然后来到index+1...)
                swap(arr,index,r);
                // 保存各种排列中，可以比赛最多场的数量
                ans = Math.max(ans,process1(arr,index + 1,k));
                swap(arr,index,r);
            }
        }
        return ans;
    }

    public static void swap(int[] arr,int i ,int j){
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }


    // 方法二：排序后，滑动窗口
    public static int maxPairNum2(int[] arr,int k){
        if (k < 0 || arr == null || arr.length < 2){
            return 0;
        }
        // 排序
        Arrays.sort(arr);
        int ans = 0;
        int N = arr.length;
        int L = 0;
        int R = 0;
        // 记录有无被用过,初始化false
        boolean[] usedR = new boolean[N];
        while (L < N && R < N){
            if (usedR[L]){      // L来到的位置被用过，后移一位
                L++;
            }else if (L >= R){  // 维护窗口(只有窗口没人，或者只有一个人情况)
                R++;
            }else {             // 两个没有使用的点，且R > L 计算是否可以比赛
                int distance = arr[R] - arr[L];
                // 如果当前两个位置可以比赛，增加一次，并记录R为已用并移动一位，L不记录是因为不再回退(即无影响)，L移动一位
                if (distance == k){
                    ans++;
                    usedR[R++] = true;
                    L++;
                }
            }
        }
        return ans;
    }



}
