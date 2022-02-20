package Algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Chu
 * @create 2021-05-22  15:56
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 创建广播电台，放入到一个Map中  key不能重复，value没有限制
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        // 将各个电台放入到broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        //加入到map
        broadcasts.put("K1",hashSet1);
        broadcasts.put("K2",hashSet2);
        broadcasts.put("K3",hashSet3);
        broadcasts.put("K4",hashSet4);
        broadcasts.put("K5",hashSet5);

        // 存放所有地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.addAll(hashSet1);  // addAll添加全部，自动去重
        allAreas.addAll(hashSet2);
        allAreas.addAll(hashSet3);
        allAreas.addAll(hashSet4);
        allAreas.addAll(hashSet5);

        /*allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");*/

        // 创建ArrayList，存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();

        //定义一个临时的集合，在遍历过程中，存放各个电台与剩余未覆盖地区的交集
        HashSet<String> tempSet = new HashSet<>();

        // 定义一个maxKey保存在一次遍历中，能够覆盖最多未覆盖地区对应的电台的key
        // 如果maxKey(存放key 即电台)不为null，则会加入到selects
        // len存放遍历过后 电台与未覆盖地区交集元素的最大个数
        String maxKey=null;
        int len=0;

        while (allAreas.size()!=0){  // 如果allAreas部位0，则表示还有未覆盖的地区

            // 没进行一次while，把上一次存放电台的maxKey置空，同时len也置零
            maxKey=null;
            len=0;

            // 遍历broadcasts，取出对应的key
            for (String key:broadcasts.keySet()){
                // 每进行一次for，就要把tempSet清空，
                //              以备存下一个电台的元素进行交集运算
                tempSet.clear();

                //当前key能够覆盖的地区
                HashSet<String> areas=broadcasts.get(key);
                tempSet.addAll(areas);  //放入临时集合中
                // 求出stmpSet 和allAreas集合的交集，交集重新赋给tempSet
                tempSet.retainAll(allAreas);

                // templen存放本轮中交集个数
                int templen=tempSet.size();

                // 如果当前电台与未覆盖的地区集合有交集
                //  且 （ maxKey没有被赋值
                //  或  当前交集元素个数大于上一轮电台与未覆盖地区集合的交集元素的个数
                //                   templen>len   体现出贪心算法的特点
                if (templen >0 && (maxKey==null || templen>len)){
                //if (tempSet.size() >0 && (maxKey==null || tempSet.size()>broadcasts.get(maxKey).size())){
                    maxKey = key;    // 把电台更换为当前电台
                    len = templen;   //就把当前交集个数赋给len
                }
            }
            //maxKey不为null，就应该将maxKey加入到selects
            if (maxKey != null){
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的地区从allAreas中去掉
                allAreas.removeAll(broadcasts.get(maxKey));
            }

        }
        System.out.println("得到的选择结果是"+selects);
    }

}
