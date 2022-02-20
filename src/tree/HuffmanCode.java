package tree;

import java.util.*;

/**
 * @author Chu
 * @create 2021-05-15  20:13
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String str="i like like like java do you like a java";
        byte[] strBytes=str.getBytes();
        System.out.println(strBytes.length);  //转之前的长度为40

        //测试 创建哈夫曼树
        Node2 huffmanTree = createHuffmanTree(strBytes);
        PreShowHuffmanTree(huffmanTree);


        // 生成哈夫曼树对应的哈夫曼编码
        // 思路：
        // 1、将哈夫曼编码表存放在Map<Byte,String>形式  key-对应字母，value-对应字母编码
        //  例如 32->01 ......
        HashMap<Byte,String> huffmanCodes = new HashMap<>();

        // 2、在生成哈夫曼编码表示，需要去拼接路径，定义一个StringBuilder存储某个叶子节点的路径
        StringBuilder stringBuilder=new StringBuilder();

        getCodes(huffmanCodes,huffmanTree,"",stringBuilder); //stringBuilder下面定义了
        System.out.println("生成的哈夫曼编码表"+huffmanCodes);


        //测试哈夫曼编码数据
        byte[] huffmanCodeBytes = zip(strBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes="+Arrays.toString(huffmanCodeBytes));


        // 测试解码
        byte[] sourceBytes=decode(huffmanCodes,huffmanCodeBytes);
        System.out.println("原来的字符串="+new String(sourceBytes));

    }



    // 先序遍历哈夫曼树
    public static void PreShowHuffmanTree(Node2 root){
        if (root!=null){
            System.out.println(root);
            PreShowHuffmanTree(root.left);
            PreShowHuffmanTree(root.right);
        }
    }

    // 创建哈夫曼树的方法
    public  static Node2 createHuffmanTree(byte[] strBytes){

        //创建一个ArrayList
        ArrayList<Node2> nodes = new ArrayList<>();

        // 遍历bytes，统计每一个byte出现的次数->map[key,value]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b:strBytes){
            Integer count=counts.get(b);
            if (count == null){ //Map还没有这个字符数据，第一次
                counts.put(b,1);
            }else {
                counts.put(b,count+1);
            }
        }

        // 把每一个键值对转成一个Node2对象，并加入到nodes集合
        for (Map.Entry<Byte,Integer> entry:counts.entrySet()){
            nodes.add(new Node2(entry.getKey(), entry.getValue()));
        }


        while (nodes.size()>1){  //最后集合中只剩一个元素的时候结束

            // 排序，从小到大
            Collections.sort(nodes); //有 Node2实现了Comparable接口重写了ComparTo

            //System.out.println(nodes);

            // 取出根结点权值最小的两棵二叉树
            // （1）取出权值最小的结点（二叉树）
            Node2 leftNode = nodes.get(0);
            // （2）取出权值最小的结点（二叉树）
            Node2 rightNode = nodes.get(1);

            // （3）构建一颗新的二叉树  没有data，只有权值
            Node2 parent = new Node2(null,leftNode.weight + rightNode.weight);
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


    /*
    * 功能：将传入的node结点的所有叶子结点的哈夫曼编码得到，并放入到huffmanCodes集合
    * huffmanCodes 哈夫曼编码表存放在Map<Byte,String>
    * node 传入的结点
    * code 路径 左子结点是0，右子结点是1
    * stringBuilder 用于拼接路径
    * */
    private static void getCodes(HashMap huffmanCodes,Node2 node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        // 将code加入到stringBuilder2
        stringBuilder2.append(code);
        if (node!=null){  // 如果node==null不处理
            // 判断当前node是叶子结点还是非叶子结点
            if (node.data==null){ //非叶子结点
                //递归处理
                //向左递归
                getCodes(huffmanCodes,node.left,"0",stringBuilder2);
                //向右递归
                getCodes(huffmanCodes,node.right,"1",stringBuilder2);
            }else {  // 说明是叶子结点
                // 表示找到某个叶子结点的最后
                huffmanCodes.put(node.data,stringBuilder2.toString());
            }
        }
    }


    // 编写一个方法，将字符串对应的byte[]数组，通过生成的哈夫曼编码表，
    // 返回一个哈夫曼编码压缩后的byte[]
    /*
    * bytes 这是原始的字符串对应的byte[]
    * huffmanCodes 生成的哈夫曼编码 集合 map<key，value>
    * 返回哈夫曼编码处理后的byte[]
    * 对应的byte[] huffmanCodeBytes ，即8位对应一个byte，放入到huffmanCodeBytes
    * huffmanCodeBytes[0]=10101000（补码）=>byte
    * [推导 10101000=> 10101000-1=> 10100111(反码)=> 11011000 = -88
    * */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){

        //1、利用huffmanCodes将bytes转成哈夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes 数组
        for (byte b:bytes){
            stringBuilder.append(huffmanCodes.get(b)); //添加对应字符的哈夫曼编码
        }

        // 将“101010001011111110...”转为byte[]
        //统计返回byte[] huffmanCodeBytes长度

        int len;
        // 一句话同理 int len=(stringBuilder.length()+7)/8;
        if (stringBuilder.length()%8==0){
            len=stringBuilder.length()/8;
        }else {
            len=stringBuilder.length()/8+1;
        }

        // 创建存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index=0; //记录是第几个byte
        for (int i=0;i<stringBuilder.length();i+=8){ // 因为每8位对应一个byte
            String strbyte;

            if (i+8>stringBuilder.length()){ //不够8位
                strbyte=stringBuilder.substring(i);
            }else {
                strbyte=stringBuilder.substring(i,i+8);
            }

            // 将strbyte转成一个byte，放入到huffmanCodes
            huffmanCodeBytes[index]=(byte) Integer.parseInt(strbyte,2);
            index++;
        }
        return huffmanCodeBytes;
    }


    /*
    * 将一个byte转成一个二进制的字符串
    *  flag 标志是否需要补高位，如果是true，表示需要补高位
    *  return 返回的是b对应的二进制的字符串，（注意是按补码返回）
    * */
    private static String byteToBitString(boolean flag,byte b){
        // 使用变量保存b
        int temp = b; // 将b转成int

        // 如果是整数还存在补高位
        if(flag){
            temp |=256; // 按位与256   0001 0000 0000

        }

        String str=Integer.toBinaryString(temp); // 返回的是temp二进制的补码

        if (flag){
            return str.substring(str.length()-8);
        }else {
            return str;
        }
    }

    //编写一个方法，完成对应压缩数据的解码
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte [] huffmanBytes){
        // 1、先得到huffmanBytes对应的二进制的字符串，形式1010100010111...
        StringBuilder stringBuilder = new StringBuilder();
        // 将byte数组转成二进制的字符串
        for (int i=0;i<huffmanBytes.length;i++){
            byte b =huffmanBytes[i];
            //判断是不是最后一个字节
            boolean flag=(i==huffmanBytes.length-1);
            stringBuilder.append(byteToBitString(!flag,b));
        }

        //把字符串安装指定的哈夫曼编码进行解码
        // 把哈夫曼编码表进行调换，因为反向查询a->100 100->a
        HashMap<String, Byte> stringByteHashMap = new HashMap<>();
        for (Map.Entry<Byte,String> entry:huffmanCodes.entrySet()){
            stringByteHashMap.put(entry.getValue(), entry.getKey());//反向
        }

        // 创建集合，存放byte
        ArrayList<Byte> list = new ArrayList<>();
        for (int i=0;i<stringBuilder.length();){
            int count=1; // 小的计数器
            boolean flag=true;
            Byte b=null;

            while (flag){
                //取出一个'1' '0'
                String key=stringBuilder.substring(i,i+count);
                //i不动，count移动，直到匹配到一个字符
                b=stringByteHashMap.get(key);
                if (b==null) {//说明没有匹配到
                    count++;
                }else {
                    //匹配到
                    flag=false;
                }
            }
            list.add(b);
            i+=count; // i直接移动到count
        }
        // 当for循环结束后，list中就存放了所有字符
        // 把list中数据放入到byte[]并返回
        byte b[] =new byte[list.size()];
        for (int i=0;i<b.length;i++){
            b[i]=list.get(i);
        }
        return b;
    }

}


//创建Node2，数据和权值
class Node2 implements Comparable<Node2>{
    Byte data;  // 存放数据本身，比如‘a’=>97
    int weight; // 权值，表示字符出现的次数
    Node2 left;
    Node2 right;

    public Node2(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node2 o) {
        return this.weight-o.weight; //从小到大排序
    }

    @Override
    public String toString() {
        return "Node2{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}