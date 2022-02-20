package HashTable;

import java.util.Scanner;

/**
 * @author Chu
 * @create 2021-05-11  14:40
 */
public class HashTableDemo {
    public static void main(String[] args) {

        // 创建哈希表
        HashTable hashTable=new HashTable(7);

        //写一个简单菜单
        String key="";
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("add: 添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");

            key=scanner.next();
            switch (key){
                case "add":
                    System.out.println("输入id");
                    int id=scanner.nextInt();
                    System.out.println("输入名字");
                    String name=scanner.next();
                    // 创建 雇员
                    Emp emp = new Emp(id, name);
                    hashTable.add(emp);
                    break;
                case "list":
                    hashTable.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id雇员");
                    id=scanner.nextInt();
                    hashTable.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

}

// 创建HashTable 管理多条链表（散列函数）
class HashTable{
    private EmpLinkedList[] empLinkedListArray;
    private int size;  // 表示共有多少条链表

    //构造器
    public HashTable(int size){
        this.size=size;
        // 初始化empLinkedListArray
        empLinkedListArray=new EmpLinkedList[size];
        // 这时不要忘记分别初始化每一条链表，不然每个链表时null，空指针异常
        for (int i=0;i<size;i++){
            empLinkedListArray[i]=new EmpLinkedList();
        }
    }

    // 添加雇员
    public void add(Emp emp){
        //根据员工的id，得到该员工应当添加到哪条链表
        int empLinkedListNo=hashFun(emp.id);
        // 将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }

    // 遍历所有链表(遍历hashtable)
    public void list(){
        for (int i=0;i<size;i++){
            empLinkedListArray[i].list(i);
        }
    }

    //根据输入的id查找雇员
    public void findEmpById(int id){
        // 使用散列函数确定到哪条链表查找
        int empLinkedListNo=hashFun(id);

        Emp emp=empLinkedListArray[empLinkedListNo].findEmpById(id);
        if (emp!=null){
            System.out.printf("在第%d条链表中找到id=%d的雇员\n",(empLinkedListNo+1),id);
        }else {
            System.out.println("在哈希表中，没有找到该雇员");
        }
    }

    // 编写散列函数，使用一个简单取模法
    public int hashFun(int id){
        return id % size;
    }

}

//表示一个雇员
class Emp{
    public int id;
    public String name;
    public Emp next;  //next默认为null

    public Emp(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}

// 创建EmpLinkedList，表示链表
class EmpLinkedList{
    // 头指针，执行第一个Emp，因此这个链表的head，是直接指向第一个Emp
    private Emp head; // 默认null

    // 添加雇员到链表
    // 1、假定，当添加雇员时，id是自增长，即id的分配总是从小到大
    //    因此将雇员直接添加到链表的最后即可
    public void add(Emp emp){
        // 如果是添加第一个雇员
        if (head==null){
            head=emp;
            return;
        }
        // 如果不是第一个，则使用辅助指针，帮助定位到最后
        Emp temp=head;
        while (true){
            if (temp.next==null){  //说明到链表最后
                break;
            }
            temp=temp.next;  // 后移
        }
        // 退出时直接将emp加入链表
        temp.next=emp;
    }

    // 遍历链表的雇员信息
    public void list(int no){
        if (head==null){  // 说明链表为空
            System.out.println("第"+(no+1)+"链表为空");
            return;
        }
        System.out.println("第"+(no+1)+"链表的信息为");
        Emp temp=head;  // 辅助指针
        while (true){
            System.out.printf("=> id=%d name=%s\t",temp.id,temp.name);
            if (temp.next==null){ //说明temp已经到最后结点
                break;
            }
            temp=temp.next;  // 后移，遍历
        }
        System.out.println();
    }

    // 根据id查找雇员
    // 如果查找到，就返回Emp，没有找到返回null
    public Emp findEmpById(int id){
        // 判断链表是否为空
        if (head==null){
            System.out.println("链表为空");
            return null;
        }
        // 辅助指针
        Emp temp=head;
        while (true){
            if (temp.id==id){ // 找到对应雇员
                break;  //这时temp指向查找的雇员
            }
            // 退出
            if (temp.next==null){  // 说明遍历当前链表没有找到该雇员
                temp=null;
                break;
            }
            temp=temp.next;  //后移
        }
        return temp;
    }

}