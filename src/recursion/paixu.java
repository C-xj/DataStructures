package recursion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Chu
 * @create 2021-04-27  15:02
 */
public class paixu {
    public static void main(String[] args) {
        int[]  arr = new int [] {13,45,6,32,88,67,43} ;
        //int [] a=zhiPai(arr,arr.length);
        //System.out.println(Arrays.toString(a));

        //zheZhong(arr,arr.length);
        //System.out.println(Arrays.toString(arr));

        //xiErpaixu(arr,arr.length);
        //System.out.println(Arrays.toString(arr));

        //maoPao(arr,arr.length);
        //System.out.println(Arrays.toString(arr));

        kuaiPai(arr,0,arr.length-1);
        System.out.println(Arrays.toString(arr));

    }

    public static int[] zhiPai(int arr[],int n){
        int i;
        int j;
        int temp;
        for ( i=1;i<n;i++){
            if(arr[i]<arr[i-1]){
                temp=arr[i];
                for ( j=i-1;j>=0 && arr[j]>temp;--j){
                    arr[j+1]=arr[j];
                }
                arr[j+1]=temp;
            }
        }
        return arr;
    }

    public static void zheZhong(int arr[],int n) {
        int i, j,temp, low, high, mid;
        for (i = 1; i < n; i++) {
            temp = arr[i];
            low = 0;
            high = i - 1;
            while (low <= high) {
                mid = (low + high) / 2;
                if (arr[mid] <= temp) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            for (j = i - 1; j >= low; --j) {
                arr[j + 1] = arr[j];
            }
            arr[low] = temp;
        }
    }

    public static void xiErpaixu(int arr[],int n){
        int i,j,temp,d;
        for(d=n/2;d>=1;d=d/2){
            for(i=0+d;i<n;i++){
                if(arr[i]<arr[i-d]){
                    temp=arr[i];
                    for (j=i-d ;j>=0 && arr[j]>temp;j=j-d){
                        arr[j+d]=arr[j];
                    }
                    arr[j+d]=temp;
                }
            }
        }
    }


    public static void maoPao(int arr[],int n){
        int temp;

        for (int i=n-1;i>=0;i--){
            boolean flag=false;

            for (int j=0;j<i;j++){
                if (arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;

                    flag=true;
                }
            }
            if (flag==false){
                return;
            }
        }
    }

    public static void kuaiPai(int arr[],int low,int high){
        if(low<high){
            int pivotpos=Partition(arr,low,high);
            kuaiPai(arr,low,pivotpos-1);
            kuaiPai(arr,pivotpos+1,high);
        }
    }

    public static int Partition(int arr[],int low ,int high){
         int key=arr[low];
         while (low<high){
             while (low<high && arr[high]>key){
                 --high;
             }
             arr[low]=arr[high];  // 把找到的第一个小于key的arr[high]赋给arr[low]
             while (low<high && arr[low]<=key){
                 ++low;
             }
             arr[high]=arr[low];
         }
         arr[low]=key; // low==high
         return high;
    }

}
