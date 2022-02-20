package linkedList;

import com.sun.source.tree.NewArrayTree;

import java.awt.event.WindowListener;
import java.util.Stack;

/**
 * @author Chu
 * @create 2021-04-13  14:01
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 进行测试
        // 先创建结点
        HeroNode sj = new HeroNode(1, "宋江", "及时雨");
        HeroNode ljy = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode wy = new HeroNode(3, "吴用", "智多星");
        HeroNode lc = new HeroNode(4, "林冲", "豹子头");

        // 创建单向链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        // 第一种方式添加 （只是根据添加顺序排列）
        //singleLinkedList.add1(sj);
        //singleLinkedList.add1(lc);
        //singleLinkedList.add1(wy);
        //singleLinkedList.add1(ljy);

        // 第二种方式添加  （根据no来排列）
        singleLinkedList.add2(sj);
        singleLinkedList.add2(lc);
        singleLinkedList.add2(wy);
        singleLinkedList.add2(ljy);


        // 显示
        singleLinkedList.showlist();

        // 测试修改节点的代码
        HeroNode newheroNode = new HeroNode(2, "小卢", "玉麒麟~~");
        singleLinkedList.updata(newheroNode);


        // 修改后显示
        System.out.println("修改后的情况...");
        singleLinkedList.showlist();

        // 删除一个结点
        singleLinkedList.del(1);
        singleLinkedList.del(3);
        System.out.println("删除后的情况...");
        singleLinkedList.showlist();

        // 测试一下统计结点个数
        System.out.println("有效的结点个数为：" + singleLinkedList.getLength());

        // 测试一下查找倒数第k个结点

        System.out.println("输出的倒数第k个结点是："+ singleLinkedList.findneedIndex(2));

        // 测试单链表的反转功能
        System.out.println("原来的单链表");
        singleLinkedList.showlist();
/*

        System.out.println("反转后的单链表");
        singleLinkedList.reverseHead();
        singleLinkedList.showlist();
*/

        System.out.println("测试逆序打印单链表,压入栈，没有改变链表的原有结构");
        singleLinkedList.reversePrint();



    }
}

// 定义SingleLinkedList管理我们的英雄   ---创建单链表
class SingleLinkedList{
    // 先初始化一个头结点，头结点不要动，不存放具体的数据
    public  static HeroNode head= new HeroNode(0,"","");

    // 返回头结点
    public HeroNode getHead() {
        return head;
    }


    // 添加节点到单向链表
    // 思路，当不考虑编号顺序时
    // 1、找到当前链表的最后节点  2、将最后这个结点的next指向新的结点
    public void add1(HeroNode heroNode){
        // 因为head结点不能动，因此需要一个辅助遍历temp
        HeroNode  temp =head;
        // 遍历链表，找到最后结点
        while (true){
            // 找到链表的最后
            if (temp.next==null){
                break;
            }
            // 如果没有找到最后，将temp后移
            temp=temp.next;
        }
        // 当推出while循环时，temp就只想了链表的最后
        temp.next=heroNode;
    }


    // 第二种方式在添加结点时，根据顺序排名将结点插入到指定位置
    // 如果有这个排名，则添加失败，并给提示
    public void add2(HeroNode heroNode){
        // 因为head结点不能动，因此需要一个辅助temp 遍历到要添加的位置
        // 因为单链表，找的temp位于添加位置的前一个结点，否则插入不了（指定位置后插）
        HeroNode temp =head;
        boolean flag=false;// 表示添加的结点的编号是否存在，默认false
        // 遍历到指定位置
        while (true){
            if (temp.next==null){ //到链表最后
                break;
            }
            if (temp.next.no>heroNode.no){  // 位置找到，就在temp的后面
                break;
            }else if (temp.next.no==heroNode.no){ // 希望添加的结点已经存在
                flag=true;
                break;

            }
            temp=temp.next;
        }
        if (flag){    // flag为true时不可以添加
            System.out.printf("结点的编号%d已存在不可以再添加 ",heroNode.no);
        }else {
            // 插入到链表中 temp后（指定位置后面）
            heroNode.next = temp.next;
            temp.next = heroNode;
        }


    }

