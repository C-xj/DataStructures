package linkedList;

import java.util.ArrayList;

/**
 * @author Chu
 * @create 2021-04-14  10:48
 */
public class DoubleLinkedList {
    public static void main(String[] args) {

        // 进行测试
        // 先创建结点
        HeroNode2 sj = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 ljy = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 wy = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 lc = new HeroNode2(4, "林冲", "豹子头");



        // 创建双向链表
        DoubleLinkedListt doubleLinkedList = new DoubleLinkedListt();

        // 结点编号方式添加  （根据no来排列添加）
        doubleLinkedList.doubleAdd(sj);
        doubleLinkedList.doubleAdd(lc);
        doubleLinkedList.doubleAdd(wy);
        doubleLinkedList.doubleAdd(ljy);

        // 显示
        doubleLinkedList.showlist();

        // 修改一个结点
        HeroNode2 gss = new HeroNode2(2, "公孙胜", "入云龙");
        doubleLinkedList.updata(gss);

        System.out.println("修改后的双向链表");
        //修改后显示
        doubleLinkedList.showlist();

        //删除操作
        doubleLinkedList.del(3);

        System.out.println("删除后的双向链表");
        //删除后显示
        doubleLinkedList.showlist();
    }
}

// 创建一个双向链表的类
class DoubleLinkedListt {
    // 先初始化一个头结点，头结点不要动，不存放具体数据
    private HeroNode2 head = new HeroNode2(0, "", "");

    //   返回头结点
    public HeroNode2 getHead() {
        return head;

    }

    // 显示链表【遍历】
    public void showlist() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头结点，不能动，因此要一个辅助遍历temp来遍历
        HeroNode2 temp = head.next;
        while (true) {
            // 判断是否到链表最后
            if (temp == null) {
                break;
            }
            //输出结点信息
            System.out.println(temp);
            // 将temp后移,一定要小心
            temp = temp.next;

        }
    }

    // 添加操作  按结点编号顺序插入链表中
    public void doubleAdd(HeroNode2 heroNode2){
        boolean flag=false;
        HeroNode2 temp =head;
        while (true){
            if (temp.next==null){
                break;
            }
            if (temp.next.no>heroNode2.no){
                break;
            }
            if (temp.next.no==heroNode2.no){
                flag=true;
                break;
            }
            temp=temp.next;
        }
        if (flag){
            System.out.printf("需要添加的结点编号%d：已存在",heroNode2.no);
        }else {
            if (temp.next!=null){
                temp.next.pre=heroNode2;
                heroNode2.next=temp.next;
                heroNode2.pre=temp;
                temp.next=heroNode2;
            }else {
                temp.next=heroNode2;
                heroNode2.pre=temp;
            }
        }
    }

    // 修改一个结点的内容  与点链表一样
    // 修改节点的信息，根据no编号来修改，即no编号不能改
    // 说明：根据newHeroNode 的no来修改
    public void updata(HeroNode2 newHeroNode2){
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp=head.next;
        boolean flag=false;
        while (true){
            if(temp==null) {
                break;  // 到链表的最后 已经遍历完链表了
            }
            if(temp.no==newHeroNode2.no){
                flag=true;  // 找到了
                break;
            }
            temp=temp.next;
        }
        // 根据flag判断是否找到要修改的结点
        if (flag){
            temp.name= newHeroNode2.name;
            temp.nickname=newHeroNode2.nickname;
        }else {
            System.out.printf("没有找到编号%d的结点 ",newHeroNode2.no);
        }
    }

    //根据no值来判断删除  与单链表不同，双链表可以再定位到要删除结点的地方实现自我删除
    public void del(int no){
        if (head.next==null){
            System.out.println("链表为空，无法删除");
            return;
        }

        HeroNode2 temp=head.next;
        boolean flag=false;  //  标识是否找到
        while (true){
            if (temp==null){ //到最后
                break;
            }
            if (temp.no==no) {
                // 找到待删除结点的temp
                flag = true;
                break;
            }
            temp=temp.next; // temp后移，继续遍历
        }
        if (flag){
            temp.pre.next=temp.next;
            // 如果是最后一个结点，就不要执行下面这句话，否则出现空指针异常
            if (temp.next!=null){
                temp.next.pre=temp.pre;
            }

        }else {
            System.out.printf("要删除的编号%d的结点不存在\n",no);
        }
    }
}


// 定义HeroNode2，每个HeroNode2的实例对象就是一个结点
class HeroNode2{
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;  // 指向下一个结点
    public HeroNode2 pre;   // 指向上一个结点

    public HeroNode2(int no, String name, String nickname) {
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