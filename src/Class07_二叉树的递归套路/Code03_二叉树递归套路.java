package Class07_二叉树的递归套路;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chu
 * @create 2021-11-30  16:51
 */
/*
* 二叉树的递归套路：
*   1）假设以X结点为头，假设可以向X左树和X右树要任何信息
*   2）在上一步的假设下，讨论以X为头结点的树，得到答案的可能性(最重要)
*   3）列出所有可能性后，确定到底需要向左树和右树要什么样的信息
*   4）把左树信息和右树信息求全集，就是任何一颗子树都需要返回的信息S
*   5）递归函数都返回S，每一棵子树都这么要求
*   6）写代码，在代码中考虑如何把左树的信息和右树信息整合除整棵树的信息
*
* 题目一：给定一棵二叉树的头结点head，返回这棵二叉树是不是平衡二叉树(一棵二叉树中的每一棵子树的左子树右子树高度差不超过1)
*
* 题目二：给定一颗二叉树的头结点head，任何两个结点之间都存在距离，返回整棵二叉树的最大距离。
*      思路：
*           ① 与X结点无关：左树最大距离或右树最大距离的最大值
*           ② 与X结点有关：左树高度 + 1 + 右树高度
*
* 题目三：给定一棵二叉树的头结点head，返回这棵二叉树中最大的二叉搜索子树的头结点
*      思路：
*           ① 与X结点无关：左树最大二叉搜索树或右树最大二叉搜索子树的最大值
*           ② 与X结点有关：左树整体是二叉搜索树、右树是二叉搜索树；且左max<X,右min>X
*                           因为左右要求不一样，因此需要求全集：最大子搜索树size、是否是最大搜索树、最大值max、最小值min
*
* 题目四：派对的最大快乐值：
*       公司的每个员工都符合Employee类的描述。整个公司的人员结构可以看作是一颗标准的、没有环的多叉树。
*
*       员工信息的定义如下：
*       class Employee{
*           public int happy;            // 这名员工可以带来的快乐值
*           List<Employee> subordinates; // 这名员工有哪些直接下级
*       }
*
*   派对的最大快乐值
*       这个公司现在要办party，你可以决定哪些员工来，哪些员工不来，规则：
*       1）如果某个员工来了，那麽这个员工的所有直接下级都不能来
*       2）派对的整体快乐值是所有到场员工快乐值的累加
*       3）你的目标是让派对的整体快乐值尽量大
*   给定一颗多叉树的头结点bos，请返回派对的最大快乐值
*
*       思路：
*           ① X来：直接子结点不来情况下，它的子结点整个子树的最大快乐值
*           ② X不来：0 + max{子结点a来的最大... ，子结点a不来的最大...} + max{子结点b来的最大... ，子结点b不来的最大...}
*
* */
public class Code03_二叉树递归套路 {

    public static class Node{
        public int value;
        public Node left;
        public Node right;

        public Node(int data){
            this.value = data;
        }
    }

    // 题目一：
    //      实现方式一：
    public static boolean isBalanced1(Node head){
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head,boolean[] ans){
        if(!ans[0] || head == null){
            return -1;
        }
        int leftHeight =process1(head.left,ans);
        int rightHeight =process1(head.right,ans);
        if (Math.abs(leftHeight - rightHeight) > 1){
            ans[0] = false;
        }
        return Math.max(leftHeight,rightHeight) + 1;
    }

    //      实现方式二：
    public static boolean isBalanced2(Node head){
        return process2(head).isBalanced;
    }

    // 左右要求一样，Info信息返回的结构体
    public static class Info{
        public boolean isBalanced;
        public int height;

