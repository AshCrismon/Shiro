package pers.ash.shiro.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String now(){
		return sdf.format(new Date());
	}
	
}
