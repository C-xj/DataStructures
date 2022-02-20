package Class09_贪心算法;

import java.util.Arrays;
import java.util.Comparator;
/* 题目二：最小字典序
* 字符串拼接 str如果是K进制
* 	例如将"abc" "de" 拼接在一起
* 		"abc" * K^2 + "de"   即，adc向右移两位，拼接上de
* */
// 最小字典序
public class Code02_LowestLexicography {

	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return (a + b).compareTo(b + a);
		}
	}

	public static String lowestString(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		Arrays.sort(strs, new MyComparator());
		String res = "";
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}

	public static void main(String[] args) {
		String[] strs1 = { "jibw", "ji", "jp", "bw", "jibw" };
		System.out.println(lowestString(strs1));

		String[] strs2 = { "ba", "b" };
		System.out.println(lowestString(strs2));

	}

}
