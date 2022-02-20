package queue;

import java.util.Scanner;

/**
 * @author Chu
 * @create 2021-04-12  19:30
 */
public class CircleArrayQueue {
    public static void main(String[] args) {
        // 测试一把
        System.out.println("测试数组模拟环形队列的案例");
        // 创建一个环形队列
        CircleArray circleArray = new CircleArray(4); // 说明设置4，其队列的有效数据最大是3
        char key=' '; // 接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop=true;
        while (loop){
            System.out.println("s(show):显示队列");
            System.out.println("e(exit):退出程序");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列取出数据");
            System.out.println("h(head):查看队列头的数据");
            key=scanner.next().charAt(0); // 接受一个字符
            switch (key) {
                case 's':
                    circleArray.showQueue();
                    break;
                case 'a':
                    System.out.println("请添加一个数据");
                    int i = scanner.nextInt();
                    circleArray.addQueue(i);
                    break;
                case 'g':
                    try {
                        int queue = circleArray.getQueue();
                        System.out.printf("取出的数据是%d\n", queue);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int i1 = circleArray.headQueue();
                        System.out.printf("头部的数据是%d\n", i1);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;


            }
        }
        System.out.println("程序退出");

    }
}


// 使用数组模拟队列-编写一个ArrayQueue类
class CircleArray{
    private int maxSize; // 表示数组的最大容量
    private int front;   // front就指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素。默认值为0
    private int rear;    // rear指向队列的最后一个元素的后一个位置，因为希望数组空出一个空间做为约定。默认值为0
    private int[] arr;   // 该数组用于存放数据，模拟队列

    // 创建队列的构造器
    public CircleArray(int arrMaxSize){
        maxSize=arrMaxSize;
        arr=new int[maxSize];
        front =0; //
        rear =0;  //
    }
    // 判断队列是否满
    public boolean isFull(){   // 当front=0，存入arr[2]后（队列第三个元素），后rear后移+1，为3，这时判断时队列已经满了。
        return (rear+1) % maxSize==front;
    }
    // 判断队列是否为空
    public boolean isEmpty(){
        return rear==front;
    }

    // 添加数据到队列
    public void addQueue(int n){
        // 判断队列是否满
        if(isFull()){
            System.out.println("队列满，添加失败");
            return;
        }
        // 直接将数据加入 rear初始为0 刚好数组第一个元素
        arr[rear]=n;
        // 将rear后移，这里必须考虑取模
        rear=(rear+1)%maxSize; // 到最后一位时maxSize-1后再加1取模，指针回到0
    }

    // 获取队列的数据，出队列
    public int getQueue(){
        // 判断队列是否空
        if(isEmpty()){
            // 通过抛出异常
            throw new RuntimeException("队列空，取数据失败");
        }
        //这里需要分析出front是指像队列的第一个元素
        // 1、先把front对应的只保留到一个临时变量  ，不然return后不能后移了
        // 2、将front后移,考虑取模
        // 3、将临时变量返回
        int value=arr[front];
        front=(front+1)%maxSize;
        return value;
    }

    //显示队列的数据
    public void showQueue(){
        if(isEmpty()) {
            System.out.println("队列为空，没有数据");
            return;
        }
        // 思路：从front开始遍历，多少个元素((rear-front+maxSize)%maxSize)
        for(int i=front;i<front+size();i++){
            System.out.printf("arr[%d]=%d\n",i % maxSize,arr[i % maxSize]);
        }
    }

    // 求出当前队列有效数据的个数
    public int size(){
        return ((rear-front+maxSize)% maxSize);
    }

    // 显示队列的头数据，注意不是取出数据
    public int headQueue(){
        // 判断队列是否空
        if(isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列空，没有数据");
        }
        return arr[front];
    }
}