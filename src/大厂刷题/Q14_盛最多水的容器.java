package 大厂刷题;

/**
 * @author Chu
 * @create 2022-01-07  13:50
 */
/*
*   给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点(i,ai) 。
*       在坐标内画 n 条垂直线，垂直线 i的两个端点分别为(i,ai) 和 (i, 0) 。
*       找出其中的两条线，使得它们与x共同构成的容器可以容纳最多的水。
说明：你不能倾斜容器。
*
*   思路： 双指针，两端两个指针，计算水量和最大值比，
*       然后两个指针对应的数值谁小对应的指针就向中间移动。
* */

public class Q14_盛最多水的容器 {

    // 双指针
    public static int maxArea1(int[] h) {
        int max = 0;
        int l = 0;
        int r = h.length - 1;
        while (l < r) {
            max = Math.max(max, Math.min(h[l], h[r]) * (r - l));
            if (h[l] > h[r]) {
                r--;
            } else {
                l++;
            }
        }
        return max;
    }

    // 暴力
    public static int maxArea2(int[] h) {
        int max = 0;
        int N = h.length;
        for (int i = 0 ;i < N;i++){
            for(int j = i + 1;j < N ;j++){
                max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
            }
        }
        return max;
    }



}
