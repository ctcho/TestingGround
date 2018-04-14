package basic;

import javafx.util.Pair;

public class PairTest {
	public static void main(String[] args) {
		Pair<String, String> pair = new Pair<String, String>("a", "b");
		System.out.println(pair.getKey());
		System.out.println(pair.getValue());
	}
}
