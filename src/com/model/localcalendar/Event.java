package com.model.localcalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";
	
	private String cTitle;
	private long lBegin;
	private long lEnd;

	public Event(String aTitle, String aBegin, String aEnd) {
		this.cTitle = aTitle;
		this.lBegin = Long.parseLong(aBegin);
		this.lEnd = Long.parseLong(aEnd);
	}
	
	public Event(String aTitle, long aBegin, long aEnd) {
		this.cTitle = aTitle;
		this.lBegin = aBegin;
		this.lEnd = aEnd;
	}
	
	public String getTitle(){
		return this.cTitle;
	}
	
	public long getBegin(){
		return this.lBegin;
	}
	
	public long getEnd(){
		return this.lEnd;
	}
	
	public String BeginToString(){
		return getDateTimeStr(Long.toString(this.lBegin));
	}
	
	public String EndToString(){
		return getDateTimeStr(Long.toString(this.lEnd));
	}

    public static String getDateTimeStr(String p_time_in_millis) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
    	Date ltime = new Date(Long.parseLong(p_time_in_millis));
    	return sdf.format(ltime);
    }
	
}
