package utils;

import net.bytebuddy.utility.RandomString;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;


public class RandomData {

	public static String alpha_numeric_string(int length) {
		return RandomString.make(length);
	}
	
	public static String email() {
		String address = RandomString.make(10);
		String domain = RandomString.make(5);
		return address + "@" + domain + ".com";
	}

	public static String dateTime_yyyyMMddHHmmss() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMss HHmmss");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
}
