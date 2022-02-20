package search;

import com.sun.jdi.PathSearchingVirtualMachine;

import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-05-11  13:24
 */
public class FibonacciSearch {
    public static int maxSize=20;
    public static void main(String[] args) {
        int [] arr={1,8,10,89,1000,1234};

        System.out.println(FibSearch(arr,1000)); // 返回下标4
    }

    // 因为后面mid=low+F[k-1]-1，需要使用到斐波那契数列，故要获取一个此数列
    // 非递归方法获取一个斐波那契数列
    public static  int[] fib(){
        int[] Fib = new int[maxSize];
        Fib[0]=1;
        Fib[1]=1;
        for (int i=2;i<maxSize;i++){
            Fib[i]=Fib[i-1]+Fib[i-2];
        }
        return Fib;
    }

    //   编写斐波那契查找算法   使用非递归方法编写
    // arr被查询的数组、value被查询的值，return返回对应下标，没有则返回-1
    public static int FibSearch(int[] arr,int value){
        int low=0;
        int high=arr.length-1;
        int k=0;  // 表示斐波那契分割数值的下标
        int mid=0;
        int [] Fib=fib(); // 调用斐波那契方法获取斐波那契数列

        // 获取到斐波那契数列的下标
        while (arr.length>Fib[k]-1){
            k++;
        }
        // 因为Fib[k]值可能大于arr的长度（不一定刚好相等），
        // 因此需要适用Array类，构造一个新的数组，并指向arr[]，
        // 不足的部分会使用0填充
        int[] temp= Arrays.copyOf(arr,Fib[k]);
        // 例如，temp={1,8,10,89,1000,1234,0,0,0}  就不是有序的了

        // 实际上需要使用arr数组最后的数值填充temp
        // temp={1,8,10,89,1000,1234,1234,1234,1234}  变为有序
        for (int i=high+1;i<temp.length;i++){
            temp[i]=arr[high];
        }

        //使用while循环来处理，找到value
        while (low<=high){
            mid=low+Fib[k-1]-1;
            //        (F[k]-1）=（F[k-1]-1）+（F[k-2]-1）+1
            if (value<temp[mid]){  // 向数组左半部分查找
                high=mid-1;
                k--;    // 左半部分（长度）再拆分的前提，k--，
                                    // 让F[k-1]-1做F[k]-1，故k要-1
            }else if (value>temp[mid]){  // 向数组右半部分查找
                low=mid+1;
                k-=2;   // 右半部分（长度）再拆分的前提，k-2，
                                    // 让F[k-2]-1做F[k]-1，故k要-2
            }else {  // 找到
                // 需要确定返回的是哪个下标   返回小的
                if (mid<=high){
                    return mid;
                }else {
                    return high;
                }
            }
        }
        return -1;   // 没找到 返回-1
    }
}