        public Info(boolean isBalanced ,int height){
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static Info process2(Node head){
        if (head == null){
            return new Info(true,0);
        }

        Info leftInfo = process2(head.left);
        Info rightInfo = process2(head.right);

        int height = Math.max(leftInfo.height,rightInfo.height) + 1;

        boolean isBalanced = true;

        if (!leftInfo.isBalanced || !rightInfo.isBalanced || Math.abs(leftInfo.height - rightInfo.height) > 1){
            isBalanced = false;
        }
        return new Info(isBalanced, height);
    }


    // 题目二;
    public static int maxDistance2(Node head){
        return process(head).maxDistance;
    }

    public static class Info2{
        public int maxDistance;
        public int height;

        public Info2(int maxDistance,int height){
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static  Info2 process(Node head){
        if (head == null){
            return new Info2(0,0);
        }
        Info2 leftInfo = process(head.left);
        Info2 rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 两种情况的，三种可能的最大距离进行比较
        int maxDistance = Math.max(Math.max(leftInfo.maxDistance,rightInfo.maxDistance),
                leftInfo.height + rightInfo.height + 1);

        return new Info2(maxDistance, height);
    }


    // 题目三：

    // 实现方式一：
    // 获取当前结点开始的二叉搜索子树大小
    public static int getBSTSize(Node head){
        if (head == null){
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        // 按中序遍历将结点的值顺序存入
        in(head,arr);
        // 遍历存在后一个小于前一个的情况，说明不是二叉搜索子树，size返回0；
        for (int i =1 ; i < arr.size(); i++ ){
            if (arr.get(i).value <= arr.get(i-1).value){
                return 0;
            }
        }
        // 是二叉搜索子树，则返回当前子树的结点个数
        return arr.size();
    }

    // 中序遍历
    public static void in(Node head,ArrayList<Node> arr){
        if (head == null){
            return;
        }
        in(head.left,arr);
        arr.add(head);
        in(head.right,arr);
    }


    // 递归获取最大二叉搜索树
    public static int maxSubBSTSize1(Node head){
        if (head == null){
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0){
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left),maxSubBSTSize1(head.right));
    }


    // 实现方式二：
    // 对于任何子树都返回四个信息
    public static class Info3{

        public boolean isAllBST;
        public int maxSubBSTSize;
        public int min;
        public int max;

        public Info3(boolean isAllBST, int maxSubBSTSize, int min, int max) {
            this.isAllBST = isAllBST;
            this.maxSubBSTSize = maxSubBSTSize;
            this.min = min;
            this.max = max;
        }
    }

    public static Info3 process3(Node head){
        if (head == null){
            return null;
        }
        // 先得到当前结点的左树、右树信息
        Info3 leftInfo = process3(head.left);
        Info3 rightInfo = process3(head.right);

        int min = head.value;
        int max = head.value;
        int maxSubBSTSize = 0;
        if (leftInfo != null){
            min = Math.min(min , leftInfo.min);
            max = Math.min(max , leftInfo.max);
            maxSubBSTSize = Math.max(maxSubBSTSize,leftInfo.maxSubBSTSize);
        }
        if (rightInfo != null){
            min = Math.min(min , rightInfo.min);
            max = Math.min(max , rightInfo.max);
            maxSubBSTSize = Math.max(maxSubBSTSize,rightInfo.maxSubBSTSize);
        }
        boolean isBST = false;
        if ((leftInfo == null ? true : (leftInfo.isAllBST && leftInfo.max < head.value))
                && (rightInfo == null ? true :(rightInfo.isAllBST && rightInfo.min > head.value))){
            isBST = true;
            maxSubBSTSize = (leftInfo == null ? 0 : leftInfo.maxSubBSTSize)
                    + (rightInfo == null ? 0 : rightInfo.maxSubBSTSize) + 1;
        }
        return new Info3(isBST,maxSubBSTSize,min,max);
    }


    public static int maxSubBSTSize2(Node head){
        if (head == null){
            return 0;
        }
        return process3(head).maxSubBSTSize;
    }


    // 题目四：
    public static class Employee{
        public int happy;
        public List<Employee> nexts;    // 其多个直接下级

        public Employee(int happy) {
            this.happy = happy;
            nexts = new ArrayList<>();
        }
    }

    // 实现方式一：
    public static int maxHappy1(Employee boss){
        if(boss == null){
            return 0;
        }
        return process4(boss,false);
    }

    public static int process4(Employee cur, boolean up){
        // true表示当前结点不能来，
        if (up){
            int ans = 0;
            for (Employee next : cur.nexts){
                // 当前结点不能来，累加它的子结点(可来、可不来)
                ans += process4(next,false);
            }
            return ans;
        // false表示当前结点可以来
        }else {
            int p1 = cur.happy; // 当前结点的子结点来的快乐值
            int p2 = 0;         // 当前结点的子结点不来的快乐值
            for (Employee next : cur.nexts){
                p1 += process4(next,true);
                p2 += process4(next,false);
            }
            // 返回最大快乐值
            return Math.max(p1,p2);
        }
    }

    // 实现方式二：
    public static class Info4{
        public int yes; // 头结点来的时候整棵树的最大快乐值
        public int no;  // 头结点不来的时候整棵树的最大快乐值

        public Info4(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static Info4 process5(Employee x){
        if (x.nexts.isEmpty()){
            return new Info4(x.happy,0);
        }
        // 当前结点来
        int yes = x.happy;
        // 当前结点不来
        int no = 0 ;
        for (Employee next : x.nexts){
            Info4 nextInfo = process5(next);
            // 当前结点来，就累加其子结点不来
            yes += nextInfo.no;
            // 当前结点不来，就累加其子结点来或者不来两种情况的最大值
            no += Math.max(nextInfo.yes,nextInfo.no);
        }
        return new Info4(yes,no);
    }

    public static int maxHappy2(Employee boss){
        if (boss == null){
            return 0;
        }
        Info4 all = process5(boss);
        // 返回最大的快乐值
        return Math.max(all.yes,all.no);
    }


}
