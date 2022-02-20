package Class13_暴力递归到动态规划;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;

/* 题目二：
 *   给定一个字符串str，给定一个字符串类型的数组arr
 * 		arr里的每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来，返回需要至少多少张贴纸可以完成这个任务。
 *
 * 	例子：str = "babac", arr ={"ba","c","abcd"}
 * 		至少需要两张贴纸"ba"和"abcd"，因为使用这两张贴纸，
 * 		把一个字符单独剪开，含有2个a、2个b、1个c。是可以频出str的，所以返回2。
 * */

public class Code07_StickersToSpellWord {


// --------------------------流程1：暴力递归(简单描述)------------------------

	public static int A(String str,String[] arr){
		// 过滤条件...


		return minS(str,arr);
	}

	public static int minS(String rest,String[] arr){
		if (rest.equals("")){	// 如果已经是空字符串，需要0张贴纸。
			return 0;
		}

		// 搞定rest的，第一张贴纸是什么
		int ans = Integer.MAX_VALUE; // 表示搞定rest，使用的最少贴纸数量
		// 遍历每个贴纸，递归使用贴纸数
		for (String first : arr){

			String nextRest;
			// rest - first -> nextRest 	一个函数，返回使用当前遍历到的贴纸后剩余的目标字符数量，然后赋给nextRest
			// int cur = minS(nextRest,arr); 递归计算后面需要的贴纸数量
			// ans = Math.min(next,cur);	赋值消耗贴纸最少的数量
		}
		return ans+1;  // 返回 使用当前贴纸下(+1)条件下+后面字符需要的贴纸数量的和

	}

// --------------------------流程2：记忆化搜索，加缓存（枚举不同的贴纸）------------------------

	public static int minStickers1(String[] stickers, String target) {
		int n = stickers.length;

		int[][] map = new int[n][26];	// 把所有贴纸stickers分别转换为一个长为26的数组
		for (int i = 0; i < n; i++) {	// 统计每个贴纸中，每个字符出现的次数
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}

		HashMap<String, Integer> dp = new HashMap<>();	// 傻缓存
		dp.put("", 0);
		return process1(dp, map, target);
	}

	// dp 傻缓存，如果rest已经算过了，直接返回dp中的值
	// rest 剩余的目标
	// 0..N每一个字符串所含字符的词频统计
	// 返回-1，map中的贴纸是怎么都无法把rest搞定
	public static int process1(HashMap<String, Integer> dp, int[][] map, String rest) {
		if (dp.containsKey(rest)) {		// 如果之前遇到过，之前从傻缓存中获取
			return dp.get(rest);
		}

		// 以下是正式的递归调用过程
		int ans = Integer.MAX_VALUE;	// 搞定rest，使用的最少贴纸数量
		int n = map.length;				// n种贴纸

		int[] tmap = new int[26];		// // 把剩余目标rest转换为一个长为26的数组
		char[] target = rest.toCharArray();
		for (char c : target) {
			tmap[c - 'a']++;
		}


		for (int i = 0; i < n; i++) { // 先用第一张贴纸，看下还剩下什么

			// 此情况防止栈溢出，因为例如当剩余"aaaabbbbccc"时，贴纸为"xyz"时，剩余不变，会陷入死循环
			if (map[i][target[0] - 'a'] == 0) {
				continue;
			}

			StringBuilder sb = new StringBuilder(); // 最后存储的是i号贴纸用后，剩余的字符
			for (int j = 0; j < 26; j++) {
				if (tmap[j] > 0) { // tmap[j]这个字符是target需要的---还有需求
					for (int k = 0; k < Math.max(0, tmap[j] - map[i][j]); k++) { // target 如果需要3个a，map 最多能提供几个a，那么sb就拼接好几个a，留给下一个贴纸去搞定
						sb.append((char) ('a' + j));	// Math.max(0, tmap[j] - map[i][j]表示还剩多少个对应的字符没有被贴，就把剩余要贴的字符('a' + j)和数量(加入的次数)加入到sb中
					}
				}
			}
			// 转为字符串，进入递归
			String s = sb.toString();

			int tmp = process1(dp, map, s);
			if (tmp != -1) {		// 剩余的有可以搞定的方案
				ans = Math.min(ans, 1 + tmp);	// 取之前的ans 与 使用当前贴纸下(+1)条件下+后面字符需要的贴纸数量和 的最小值
			}
		}
		// 如果可以完成rest的拼接则存入缓存中，否则此条件下不能完成，存入-1
		dp.put(rest, ans == Integer.MAX_VALUE ? -1 : ans);
		return dp.get(rest);
	}


// --------------------------流程2：记忆化搜索，加缓存(枚举贴纸使用的数量)------------------------

	public static int minStickers2(String[] stickers, String target) {
		int n = stickers.length;
		int[][] map = new int[n][26];
		for (int i = 0; i < n; i++) {
			char[] str = stickers[i].toCharArray();
			for (char c : str) {
				map[i][c - 'a']++;
			}
		}
		char[] str = target.toCharArray();
		int[] tmap = new int[26];
		for (char c : str) {
			tmap[c - 'a']++;
		}
		HashMap<String, Integer> dp = new HashMap<>();
		int ans = process2(map, 0, tmap, dp);
		return ans;
	}

	// i表示来到的当前贴纸
	public static int process2(int[][] map, int i, int[] tmap, HashMap<String, Integer> dp) {
		StringBuilder keyBuilder = new StringBuilder();
		keyBuilder.append(i + "_");
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				keyBuilder.append((char) (asc + 'a') + "_" + tmap[asc] + "_");
			}
		}
		String key = keyBuilder.toString();
		if (dp.containsKey(key)) {
			return dp.get(key);
		}
		boolean finish = true;
		for (int asc = 0; asc < 26; asc++) {
			if (tmap[asc] != 0) {
				finish = false;
				break;
			}
		}
		if (finish) {
			dp.put(key, 0);
			return 0;
		}
		if (i == map.length) {
			dp.put(key, -1);
			return -1;
		}
		int maxZhang = 0;
		for (int asc = 0; asc < 26; asc++) {
			if (map[i][asc] != 0 && tmap[asc] != 0) {
				maxZhang = Math.max(maxZhang, (tmap[asc] / map[i][asc]) + (tmap[asc] % map[i][asc] == 0 ? 0 : 1));
			}
		}
		int[] backup = Arrays.copyOf(tmap, tmap.length);
		int min = Integer.MAX_VALUE;
		int next = process2(map, i + 1, tmap, dp);
		tmap = Arrays.copyOf(backup, backup.length);
		if (next != -1) {
			min = next;
		}
		for (int zhang = 1; zhang <= maxZhang; zhang++) {
			for (int asc = 0; asc < 26; asc++) {
				tmap[asc] = Math.max(0, tmap[asc] - (map[i][asc] * zhang));
			}
			next = process2(map, i + 1, tmap, dp);
			tmap = Arrays.copyOf(backup, backup.length);
			if (next != -1) {
				min = Math.min(min, zhang + next);
			}
		}
		int ans = min == Integer.MAX_VALUE ? -1 : min;
		dp.put(key, ans);
		return ans;
	}


	// 测试
	public static void main(String[] args) {
		String target = "abcccdddddbbbbaaaa";
		String[] arr = {"aaa","bbb","ccdd"};
		System.out.println(minStickers1(arr,target));
		System.out.println(minStickers2(arr,target));
	}

}
