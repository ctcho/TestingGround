package basic;

import java.util.*;

public class ImmutableEntryTest {
	public static void main(String[] args) {
		AbstractMap.SimpleEntry<String, String> pair = new AbstractMap.SimpleEntry<String, String>("a", "b");
		System.out.println(pair.getKey());
		System.out.println(pair.getValue());
	}
}
