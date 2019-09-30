package utils;

import net.bytebuddy.utility.RandomString;


public class RandomData {

	public static String alpha_numeric_string(int length) {
		return RandomString.make(length);
	}
	
	public static String email() {
		String address = RandomString.make(10);
		String domain = RandomString.make(5);
		return address + "@" + domain + ".com";
	}
}
