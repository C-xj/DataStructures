package Algorithm;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author Chu
 * @create 2021-05-29  12:42
 */
public class HorseChessBoardAlgorithm {

    private static int X; //表示棋盘的列数（X的下标负责每行的左右）
    private static int Y; //表示棋盘的行数（Y的下标负责每列的左右）
    //创建一个数组，标记棋盘的各个位置是否被访问过，一维表示二维
    private static boolean visited[];
    // 使用一个属性，标记是否棋盘的所有位置都被访问
    private static boolean finished; // 初始为flase，如果为true，表示成功


    public static void main(String[] args) {
        //测试
        System.out.println("骑士周游：");
        X = 8;
        Y = 8;
        int row = 1; //骑士初始行，从1开始编号
        int col = 1; //骑士初始列，从1开始编号
        //创建棋盘
        int[][] chessboard = new int[X][Y];
        //初始值都为false
        visited = new boolean[X * Y];
        long start = System.currentTimeMillis();
        traversalChessboard(chessboard, row -1, col - 1, 0);
        long end = System.currentTimeMillis();
        System.out.println("共耗时 " + (end - start) + " 毫秒");

        //输出棋盘情况
        for (int[] rows : chessboard) {
            for (int step : rows) {
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }


    /**
     * 骑士周游算法
     * @param chessboard 棋盘(棋盘的每个格子记录的是骑士第几部访问的该位置)
     * @param row 骑士当前位置的行
     * @param col 骑士当前位置的列
     * @param step 当前骑士走的是第几步，初始时为1
     */
    public static void traversalChessboard(int[][] chessboard, int row, int col, int step){
        //记录骑士第step步访问该位置
        chessboard[row][col] = step;
        //将该位置记录为已访问  一维表示二维
        visited[row * X + col] = true;
        //获取当前位置可以走的下一个位置的集合
        ArrayList<Point> next = next(new Point(col, row));
        //对next进行排序  排序的规则就是对next的所有的Point对象的下一步的位置的数目，进行非递减排序
        sort(next);  // 贪心优化

        while (!next.isEmpty()){
            //取出骑士下一个走的位置
            Point p = next.remove(0);
            //判断下一个位置是否已经访问过，没有访问则访问
            if(!visited[p.y * X + p.x]){
                traversalChessboard(chessboard, p.y, p.x, step + 1);
            }
        }

        //判断骑士是否完成周游，如果骑士走的步数小于 X * Y 则表示没有完成任务，此时，将当前位置，置0
        //step < X * Y有两种情况：1.棋盘还没有走完  2.棋盘此时正在回溯
        if(step < X * Y - 1  && !finished){
            chessboard[row][col] = 0;
            visited[row * X + col] = false;
        }else {
            finished  = true;
        }

    }

    //根据当前这一步的所有的下一步的选择位置，进行非递减排序，减少回溯的次数
    private static void sort(ArrayList<Point> next) {
        next.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                //获取o1下一步的所有位置个数
                int count1 = next(o1).size();
                //获取o2下一步的所有位置个数
                int count2 = next(o2).size();
                if(count1 < count2){
                    return -1;
                }else if(count1 == count2){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
    }


    /**
     * 根据当前的位置(Point为java的内置对象)，计算骑士下一步能走的位置，最多有8个位置
     * @param curPoint
     * @return 包含当前位置能访问的下一个位置的坐标！！！
     *      (坐标横着的是X轴！！！)，X的下标自左向右增大，Y的下标自上向下增大
     */
    public static ArrayList<Point> next(Point curPoint){
        ArrayList<Point> nextList = new ArrayList<>();
        Point p = new Point();

        //表示骑士可以走5这个位置
        if((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y - 1) >= 0){
            nextList.add(new Point(p));
        }

        //表示骑士可以走6这个位置
        if((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y - 2) >= 0){
            nextList.add(new Point(p));
        }

        //表示骑士可以走7这个位置
        if((p.x = curPoint.x + 1) < X && (p.y = curPoint.y - 2) >= 0){
            nextList.add(new Point(p));
        }

        //表示骑士可以走0这个位置
        if((p.x = curPoint.x + 2) < X && (p.y = curPoint.y - 1) >= 0){
            nextList.add(new Point(p));
        }

        //表示骑士可以走1这个位置
        if((p.x = curPoint.x + 2) < X && (p.y = curPoint.y + 1) < Y){
            nextList.add(new Point(p));
        }

        //表示骑士可以走2这个位置
        if((p.x = curPoint.x + 1) < X && (p.y = curPoint.y + 2) < Y){
            nextList.add(new Point(p));
        }

        //表示骑士可以走3这个位置
        if((p.x = curPoint.x - 1) >= 0 && (p.y = curPoint.y + 2) < Y){
            nextList.add(new Point(p));
        }

        //表示骑士可以走4这个位置
        if((p.x = curPoint.x - 2) >= 0 && (p.y = curPoint.y + 1) < Y){
            nextList.add(new Point(p));
        }

        return  nextList;
    }

}
