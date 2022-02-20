package recursion;

/**
 * @author Chu
 * @create 2021-04-25  10:20
 */
public class RecursionTest {
    public static void main(String[] args) {
        //PrintTest(4);
        System.out.println(factorial(5));
    }

    public static void PrintTest(int n){
        if(n>2){
            PrintTest(n-1);
        }
        System.out.println(n);
    }

    public static int factorial(int n){
        if (n==1){
            return 1;

        }else {
            return factorial(n-1)*n;
        }
    }
}
