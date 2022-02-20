package Class09_贪心算法;


import java.util.HashSet;

/*题目三：墙和居民点问题
*	给定一个字符串st,只由X和：两种字符构成。
	X表示墙，不能放灯，也不需要点亮
	表示居民点，可以放灯，需要点亮
	如果灯放在位置，可以让i-1,i和i+1三个位置被点亮
	返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
* */
public class Code03_Light {


	// 实现方式一：暴力解法
	public static int minLight1(String road) {
		if (road == null || road.length() == 0) {
			return 0;
		}
		return process(road.toCharArray(), 0, new HashSet<>());
	}


	// str[index...]位置，自由选择放灯还是不放灯
	// str[0 .. index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
	// 要求选出能照亮所有.的方案，并且在这些方案中，返回最少需要几个灯
	public static int process(char[] str, int index, HashSet<Integer> lights) {
		if (index == str.length) {
			for (int i = 0;i < str.length;i++){
				if (str[i] != 'X'){	// 当前位置是点
					if (!lights.contains(i-1)
							&& !lights.contains(i)
							&& !lights.contains(i+1)){
						return Integer.MAX_VALUE;	// 连续的三个点都没有灯，用正数最大值表示无效
					}
				}
			}
			return lights.size();
		}else {	// str还没有结束
			// 当前点不放灯的情况
			int no = process(str,index + 1,lights);

			int yes = Integer.MAX_VALUE;
			if (str[index] == '.'){
				// 放灯的情况，再去算下一个点
				lights.add(index);
				yes = process(str,index + 1 ,lights);
				// 上面计算得到结果后，要去除，恢复现场；
				// 类似于树，当左结点是不放灯，其后面子树有地方放灯了，如果不去除，会对当前结点右边放灯的造成数据脏读情况。
				lights.remove(index);
			}
			return Math.min(no,yes);
		}
	}

	// 实现方式二：贪心算法
	public static int minLight2(String road) {
		char[] str = road.toCharArray();
		int index = 0;
		int light = 0;
		while (index < str.length) {
			if (str[index] == 'X') {
				index++;
			} else { // str[index] == '.'
				light++;	// 增加一个灯
				if (index + 1 == str.length) {	// 灯就放在i
					break;
				} else {
					if (str[index + 1] == 'X') { // 灯就放在i，来到i+2的位置
						index = index + 2;
					} else {    // 将灯放在i+1的位置，跳到i+3继续判断
						index = index + 3;
					}
				}
			}
		}
		return light;
	}

	public static String randomString(int len) {
		char[] res = new char[(int) (Math.random() * len) + 1];
		for (int i = 0; i < res.length; i++) {
			res[i] = Math.random() < 0.5 ? 'X' : '.';
		}
		return String.valueOf(res);
	}

	public static void main(String[] args) {
		int len = 20;
		int testTime = 300000;
		for (int i = 0; i < testTime; i++) {
			String test = randomString(len);
			int ans1 = minLight1(test);
			int ans2 = minLight2(test);
			if (ans1 != ans2) {
				System.out.println("oops!");
				System.out.println(test);
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
	}
}
