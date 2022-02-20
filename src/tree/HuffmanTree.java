package tree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Chu
 * @create 2021-05-14  15:46
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int [] arr={13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(arr);
        PreShowHuffmanTree(huffmanTree);
    }


    // 先序遍历哈夫曼树
    public static void PreShowHuffmanTree(Node root){
        if (root!=null){
            System.out.println(root);
            PreShowHuffmanTree(root.left);
            PreShowHuffmanTree(root.right);
        }
    }


    // 创建哈夫曼树的方法
    public  static Node createHuffmanTree(int[] arr){
        // 第一步为了操作方便
        // 1、遍历arr数组
        // 2、将arr的每个元素构成一个Node
        // 3、将Node放入到ArrayList中
        ArrayList<Node> nodes = new ArrayList<>();
        for (int value:arr){
            nodes.add(new Node(value));
        }

        while (nodes.size()>1){  //最后集合中只剩一个元素的时候结束

            // 排序，从小到大
            Collections.sort(nodes);

            //System.out.println(nodes);

            // 取出根结点权值最小的两棵二叉树
            // （1）取出权值最小的结点（二叉树）
            Node leftNode = nodes.get(0);
            // （2）取出权值最小的结点（二叉树）
            Node rightNode = nodes.get(1);

            // （3）构建一颗新的二叉树
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left=leftNode;
            parent.right=rightNode;

            //(4)从ArrayList删除处理过的二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            // (5)将parent加入到集合nodes中
            nodes.add(parent);

        }
        // 返回哈夫曼树的根结点  即：集合中最后一个元素
        return nodes.get(0);
    }
}


//创建结点类
// 为了让Node对象实现Collections集合排序
// 让Node实现Comparable接口
class Node implements Comparable<Node>{
    int value; //结点权值
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value-o.value;  // 从小到大排序
    }
}