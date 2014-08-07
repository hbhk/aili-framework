package org.hbhk.aili.job;

import java.util.HashMap;
import java.util.Map;

public class CronExRelated {

	public static final String _EVERY = "every"; 
	public static final String _ANY = "any"; 
	public static final String _RANGES = "ranges"; 
	public static final String _INCREMENTS = "increments"; 
	public static final String _ADDITIONAL = "additional"; 
	public static final String _LAST = "last"; 
	public static final String _WEEKDAY = "weekday"; 
	public static final String _THENTH = "theNth"; 
	public static final String _CALENDAR = "calendar"; 
	public static final String _TYPE = "type"; 

	/** 
	* 0 0 6 ? * 1#1 ? monthly 
	* 0 0 6 ? * 1 ? weekly 
	* 0 0 6 30 7 ? 2006 useDefined 
	*/ 
	static String[] headTitle = {"TYPE","SECONDS","MINUTES","HOURS","DAYOFMONTH","MONTH","DAYOFWEEK","YEAR"}; 

	/** 
	* cron expression special characters 
	* Map 
	* specialCharacters 
	*/ 
	public static Map<String,Object> specialCharacters; 

	static {
	specialCharacters = new HashMap<String,Object>(10); 
	specialCharacters.put(_EVERY, "*"); 
	specialCharacters.put(_ANY, "?");
	specialCharacters.put(_RANGES, "-");
	specialCharacters.put(_INCREMENTS, "/"); 
	specialCharacters.put(_ADDITIONAL, ",");
	specialCharacters.put(_LAST, "L");
	specialCharacters.put(_WEEKDAY, "W");
	specialCharacters.put(_THENTH, "#");
	specialCharacters.put(_CALENDAR, "C");
	specialCharacters.put(_TYPE, headTitle);
	
	} 

	public static void set(String ex, int index) { 
	  ((String[])specialCharacters.get(_TYPE))[index] = ex; 
	} 

}
