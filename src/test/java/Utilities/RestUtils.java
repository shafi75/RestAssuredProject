package Utilities;

import org.apache.commons.lang3.RandomStringUtils;

public class RestUtils {
	
	public static String empName() {
		String generatedString=RandomStringUtils.randomAlphabetic(1);
		return ("Johns"+generatedString);
	}
	public static String empSal() {
		String generatedString=RandomStringUtils.randomNumeric(5);
		return (generatedString);
	}
	public static String empAge() {
		String generatedString=RandomStringUtils.randomNumeric(2);
		return (generatedString);
	}
	public static String empEmail() {
		String generatedString=RandomStringUtils.randomAlphanumeric(6);
		return (generatedString+"@gmail.com");
	}
	public static String empPassword() {
		String generatedString=RandomStringUtils.randomAlphanumeric(6);
		return (generatedString);
	}
	public static String empJob() {
		String generatedString=RandomStringUtils.randomAlphanumeric(4);
		return (generatedString);
	}
}
