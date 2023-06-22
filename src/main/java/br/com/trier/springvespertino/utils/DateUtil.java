package br.com.trier.springvespertino.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static ZonedDateTime convertStringToZDT(String strDate) {
		return LocalDate.parse(strDate, dtf).atStartOfDay(ZoneId.systemDefault());
	}
	
	public static String convertZDTToString(ZonedDateTime date) {
		return dtf.format(date);
	}
}
