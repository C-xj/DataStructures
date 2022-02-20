package stack;



/**
 * @author Chu
 * @create 2021-04-15  18:17
 */
public class ListStackDemo {
    public static void main(String[] args) {
        // 创建结点
        Person a = new Person(1);
        Person b = new Person(2);
        Person c = new Person(3);
        Person d = new Person(4);

        // 创建栈
        ListStack listStack = new ListStack();

        //添加数据 进栈
        listStack.StackAdd(a);
        listStack.StackAdd(b);
        listStack.StackAdd(c);
        listStack.StackAdd(d);

        System.out.println("进栈后的栈是---");
        //显示
        listStack.StackShow();

        // 删除数据 出栈
        Person deldata1=listStack.StackDel();
        System.out.println("出栈的元素是"+ deldata1);

        System.out.println("现在的栈是---");
        listStack.StackShow();

    }
}

class ListStack{
    // 先创建一个头结点
    Person head =new Person(0);

    public void StackAdd(Person person){  // 添加操作 进栈

        person.next = head.next;
        head.next = person;

        }

    public Person StackDel(){   // 删除操作  出栈
        if (head.next==null){
            System.out.println("栈为空");
            return null;
        }else if (head.next.next==null){
            return head.next;
        }else {
            Person temp=head.next;  // 变量向保存要出栈的数据，以便return
            head.next=head.next.next;
            return temp;
        }
    }

    public void StackShow(){   // 显示操作 由于是头插，直接头部遍历
        Person temp=head;  // 遍历不能破坏链表 要移动辅助遍历temp
        if (temp.next==null){
            return;
        }
        while (true){
            if (temp.next==null){
                break;
            }
            System.out.println(temp.next);
            temp=temp.next;
        }
    }
}




// 创建一个Person结点类
class Person{
    public int num;
    public Person next;

    @Override
    public String toString() {
        return "Person{" +
                "num=" + num +
                '}';
    }

    public Person(int num) {
        this.num = num;
    }
}