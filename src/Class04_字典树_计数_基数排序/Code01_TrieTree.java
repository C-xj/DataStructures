package Class04_字典树_计数_基数排序;

import java.util.HashMap;

/**
 * @author Chu
 * @create 2021-11-17  16:19
 */
//  前缀树（字典树）
/*
 * 1）单个字符串中，字符从前到后的加到一棵多叉树上
 * 2）字符放在路上，结点上有专属的数据项(常见的是pass-通过这个结点的次数和end-以此结点为结尾的个数)
 * 3）所有样本都这样添加，如果没有路就新建，如果有路就复用
 * 4）沿途结点的pass值增加1，每个字符串结束时来到的结点end值增加1
 *   生成这个树：每一个字符的代价都是O(1)
 * */
public class Code01_TrieTree {


    // 实现方式一：
    public static class Node1{
        public int pass;
        public int end;
        public Node1[] nexts;


        public Node1(){
            pass = 0;
            end = 0;
            nexts = new Node1[26];
        }

    }

    public static class Trie1{
        private Node1 root;

        public Trie1(){
            root = new Node1();
        }

        public void insert(String word){
            if(word == null){
                return;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0;i < chars.length ; i++){ // 从左往右遍历字符
                index = chars[i] - 'a';     //  由字符，对应成走向哪条路
                if (node.nexts[index] == null){
                    node.nexts[index] = new Node1();
                }
                node = node.nexts[index];
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word){

            if (search(word) !=0){
                char[] chars = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0;i < chars.length ; i++) { // 从左往右遍历字符
                    index = chars[i] - 'a';     //  由字符，对应成走向哪条路
                    if (--node.nexts[index].pass == 0) {
                        node.nexts[index] = null;
                        return;
                    }
                    node = node.nexts[index];
                }
                node.end--;
            }
        }


        // word这个单词之前加入过几次
        public int search(String word){
            if (word == null){
                return 0;
            }
            char[] chars = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0 ;i < chars.length ; i++){
                index = chars[i] - 'a';
                if (node.nexts[index] == null){
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }


        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre){
            if (pre == null){
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0 ;i < chars.length ; i++){
                index = chars[i] - 'a';
                if (node.nexts[index] == null){
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }

    }

    // 实现方式二：
    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;


        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2{
        private Node2 root;

        public Trie2(){
            root = new Node2();
        }

        public void insert(String word){
            if(word == null){
                return;
            }
            char[] chars = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0;i < chars.length ; i++){ // 从左往右遍历字符
                index = (int) chars[i] ;
                if (!node.nexts.containsKey(index)){
                    node.nexts.put(index,new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word){
            if (search(word) !=0){
                char[] chars = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0;i < chars.length ; i++) { // 从左往右遍历字符
                    index = (int) chars[i] ;
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }


        // word这个单词之前加入过几次
        public int search(String word){
            if (word == null){
                return 0;
            }
            char[] chars = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0 ;i < chars.length ; i++){
                index = (int) chars[i] ;
                if (!node.nexts.containsKey(index)){
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }


        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre){
            if (pre == null){
                return 0;
            }
            char[] chars = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0 ;i < chars.length ; i++){
                index = (int) chars[i];
                if (!node.nexts.containsKey(index)){
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }

    }

    // 验证
    public static class Right{

        private HashMap<String,Integer> box;

        public Right(){
            box = new HashMap<>();
        }

        public void insert(String word){
            if (!box.containsKey(word)){
                box.put(word,1);
            }else {
                box.put(word,box.get(word) + 1);
            }
        }

        private void delete(String word){
            if (box.containsKey(word)){
                box.put(word,1);
            }else {
                box.put(word,box.get(word)+1);
            }
        }


        public int search(String word){
            if (!box.containsKey(word)){
                return 0;
            }else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre){
            int count = 0;
            for (String cur : box.keySet()){
                if (cur.startsWith(pre)){
                    count += box.get(cur);
                }
            }
            return count;
        }

    }




}
