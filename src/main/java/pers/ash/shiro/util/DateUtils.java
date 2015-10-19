package pers.ash.shiro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String now(){
		return sdf.format(new Date());
	}
	
	public static String format(String date) throws ParseException{
		return sdf.format(sdf.parse(date));
	}
	
}
