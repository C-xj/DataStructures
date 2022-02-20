package 大厂刷题;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Chu
 * @create 2022-01-12  14:58
 */
/*
*   给定一个整型数组arr，打印其中出现次数大于一半的数，如果没有这样的数，打印提示信息
*       要求：时间O(N),空间O(1)   因此不能使用hash表
*
*   (补充知识点)
*   摩尔投票：
*       核心就是对拼消耗。玩一个诸侯争霸的游戏，假设你方人口超过总人口一半以上，并且能保证每个人口出去干仗都能一对一同归于尽。最后还有人活下来的国家就是胜利。那就大混战呗，最差所有人都联合起来对付你（对应你每次选择作为计数器的数都是众数），或者其他国家也会相互攻击（会选择其他数作为计数器的数），但是只要你们不要内斗，最后肯定你赢。最后能剩下的必定是自己人。
*
*       计数器：如果我们设置一个计数器，在遍历时遇到不同于这个群体的人时就将计数器-1，遇到同个群体的人时就+1，只要在计数器归0时就重新假定当前元素代表的群体为人数最多的群体再继续遍历，那么到了最后，计数器记录的那个群体必定是人最多的那个群体。
*
*   思路方法：
*       1) 遍历arr，一次删除2个不同值的数(同归于尽)，看谁会剩下来，
*              ① 没有数剩下，则没有水王数
*              ② 剩下x，再遍历一遍arr，查看x出现的次数与N/2做对比，大于则是水王数。
*
*       两个整型变量，int 候选 = 0，int 血量 = 0;
*           1) 如果无候选：当前数立为候选，血 = 1
*           2) 如果有候选：① 当前数 != 候选，血量--；② 当前数 == 候选，血量++
*
*   拓展：
*       给定一个正数K，返回所有出现次数 > N/K的数
*
* */
public class Q31_超级水王_摩尔投票_拓展 {

    // 超级水王/摩尔投票 思想方法一：
    public static void printHalfMajor1(int[] arr){
        int candidate = 0;
        int HP = 0;
        for (int i = 0;i < arr.length;i++){
            if (HP == 0){
                candidate = arr[i];
                HP = 1;
            }else if (arr[i] == candidate){
                HP++;
            }else{
                HP--;
            }
        }
        if (HP == 0){
            System.out.println("no such number.");
            return;
        }
        HP = 0;    // 变量复用，表示最后剩余那个数的次数
        for (int i = 0;i < arr.length;i++){
            if (arr[i] == candidate){
                HP++;
            }
        }
        if (HP > arr.length / 2){
            System.out.println(candidate);
        }else {
            System.out.println("no such number.");
        }
    }

    /* 拓展：一个数组arr长度N，给定一个正数K，返回所有出现次数 > N/K的数
    *
    *   思路：创建一个表，长度K-1(即存K-1个候选，因为最多只有K-1个)，同上依次放入候选后，
    *           如果再来的数与所有候选都不同，则都减一点血量，血量为0，下一个元素成为新的候选。
    */
    public static void printKMajor2(int[] arr,int K){
        if (K < 2){
            System.out.println("the value of K is invalid");
            return;
        }
        // 攒候选，candidates，候选表，最多K-1条记录！ > N/K次的数字，最多右K-1个
        HashMap<Integer, Integer> candidates = new HashMap<>();
        for (int i = 0;i != arr.length;i++){
            if (candidates.containsKey(arr[i])){      // 当前数是候选，血量+1
                candidates.put(arr[i],candidates.get(arr[i] + 1));
            }else {   // 当前数不是候选(即没在候选名单中找到)
                if (candidates.size() == K - 1){     // 候选名额满的情况,没一个候选-1血量，血量变成0的候选，要删掉！
                    allCandidatesMinusOne(candidates);
                }else {
                    candidates.put(arr[i], 1);      // 候选名额没满的情况，将当前数加入候选
                }
            }
        }
        // 所有可能的候选，都在candidates表中！遍历一遍arr，每个候选收集真实次数做判断
        HashMap<Integer,Integer> reals = getReals(arr,candidates);
        boolean hasPrint = false;
        for (Map.Entry<Integer,Integer> set : candidates.entrySet()){
            Integer key = set.getKey();
            if (reals.get(key) > arr.length / K){
                hasPrint = true;
                System.out.println(key + " ");
            }
        }
        System.out.println(hasPrint ? " " : "no such number");

    }

    /*当候选值达到k-1时更新候选值的次数*/
    public static void allCandidatesMinusOne(HashMap<Integer,Integer> map){
        LinkedList<Integer> removeList = new LinkedList<>();
        for (Map.Entry<Integer,Integer> set : map.entrySet()){
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1){    // 如果只剩最后一点血，就减血(此过程可省)，移除该候选(加入移除表)
                removeList.add(key);
            }
            map.put(key,value - 1);
        }
        for (Integer removeKey : removeList){
            map.remove(removeKey);
        }
    }

    /*获取候选值在原数组中出现的总次数*/
    private static HashMap<Integer,Integer> getReals(int[] arr, HashMap<Integer,Integer> cands) {
        HashMap<Integer,Integer> reals=new HashMap<>();
        for (int i=0;i!=arr.length;i++){
            int curNum=arr[i];
            if (cands.containsKey(curNum)){
                if (reals.containsKey(curNum)){
                    reals.put(curNum,reals.get(curNum)+1);
                }else{
                    reals.put(curNum,1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr=new int[]{1,5,2,2,2,1,1};
        printKMajor2(arr, 3);

    }

}
