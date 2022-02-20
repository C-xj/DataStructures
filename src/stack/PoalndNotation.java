package stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Chu
 * @create 2021-04-16  15:06
 */
public class PoalndNotation {
    public static void main(String[] args) {

        // 完成将一个中缀表达式专场后缀表达式的功能
        //1、要使  1+((2+3)*4)-5  => 转成 1 2 3 + 4 * + 5 -
        // 2、 因为直接对str进行操作。不方便，因此先将” 1+((2+3)*4)-5 “
        //     转为中缀表达式对应的List 即 ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
        // 3、  将得到的中缀表达式对应的List  =>转为 后缀表达式对应的List
        //      即 ArrayList [1,2,3,+,4,*,+,5,-]

        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpression(expression);
        System.out.println("中缀表达式对应的List"+infixExpressionList); // [1,+,(,(,2,+,3,),*,4,),-,5]
        List<String>  parseSuffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式对应的List"+parseSuffixExpressionList); // [1,2,3,+,4,*,+,5,-]





        //思路
        // 1、先将逆波兰表达式放入到ArrayList集合中
        // 2、将ArrayList传递给一个方法，遍历ArrayList配合栈完成计算
        //List<ArrayStack3> rpnList = getListString(suffixExpression);
        int res=calculate(parseSuffixExpressionList);
        System.out.println("计算的结果是："+res);




    }


    // 中缀表达式转后缀表达式（逆波兰） 放入List中好遍历
    // 将中缀表达式转为对应的中缀List
    public static List<String> toInfixExpression(String arr) {
        ArrayList<String> ls = new ArrayList<>(); // 定义一个List，存放中缀对应的内容
        int index = 0;// 索引取遍历中缀表达式arr
        char ch; //将每次扫描得到的char保存到ch
        String str = ""; // 用于拼接多位数

        do {
            // 如果ch是一个非数字，需要加入到ls中
            // String.charAt（int index）方法获取当前索引位置对应的字符
            // 字符与数字进行比较是拿ascii码进行的
            // 字符与数字计算  例如 ch+=2;或ch++； 得到的ch还是字符
            //              而system.out.println(ch+2) 输出的是ascii码
            if ((ch = arr.charAt(index)) < 48 || (ch = arr.charAt(index)) > 57) {
                ls.add("" + ch);
                index++; //需要index后移
            } else { //如果是一个数，需要考虑多位数
                str = ""; // 先将str置成”“
                while (index < arr.length() && (ch = arr.charAt(index)) >= 48 && (ch = arr.charAt(index)) <= 57) {
                    str += ch; // 拼接
                    index++;
                }
                ls.add(str);
            }
        } while (index < arr.length());
        return ls; //返回
    }



    // 方法：将得到的中缀表达式List转为后缀表达式List
    public static  List<String> parseSuffixExpressionList(List<String> ls){
        // 定义两者栈
        Stack<String> s1 = new Stack<>();  // 符号栈
        // Stack<String> s2 = new Stack<>();  // 中间值栈
        // 说明 因为s2这个栈，在整个过程中，没有pop过程，而且后面还需要逆序输出，比较麻烦。
        // 这里就不用Stack<String> s2   直接用List<String> s2
        ArrayList<String> s2 = new ArrayList<>();


        // 遍历ls
        for(String item:ls){
            //如果是一个数 加入s2
            if (item.matches("\\d+")){
                s2.add(item);
            }else if (item.equals("(")){
                s1.push(item);
            }else if(item.equals(")")){
                // 如果是右括号”）“，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时一对括号被丢弃
                while (!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                s1.pop(); // 将”（“ 弹出s1栈，消除小括号
            }else {
                // 当item优先级小于等于s1栈顶运算符优先级，将s1栈顶运算符弹出并压入s2，
                // 再次转（4.1）与s1新栈顶比较  （即 弹出所有大于等于的）
                // 缺少个比较优先级的类
                while (s1.size()!=0 && Operation.getValue(s1.peek())>=Operation.getValue(item)){
                    s2.add(s1.pop());
                }
                //还需要将item压入栈
                s1.push(item);
            }
        }
        // 将s1剩余的运算符弹出压入s2
        while (s1.size()!=0){
            s2.add(s1.pop());
        }

        return s2; // 注意因为存的List，因此按顺序输出就是对应的后缀表达式List
    }




    // 将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
    public static List<String> getListString(String suffixExpression){
        //将suffixExpression分割
        String[] split =suffixExpression.split(" ");
        ArrayList<String> list = new ArrayList<>();
        for(String ele:split){
            list.add(ele);
        }
        return list;
    }


    // 完成逆波兰的计算
    public static int calculate(List<String> ls){  //ls传入的集合
        // 创建一个栈
        Stack<String> stack = new Stack<String>();

        //遍历ls
        for(String item:ls){
            //使用正则表达式来取数
            if (item.matches("\\d+")){ // 匹配多位数
                stack.push(item);
            }else {
                //pop两个数计算
                int num2=Integer.parseInt(stack.pop());
                int num1=Integer.parseInt(stack.pop());
                int res=0;
                if (item.equals("+")){
                    res=num1+num2;
                }else if (item.equals("-")){
                    res=num1-num2;

                }else if (item.equals("*")){
                    res=num1*num2;

                }else if (item.equals("/")){
                    res=num1/num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                //把res入栈
                stack.push(""+res);
            }
        }
        //最后留在stack中的数据是运算结果
        return Integer.parseInt(stack.pop());

    }

}

// 编写一个类Operation可以返回一个运算符，对应的优先级
class  Operation{
    private static int ADD=1;
    private static int SUB=1;
    private static int MUL=1;
    private static int DIV=1;

    // 写一个方法，返回对应的优先级数字
    public static int getValue(String operation){
        int result=0;
        switch (operation){
            case "+":
                result=ADD;
                break;
            case "-":
                result=SUB;
                break;
            case "*":
                result=MUL;
                break;
            case "/":
                result=DIV;
                break;
            default:
                break;
        }
        return result;
    }
}


class ArrayStack3{
    private int maxSize; // 栈的大小
    private int[] stack; // 数组模拟栈，数据就放在该数组
    private int top =-1; //top表示栈顶，初始化为-1

    // 构造器
    public ArrayStack3(int maxSize) {
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
            //System.out.println("栈空，没有数据---");
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