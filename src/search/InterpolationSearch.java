package search;

/**
 * @author Chu
 * @create 2021-05-11  10:36
 */
public class InterpolationSearch {
    public static void main(String[] args) {

        int[] arr = new int[100];
        for (int i=0;i<100;i++){
            arr[i]=i+1;
        }

        int valueIndex=InterValueSearch(arr,0,arr.length-1,6);
        System.out.println("查找到value对应的下标"+valueIndex);
    }

    // 使用递归  编写插值查找
    public static int InterValueSearch(int arr[],int low,int high,int vaule){
        /*
        * 如果找到返回对应下标，如果没有找到，就返回-1
        *  vaule<arr[0] ||vaule>arr[arr.length-1]必须要有，否则mid可能越界
        *
        * */
        if (low>high || vaule<arr[0] ||vaule>arr[arr.length-1]){
            return -1;            // 优化，减少查找时间
        }

        // 插值查找的自适应 mid
        int mid=low+(low+high)*(vaule-arr[low])/(arr[high]-arr[low]);

        if (vaule==arr[mid]) {
            return mid;
        } else if (vaule<arr[mid]){        // 向前半部分递归
            return InterValueSearch(arr,low,mid-1,vaule);
        }else {                           // 向后半部分递归
            return InterValueSearch(arr,mid+1,high,vaule);
        }
    }

}
