package Class12_暴力递归;

import java.util.ArrayList;
import java.util.List;
/* 暴力递归：
 * 		暴力递归就是尝试
 * 		1) 把问题转化为规模缩小了的同类问题的子问题
 * 		2) 有明确的不需要继续进行递归的条件(base case)
 * 		3) 有当得到了子问题的结果之后的决策过程
 * 		4) 不记录每一个子问题的解(记录每一个子问题的解就是动态规划)
 *
 * 		思路分析:
 * 			① 1~N-1  from → other
 * 			② N 	  from → to
 * 			③ 1~N-1  other → to
 * */

public class Code01_Hanoi {

    // ---------------汉诺塔实现方式：启发变量替换递归---------------------------
    public static void printAllWays(int n) {
        leftToRight(n);
    }

    // 请把1~N层圆盘   从左 -> 右
    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to right");
            return;
        }
        // 1~N-1层圆盘   从左 -> 中
        leftToMid(n - 1);
        System.out.println("Move " + n + " from left to right");
        // 1~N-1层圆盘   从中 -> 右
        midToRight(n - 1);
    }

    // 请把1~N层圆盘   从左 -> 中
    public static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("Move " + n + " from left to mid");
        rightToMid(n - 1);
    }

    public static void midToRight(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("Move " + n + " from mid to right");
        leftToRight(n - 1);
    }

    public static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("Move " + n + " from mid to left");
        rightToLeft(n - 1);
    }

    public static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("Move " + n + " from right to mid");
        leftToMid(n - 1);
    }


    public static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("Move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("Move " + n + " from right to left");
        midToLeft(n - 1);
    }

// ----------------------------------------------------------------
//  使用变量替代，递归实现汉诺塔
    public static void hanoi(int n) {
        if (n > 0) {
            func(n, "左", "中", "右");
        }
    }

    // 1~i 圆盘 目标是from -> to， other是另外一个
    public static void func(int N, String from, String to, String other) {
        if (N == 1) {
            System.out.println("Move 1 from "  + from + " to " + to);
        }else {
            func(N - 1, from, other, to);
            System.out.println("Move " + N + " from " + from + " to " + to);
            func(N - 1, other, to, from);
        }
    }

// ------------------------------------------------------------------


    public static void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        if (A == null || A.size() == 0) {
            return;
        }
        int N = A.size();
        process(N, A, B, C);
    }

    private static void process(int n, List<Integer> from,
                                List<Integer> other,
                                List<Integer> to) {
        if (n <= 0) {
            return;
        }
        process(n - 1, from, to, other);
        to.add(from.remove(from.size() - 1));
        process(n - 1, other, from, to);
    }



    public static void main(String[] args) {
        hanoi(3);
        ArrayList<Integer> a = new ArrayList<>();
        a.add(2);
        a.add(1);
        a.add(0);
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> c = new ArrayList<>();
        hanota(a, b, c);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(c.get(0));
    }

}
