package Class10_并查集;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
/*
* 	1)有若干个样本a、b、c、d…类型假设是V
	2)在并查集中一开始认为每个样本都在单独的集合里
	3)用户可以在任何时候调用如下两个方法
	boolean isSameSet(V x,V y):查询样本x和样本y是否属于一个集合
	void union(V x,V y):把x和各自所在集合的所有样本合并成一个集合
	4) isSameSet和 union方法的代价越低越好
* */
public class Code01_UnionFind {

	// 样本进来会包一层，叫做元素
	public static class Element<V> {
		public V value;

		public Element(V value) {
			this.value = value;
		}

	}

	public static class UnionFindSet<V> {
		public HashMap<V, Element<V>> elementMap;

		// key  某个元素  value 该元素的父元素
		public HashMap<Element<V>, Element<V>> fatherMap;

		// key 某个集合的代表元素   value 该集合的大小
		public HashMap<Element<V>, Integer> sizeMap;

		public UnionFindSet(List<V> list) {
			elementMap = new HashMap<>();
			fatherMap = new HashMap<>();
			sizeMap = new HashMap<>();
			for (V value : list) {
				Element<V> element = new Element<V>(value);
				elementMap.put(value, element);
				fatherMap.put(element, element);
				sizeMap.put(element, 1);
			}
		}

		// 给定一个ele，往上一直找，把代表元素返回
		private Element<V> findHead(Element<V> element) {
			Stack<Element<V>> path = new Stack<>();
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		/**
		 * 是否在同一个集合下面
		 * @param a
		 * @param b
		 * @return
		 */
		public boolean isSameSet(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
			}
			return false;
		}

		/**
		 * 两个集合合并
		 * @param a
		 * @param b
		 */
		public void union(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				Element<V> aF = findHead(elementMap.get(a));
				Element<V> bF = findHead(elementMap.get(b));
				if (aF != bF) {
					Element<V> big = sizeMap.get(aF) >= sizeMap.get(bF) ? aF : bF;
					Element<V> small = big == aF ? bF : aF;
					fatherMap.put(small, big);
					sizeMap.put(big, sizeMap.get(aF) + sizeMap.get(bF));
					sizeMap.remove(small);
				}
			}
		}
	}



	/*题目一： (1,10,13) (2,10,37) (400,500,37)
	 如果两个uesr，a字段一样、或者b字段一样或者c字段一样，就认为是一个人
	 请合并users，返回合并之后的用户数量
	* */

	public static class User{
		public String a;
		public String b;
		public String c;

		public User(String a, String b, String c) {
			this.a = a;
			this.b = b;
			this.c = c;
		}
	}
	// ......
}
