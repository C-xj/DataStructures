package Class03_归并_快排_堆排;


/**
 * @author Chu
 * @create 2021-11-15  20:08
 */
// 语言提供的堆结构 vs 手写的堆结构
//     取决于，有没有动态该信息的需求，
//     语言提供的堆结构，如果动态改数据，不保证依然有序
//      手写堆结构，因为增加了对象的位置表，所以能够满足动态改信息的需求。

// 大根堆
public class Code04_Heap01 {

    public static class MyMaxHeap{
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit){
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty(){
            return heapSize == 0;
        }

        public boolean ifFull(){
            return heapSize == limit;
        }

        // 加入新数据，再调整为大根堆
        public void push(int value){
            if (heapSize == limit){
                throw new RuntimeException("heap is full");
            }
            // 堆的下标从0开始，因此heap[heapSize]=value表示新添的第heapSize+1个结点
            heap[heapSize] = value;
            // 新加的数加在了当前树的最后一个数的下一个，要判断并重新调整为大根堆
            //      刚加入的索引位置与其父结点进行比较
            heapInsert(heap,heapSize++);
        }

        // 取出堆顶的数，再调整为大根堆
        // 用户要求返回最大值，并删除，剩下的，依然保持大根堆
        public int pop(){
            int ans = heap[0];  //  取到堆顶元素，后面要返回
            // 把最后一个位置的值，与第一个位置的交换，堆元素减1，这时会被回收。
            swap(heap , 0 ,--heapSize);
            // 将新来到堆顶的元素与子结点中最大的那个进行比较，不断交换下移，重新变成大根堆
            heapify(heap , 0 ,heapSize);
            return ans;
        }

        // 插入数与父结点比大小
        private void heapInsert(int[] arr , int index){
            // arr[index]
            // arr[index] 不比它的父结点大时，停止；index=0时也停止
            while (arr[index] > arr[(index-1) / 2]){
                swap(arr ,index ,(index-1) / 2);
                index = (index -1 ) / 2;
            }
        }

        // 将新来到堆顶的元素与子结点中最大的那个进行比较，不断交换下移，重新变成大根堆
        // 停止条件：当前结点比其子结点大；或者没有子结点
        private void heapify(int[] arr, int index, int heapSize){
            int left = index * 2 + 1;
            while (left < heapSize){
                // 两个子结点中对应值进行比较，返回值大的那个结点的索引
                //     选取右孩子的判断条件：1）有右孩子，2）右孩子的值比左孩子的大，否则为选取左孩子
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left+1 : left;
                // 比较当前结点与其最大的一个子结点的大小，返回对应最大值的索引
                largest = arr[largest] > arr[index] ? largest :index;
                // 如果比较出的索引就是当前结点的索引(即，以当前结点为堆顶的堆已经是大根堆)，不再下移
                if (largest == index){
                    break;
                }
                // 否则交换当前结点与比较出的结点,交换后赋值，继续下移判断。
                swap(arr , largest ,index);
                index = largest;
                left = index * 2 +1;
            }
        }


        private void swap(int[] arr ,int i,int j){
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }


    }

}
