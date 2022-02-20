package Class07_二叉树的递归套路;

/*
* 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，
* 压出折痕后展开。此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
* 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，
* 从上到下依次是下折痕、下折痕和上折痕。
*
* 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。请从上到下打印所有折痕的方向。
*
* 例如：N=1时，打印：down N=2时，打印：down down up
*
* 	第一次折：最中间为1凹
* 	第二次折：上有一个2凹，下有一个2凸
* 	第三次折：2凹上有一个凹，下有一个凸，2凸上有一个凹，下有一个凸
*
* 	从中间向两边看成树的形式，要从上到下打印折痕就是---此树的中序遍历
* */
public class Code02_PaperFolding {

	public static void printAllFolds(int N) {

		printProcess(1, N, true);
	}

	// 递归过程，来到了某一个结点
	// i是结点的层数，N一共的层数，down == true 凹 ； down == false 凸
	public static void printProcess(int i, int N, boolean down) {
		if (i > N) {
			return;
		}
		printProcess(i + 1, N, true);
		System.out.println(down ? "凹 " : "凸 ");
		printProcess(i + 1, N, false);
	}

	public static void main(String[] args) {
		int N = 1;
		printAllFolds(N);
	}
}
