package stack;

/**
 * @author Chu
 * @create 2021-04-16  10:36
 */
public class Calculator {
    public static void main(String[] args) {
        String expression ="300+2*6-2*2";
        //创建两个栈，数栈和符号栈
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        // 定义需要的相关变量  字符串不能用for直接遍历，故采用index索引扫描
        int index=0; // 用于扫描
        int num1=0;
        int num2=0;
        int oper=0;
        int res=0;
        char ch=' '; //将每次扫描得到的char保存到ch
        String keepnum=""; // 用于拼接多位数
        // 开始while循环的扫描experssion
        while (true){
            // 一次得到experssion的每一个字符
            // substring方法获取返回一个索引间的字符串，charAt获取第一个
            ch =expression.substring(index,index+1).charAt(0);
            //判断ch是什么
            if (operStack.isOper(ch)){ // 如果是运算符
                // 判断当前的符号栈是否为空
                if (!operStack.isEmpty()){ //如果不为空
                    if (operStack.priority(ch)<=operStack.priority(operStack.showTop())){
                        num1=numStack.stackPop();
                        num2=numStack.stackPop();
                        oper=operStack.stackPop();
                        res=numStack.cal(num1,num2,oper);
                        // 把运算的结果入数栈
                        numStack.stackPush(res);
                        // 然后将当前的操作符入符号栈
                        operStack.stackPush(ch);
                    }else {
                        // 如果当前操作符的优先级大于栈中的操作符，直接写入
                        operStack.stackPush(ch);
                    }
                }else {
                    // 如果符号栈为空，则直接入符号栈
                    operStack.stackPush(ch);
                }
            }else {  //如果是数，直接入数栈
                // numStack.stackPush(ch)是错的，因为是字符串要ascll码
                // numStack.stackPush(ch-48);
                // 分析思路
                // 1、当处理多位数时，不能发现是一个数就立即出栈，可能是个多位数
                // 2、在处理数，需要向expression表达式的index再看一位，如果是数就进行扫描，如果符号才入栈
                // 3、因此需要定义一个变量字符串，用于拼接

                // 处理多位数
                keepnum+=ch;

                // 如果ch已经是expression最后一位，就直接入栈
                if (index==expression.length()-1){
                    numStack.stackPush(Integer.parseInt(keepnum));
                }else {
                    // 判断下一个字符是不是数字，如果是数字，就继续扫描，如果是运算符，则入栈
                    // 注意只是看后一位，不是index++
                    if (operStack.isOper(expression.substring(index+1,index+2).charAt(0))){
                        //如果后一位是运算符，则入栈keepNum=”1“或”123“
                        numStack.stackPush(Integer.parseInt(keepnum));
                        //重要的  要把keeepnum清空
                        keepnum="";
                    }
                }

            }
            // 让index+1,并判断是否扫描到expression最后
            index++;
            if (index>=expression.length()){
                break;
            }
        }
        // 当表达式扫描完毕，就顺序的从数栈和符号栈中弹出相应的数和符号，并允许
        while (true){
            // 如果符号栈为空，最计算得到最后结果
            if (operStack.isEmpty()){
                break;
            }
            num1=numStack.stackPop();
            num2=numStack.stackPop();
            oper=operStack.stackPop();
            res=numStack.cal(num1,num2,oper);
            // 把运算的结果入数栈
            numStack.stackPush(res);
        }
        // 将最后的数，弹出
        System.out.printf("表达式%s=%d",expression,numStack.stackPop());
    }
}



// 先创建一个栈
// 定义一个ArrayStack2表示栈
class ArrayStack2{
    private int maxSize; // 栈的大小
    private int[] stack; // 数组模拟栈，数据就放在该数组
    private int top =-1; //top表示栈顶，初始化为-1

    // 构造器
    public ArrayStack2(int maxSize) {
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

    // 观察栈顶的方法
    public int showTop(){
        if (top==-1) {
            System.out.println("栈空，没有数据---");
            return -1;
        }
        return stack[top];

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

    // 返回运算符的优先级，优先级使用数字表示，数字越大，优先级越高
    public int priority(int oper){
        if (oper=='*'||oper=='/'){
            return 1;
        }else if(oper=='+'||oper=='-'){
            return 0;
        }else {
            return -1;// 假定目前的表达式只有+、-、*、/
        }
    }

    // 判断是否是个运算符
    public  boolean isOper(char val){
        return val=='+'||val=='-'||val=='*'||val=='/';
    }

    // 计算方法
    public int cal(int num1,int num2 ,int oper){
        int res=0; //res 用于存放计算结果
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                res = num2 - num1; // 注意顺序
                break;
            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }
}