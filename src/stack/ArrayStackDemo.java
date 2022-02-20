package stack;

import java.util.Scanner;

/**
 * @author Chu
 * @create 2021-04-15  16:23
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        // 先创建一个ArrayStack对象---表示栈
        ArrayStack arrayStack = new ArrayStack(3);

        String key="";  // 保存后续选择
        boolean loop=true; // 控制是否退出菜单
        Scanner scanner = new Scanner(System.in);//获取一个扫描器

        while (loop){
            System.out.println("show:表示显示栈");
            System.out.println("exit:退出栈");
            System.out.println("push:表示添加数据到栈（入栈）");
            System.out.println("pop:表示从栈取出数据（出栈）");
            System.out.println("请输入你的选择");
            key=scanner.next();
            switch (key){
                case "show":
                    arrayStack.showStack();
                    break;
                case "push":
                    System.out.println("请输入你要添加的数：");
                    int value=scanner.nextInt();
                    arrayStack.stackPush(value);
                    break;
                case "pop": // 在里面抛出了异常，可能有异常，try-catch
                    try {
                        int res=arrayStack.stackPop();
                        System.out.printf("出栈的数据为%d\n",res);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop=false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }
}

// 定义一个ArrayStack表示栈
class ArrayStack{
    private int maxSize; // 栈的大小
    private int[] stack; // 数组模拟栈，数据就放在该数组
    private int top =-1; //top表示栈顶，初始化为-1

    // 构造器
    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[this.maxSize];  //初始化数组,
                                    // 并赋值给数组类型的变量stack
    }


    // 栈满
    public boolean isFull(){
        return top==maxSize-1;
    }

    // 栈空
    public boolean isEmpty(){
        return top==-1;
    }

    //入栈 push
    public void stackPush(int value){

        if (top==maxSize-1) {
            System.out.println("栈满---不能再添加");
            return;
        }
        top++;
        stack[top]=value;
    }

    // 出栈 pop,将栈顶的数据返回
    public int stackPop(){
        if (top==-1){
            // 抛出异常
            throw new RuntimeException("栈空，没有数据---");
        }
        int temp=stack[top]; // 记录下要出栈的值，
        top--;                    // 如果直接return，就不能完成top--了
        return temp;
    }

    // 遍历栈 从栈顶开始到栈底
    public void showStack(){
        if (top==-1){
            System.out.println("栈空，没有数据---");
            return;
        }
        for (int i=top;i>=0;i--){
            System.out.printf("stack[%d]=%d\n",i,stack[i]);
        }
    }

}