package linkedList;

/**
 * @author Chu
 * @create 2021-04-14  15:11
 */
public class Josephu {
    public static void main(String[] args) {
        // 测试创建一个环形链表和它的遍历
        CircleSingLinkedList circleSingLinkedList = new CircleSingLinkedList();

        // 加入9个Person成员结点进入链表
        circleSingLinkedList.CircleAdd(9);

        // 遍历显示
        circleSingLinkedList.ShowCircleList();

        // 测试成员出圈
        circleSingLinkedList.countPerson(1,2,9);

    }

}

// 创建一个环形链表的类
class CircleSingLinkedList{
    // 创建一个first结点，当前没有编号  // 类似头结点，不过后面要赋值
    // private Person first =null;
    private Person first = new Person(-1);

    //添加结点的方法
    public void CircleAdd(int nums){
        // nums做一个数据校验
        if (nums<1){
            System.out.println("输入的nums的值不正确");
            return;
        }
        Person temp=null;  // 创建辅助变量 temp
        // 使用for来创建我们的环形链表
        for (int i=1;i<=nums;i++){
            // 根据编号，创建person结点
            Person person = new Person(i);
            // 如果是第一个人
            if (i==1){
                first=person;      // 先前创建的first替换到新结点，编号1
                first.next=first; // 形成一个元素的环形链表
                temp=first;  // 由于first不能动，让temp辅助变量也指向它来替代
            }else {
                temp.next=person;  // 第一个/添加上一个循环中的person结点指向这一循环中的person
                person.next=first; // 这轮循环中的person结点指向，第一个结点
                temp=person;  // temp后移到这轮循环person结点的位置
            }
        }
    }

    // 遍历环形链表
    public void ShowCircleList(){
        if (first.no==-1){  //没有链表
            System.out.println("没有任何人员");
            return;
        }
        // 因为first不能动，因此我们使用一个辅助指针temp（变量）来完成
        Person temp=first;
        while (true){
            System.out.printf("人员的编号为%d：\n",temp.no);
            if (temp.next==first){
                break;
            }
            temp=temp.next;
        }
    }


    // 根据用户的输入，计算出成员出圈的顺序
    /*
     * startNo 表示从第几个成员开始报数
     * countNum 表示数几下
     * nums 表示最初有多少个成员在圈中
     * */
    public void countPerson(int startNo,int countNum ,int nums){
        // 先进行数据校验
        if (first==null || startNo<1 || startNo>nums){
            System.out.println("参数输入有误，请重新输入...");
            return;
        }
        // 创建辅助指针，帮助出圈   因为直接移动first，helper的存在保证一直是个环形链表
        Person helper = first;
        // 需求创建一个辅助指针(变量)helper，事先应该指向环形链表最后的结点
        while (true){
            if (helper.next==first){
                break;
            }
            helper=helper.next;
        }
        // 成员报数前，先让first和helper移动k-1次
        for (int j=0;j<startNo-1;j++){
            first=first.next;
            helper=helper.next;
        }
        // 当成员报数时，让first和helper指针同时移动m-1次，然后出圈
        // 这里是一个循环操作，直到圈中只有一个结点
        while (true){
            if (helper==first){
                break;   //圈中只有一个结点
            }

            // 让first和helper指针同时移动到 countNum-1
            for (int j=0;j<countNum-1;j++){
                first=first.next;
                helper=helper.next;
            }

            // 这时first指向的结点，就是要出圈的成员结点
            System.out.printf("成员%d出圈\n",first.no);
            // 这时将first指向的成员结点出圈
            first=first.next;   // first要后移一位
            helper.next=first; //  helper没有移动，只是它的指向
        }
        System.out.printf("最后留在圈中的成员编号%d\n",first.no);

    }


}



// 创建一个Person类表示一个结点
class Person{
    public int no; // 编号
    public Person next; // 指向下一个结点，默认null

    public Person(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    @Override
    public String toString() {
        return "Person{" +
                "no=" + no +
                '}';
    }
}