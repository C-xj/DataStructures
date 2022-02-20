package 大厂刷题;

import java.util.Stack;

/**
 * @author Chu
 * @create 2022-02-13  9:46
 */
/*
* 准备一个栈，遇到'(' '[' '{' 将对应的右括号压入栈，否则弹出栈中一个与此时的符号进行对比。
* */
public class Q36_20_有效括号 {

    public static boolean isValid(String s){
        if (s == null || s.length() == 0){
            return true;
        }
        char[] str = s.toCharArray();
        Stack<Character> stack = new Stack<>();
        for (int i = 0;i < str.length;i++){
            char cha = str[i];
            if (cha == '(' || cha == '[' || cha == '{'){
                stack.add(cha == '(' ? ')' : (cha == '[' ? ']' : '}'));
            }else {
                if (stack.isEmpty()){
                    return false;
                }
                char last = stack.pop();
                if (cha != last){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }


    // 优化：使用数组模拟栈，size表示当前栈的大小
    public static boolean isValid2(String s){
        if (s == null || s.length() == 0){
            return true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        char[] stack = new char[N];
        int size = 0;

        for (int i = 0;i < N;i++){
            char cha = str[i];
            if (cha == '(' || cha == '[' || cha == '{'){
                stack[size++] = cha == '(' ? ')' : (cha == '[' ? ']' : '}');
            }else {
                if (size == 0){
                    return false;
                }
                char last = stack[--size];
                if (cha != last){
                    return false;
                }
            }
        }
        return size == 0;
    }

}
