package com.model.localcalendar;

import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;


public class LocalCalendar {
	private static final String CALENDARNAME = "Maestria";
	private String[] lCalendarFields = new String[]{"_id", "displayName"};
	private String[] lEventFields = new String[]{"title", "dtstart", "dtend"};
	
	private String cCalendarname;
	private Context mcontext;
	private Uri mURI;
	private String mCalendarId="0";

	public LocalCalendar(String aCalendarName, Context aContext) {
		this.cCalendarname = aCalendarName;
		this.mcontext = aContext;
		this.mURI = this.getUri();
		this.mCalendarId = this.getCalendar();
	}
	
	public LocalCalendar(Context aContext) {
		this (CALENDARNAME, aContext);
	}
	
	private Uri getUri(){
		Uri pURI;
		if (Build.VERSION.SDK_INT >= 8 ) {
			pURI = Uri.parse("content://com.android.calendar/calendars");
		} else {
			pURI = Uri.parse("content://calendar/calendars");
		}
		return pURI;
	}
	
	private String getCalendar() {
		String calId = "";
		
		Cursor lCursor = this.mcontext.getContentResolver().query(this.mURI, this.lCalendarFields, "selected=1 and displayName='"+this.cCalendarname+"'", null, null);
		if (lCursor.moveToFirst()) {
			int idColumn = lCursor.getColumnIndex(this.lCalendarFields[0]);
			calId = lCursor.getString(idColumn);
		}
		return calId;
	}
	
	public Event getNextEvent() {
		Event pEvent = null;
		Calendar actualTime = Calendar.getInstance();
		long lNow = actualTime.getTimeInMillis();
		
		Cursor lCursor = mcontext.getContentResolver().query(this.mURI, this.lEventFields, "calendar_id=" + this.mCalendarId + " and dtstart>=" +lNow, null, "dtstart ");     
		if (lCursor.moveToFirst()) {
			int lcolTitle = lCursor.getColumnIndex(this.lEventFields[0]);
			int lcolBegin = lCursor.getColumnIndex(this.lEventFields[1]);
			int lcolEnd = lCursor.getColumnIndex(this.lEventFields[2]);
			pEvent = new Event(lCursor.getString(lcolTitle),lCursor.getString(lcolBegin),lCursor.getString(lcolEnd));
		}
		return pEvent;
	}
	

}
