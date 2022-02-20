package Class09_贪心算法;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Chu
 * @create 2021-12-16  15:47
 */

/*题目一：排列会议问题
*   @description: 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 * 给你每一个项目开始的时间和结束的时间(给你一个数 组，里面是一个个具体的项目)，
 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。返回这个最多的宣讲场次。
 *
    以结束时间早晚来排，把与当前有重复时间段的排除，然后再次排
 /
 */
public class Code01_BestArrange {

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    // 方式一：贪心算法
    // 重写一个比较器，用来比较会议结束时间点
    public static class ProgramComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }

    }

    public static int bestArrange(Program[] programs, int timePoint) {
        Arrays.sort(programs, new ProgramComparator()); // 根据结束时间排序
        int result = 0;
        // 从左往右依次遍历所有的会议
        for (int i = 0; i < programs.length; i++) {
            if (timePoint <= programs[i].start) {   // timePoint当前时间比要安排的会议开始时间早，即可安排会议
                result++;
                timePoint = programs[i].end;    // 把当前时间，设置成刚刚安排会议的结束时间
            }
        }
        return result;  // 得到安排多少场会议
    }


    // 方式二：暴力递归
    public static int bestArrange1(Program[] programs) {
        if (programs == null || programs.length == 0) {
            return 0;
        }

        return process(programs, 0, 0);
    }

    /**
     * 会议暴力递归处理
     *
     * @param programs 会议项目
     * @return
     */
    private static int process(Program[] programs, int timeLine, int done) {
        if (programs.length == 0) {
            return 0;
        }

        int max = done;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, programs[i].end, done + 1));
            }
        }

        return max;
    }

    /**
     * 拷贝除了 i 节点元素的数组
     *
     * @param programs 数组
     * @param i        节点索引
     * @return 新数组
     */
    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }


    // 测试是否一致
    // for test
    public static Program[] generatePrograms(int programSize, int timeMax) {
        Program[] ans = new Program[(int) (Math.random() * (programSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Program(r1, r1 + 1);
            } else {
                ans[i] = new Program(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Program[] programs = new Program[4];
      /*
        programs[0] = new Program(0, 7);
        programs[1] = new Program(5, 6);
        programs[2] = new Program(12, 14);
        programs[3] = new Program(0, 3);

        */

        programs[0] = new Program(0, 3);
        programs[1] = new Program(0, 5);
        programs[2] = new Program(4, 6);
        programs[3] = new Program(12, 14);
        System.out.println(bestArrange1(programs));


    }


}
