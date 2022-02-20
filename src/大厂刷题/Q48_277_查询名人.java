package 大厂刷题;

/**
 * @author Chu
 * @create 2022-02-14  13:58
 */
/*
* 有0~n-1这么些人，
*       一个函数know(i,j)，返回i认不认识j  --- 认识返回true，不认识返回false
*     名人的特点：
*           ① 其他人都认识这个人
*           ② 名人不认识其他人
*     如果有名人返回序号(最多一个明星)，没有则返回-1
* */
public class Q48_277_查询名人 {

    // 提交时不要提交这个函数，只提交下面的方法
    public static boolean knows(int x, int i) {
        return true;
    }


    public int findCelebrity(int n) {
        // 谁可能成为明星，谁就是cand
        int cand = 0;
        // 前一个如果认识后一个，那其就不是名人(违反②)，
        for (int i = 0; i < n; ++i) {
            if (knows(cand, i)) {
                cand = i;           // 名人候选就选到下一位
            }
        }
        // 判断选取到的名人是否认识每个人 cand...(右侧cand都不认识，上个循环得出)，
        //      所以，只用验证 ...cand的左侧即可，如果有认识的人(违反②)就返回-1
        for (int i = 0; i < cand; ++i) {
            if (knows(cand, i)) {
                return -1;
            }
        }
        // 判断所有人是否认识选取到的名人，存在不认识的则返回-1
        for (int i = 0; i < n; ++i) {
            if (!knows(i, cand)) {
                return -1;
            }
        }
        return cand;
    }


}
