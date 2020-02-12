package indi.monkey.test;

import java.util.UUID;

public class UUIDTest {
	
	public static void test01() {
		String string = UUID.randomUUID().toString();
		System.out.println(string);
	}
	public static void main(String[] args) {
		test01();
	}
}