    // 修改节点的信息，根据no编号来修改，即no编号不能改
    // 说明：根据newHeroNode 的no来修改
    public void updata(HeroNode newHeroNode){
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }
        HeroNode temp=head.next;
        boolean flag=false;
        while (true){
            if(temp==null) {
                break;  // 到链表的最后 已经遍历完链表了
            }
            if(temp.no==newHeroNode.no){
                flag=true;  // 找到了
                break;
            }
            temp=temp.next;
        }
        // 根据flag判断是否找到要修改的结点
        if (flag){
            temp.name= newHeroNode.name;
            temp.nickname=newHeroNode.nickname;
        }else {
            System.out.printf("没有找到编号%d的结点 ",newHeroNode.no);
        }
   }

    //根据no值来判断删除
   public void del(int no){
       HeroNode temp=head;
       boolean flag=false;  //  标识是否找到
       while (true){
           if (temp.next==null){ //到最后
               break;
           }
           if (temp.next.no==no) {
               // 找到待删除结点的前一个结点temp
               flag = true;
               break;

           }
           temp=temp.next; // temp后移，继续遍历
       }
       if (flag){
           temp.next=temp.next.next;
       }else {
           System.out.printf("要删除的编号%d的结点不存在\n",no);
       }
   }


    // 方法：获取到单链表有效结点的个数（有头结点的要去掉）
    //  head 链表的头结点  return是返回的有效结点的个数
    public  int getLength(){
        if(head.next==null){
            return 0;
        }
        int length=0;
        HeroNode temp=head;
        while (true){
            if (temp.next==null){
                break;
            }
            temp=temp.next;
            length++;
        }
        return length;
    }

    // 查找单链表中倒数第k个节点
    // 思路：
    // 1、编写一个方法，接受head结点，同时接受一个index
    // 2、index表示是倒数第index个结点
    // 3、先把链表遍历一遍，得到链表长度m，然后再遍历 （m-index) 它对应的next就是要查找的倒数k个
    public  HeroNode  findneedIndex(int index){
        if(head.next==null){
            return null;
        }
        int m=getLength();   // 获取链表长度
        HeroNode temp=head;
        if(index<=0 || index>m ){
            return null;
        }
        for (int i=1;i<=(m-index);i++){
            temp=temp.next;
        }
        return temp.next;
    }

    public void reverseHead(){
        if (head.next==null || head.next.next==null){
            return;
        }
        HeroNode temp=head.next;
        HeroNode next =null; // 指向当前结点【cur】的下一个结点
        HeroNode reversehead= new HeroNode(0,"","");
        // 从头到尾遍历原来的链表，没遍历一个结点，就将其取出，
        // 并放在新的链表reverseHead的最前端。
        while (temp!=null) {
            next = temp.next;// 保存temp后的那个结点，后面移位要使用
            temp.next = reversehead.next; // 取下的temp指向新链表头结点指向的结点（null）。
            reversehead.next = temp; // 将新链表的头结点指向取下的temp结点
            temp = next;  // 把temp移到保存再next中它后面的那个结点
        }
        // 将head.next指向reversehead.next，实现头结点的替换
        head.next=reversehead.next;
    }

    // 使用栈的方式（先进后出）实现逆序打印
    public static void reversePrint(){
        if(head.next==null){
            return; // 空链表，不能打印
        }
        // 创建一个栈，将各个结点压入栈中
        Stack<HeroNode> stack = new Stack<HeroNode>();
        HeroNode temp=head.next;
        // 将链表的所有结点压入栈
        while (temp!=null){
            stack.push(temp);
            temp=temp.next;  // temp后移，这样就可以压入下一个结点
        }
        //将栈中的结点pop出栈打印
        while (stack.size()>0){
            System.out.println(stack.pop());// pop就是将栈顶的数据取出（先进后出）
        }

    }



    // 显示链表【遍历】
    public void showlist(){
        // 判断链表是否为空
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }
        // 因为头结点，不能动，因此要一个辅助遍历temp来遍历
        HeroNode temp=head.next;
        while (true){
            // 判断是否到链表最后
            if (temp==null){
                break;
            }
            //输出结点信息
            System.out.println(temp);
            // 将temp后移,一定要小心
            temp=temp.next;
        }
    }


}


// 定义HeroNode，每个HeroNode的实例对象就是一个结点
class HeroNode{
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;  // 指向下一个结点

    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    // 为了显示方便，重写toString方法
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\''  +
                '}';
    }
}

