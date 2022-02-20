package Sort;

import java.util.Arrays;


/**
 * @author Chu
 * @create 2021-05-09  14:04
 *
 *
 *
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr ={53,17,78,9,45,65,87,32};
        Heapsort(arr,arr.length);

        System.out.println(Arrays.toString(arr));
    }



    public static void Heapsort(int arr[],int len){
        BuildMaxHeap(arr,len);          // 初始建堆
        for (int i=len-1;i>0;i--){        // n-1趟交换和建堆过程
            swap(arr,0,i);          // 堆顶元素索引0和堆底元素交换
            HeadAdjust(arr,0,i); // 把剩余的待排序元素整理成大根堆
             // 索引0位置的最大值被换到i(len-1)，剩余[0,len-1]调整为大根堆
        }
    }

    /**
     * 交换元素
     * @param arr
     * @param a 元素的下标
     * @param b 元素的下标
     */
    public static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }



    public static void BuildMaxHeap(int arr[],int len){
        // arr元素索引是从0开始的，所以最后一个非终端结点len/2-1
                                    // ---即arr.length/2-1
        for(int i=len/2-1;i>=0;i--){  // 从后往前(树-从下往上)
                                        // 遍历调整所有非终端结点
            HeadAdjust(arr,i,len);    //调整堆
        }
    }


    // i代表非终端结点 ，len代表树中元素的个数（数组的长度）
    public static void HeadAdjust(int arr[],int i,int len){
        int temp=arr[i];  // temp暂存子树的根结点，后续可能被交换
        for (int k=2*i+1;k<len;k=2*k+1){
            // //2*i+1为左子树i的左子树(因为i是从0开始的), 2*k+1为k的左子树
            // 沿值较大的子结点向下筛选
            if (k+1<len && arr[k]<arr[k+1]){  //如果有右子树,并且右子树大于左子树
                k++;    // 取到当前非终端结点的左右孩子值最大对应的索引
            }
            if (temp>=arr[k]){
                break;      // 根结点比左右孩子中最大的都大，不用交换，结束循环
            }else {   // 如果小于最大的就要交换
                arr[i]=arr[k];  // 把当前结点的左右孩子中最大的赋给当前结点
                i=k;    // 当前结点的值下坠，可能导致下层不满足大根堆，
                    // 因此从被交换的孩子索引（交换后当前结点对应的位置），继续向下筛选
            }
        }
        arr[i]=temp; // 最后把当前结点的值赋给最后k停留的位置
    }

}
