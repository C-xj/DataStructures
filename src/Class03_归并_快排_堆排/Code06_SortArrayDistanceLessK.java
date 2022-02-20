package Class03_归并_快排_堆排;

import java.util.PriorityQueue;

/**
 * @author Chu
 * @create 2021-11-16  18:59
 */
// 已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离
//      不超过k，并且k相对于数组长度来说是比较小的。
// 分析：
//      1）k是多少就先把数组的前k+1个数放进小根堆，
//      2）先将小根堆中弹出最小值放到数组中，然后将数组中下个数加入小根堆
//      3）循环将数组的数都加进去之后，最后把小根堆的最小值循环全部弹出放到数组中。

public class Code06_SortArrayDistanceLessK {

    public void sortedArrDistanceLessK(int[] arr, int k){
        // 默认小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 控制数组下标位置，以便后面加入对应位置的数
        int index = 0;
        // 先将数组前k+1加入小根堆(0..K)，如果数组长度小于k，那数组元素全加进去
        for (; index <= Math.min(arr.length-1,k); index++){
            heap.add(arr[index]);
        }
        int i = 0;
        // 先弹出一个最小值，再加入一个新的值
        for (; index < arr.length ; i++,index++){
            arr[i] = heap.poll();
            heap.add(arr[index]);
        }
        // 当数组中的数都加入小根堆，那就循环弹出小根堆中的数
        while (!heap.isEmpty()){
            arr[i++] = heap.poll();
        }
    }

}

