package search;

/**
 * @author Chu
 * @create 2021-05-10  10:36
 */
public class LinearSearch {
    public static void main(String[] args) {
        int [] arr={1,51,6,75,33,2,9,11,66};
        int index = linearSearch(arr, 66);
        if (index==-1){
            System.out.println("没有找到");
        }else {
            System.out.println("找到下标="+index+"对应的数值");
        }

    }

    public static int linearSearch(int arr[],int value){
        // 线性查找是逐一比对，发现有相同值，就返回下标
        for (int i=0;i<arr.length;i++){
            if (arr[i]==value){
                return i;
            }
        }
        return -1;
    }
}
