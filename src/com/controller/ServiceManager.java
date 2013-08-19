package com.controller;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class ServiceManager {
	private static final int LOCATION_REQUEST = 31031;
	private static final int CALENDAR_REQUEST = 31032;
	private static final int MINUTES = 60*1000;
	private static final int CALENDAR_REQ_INTERVAL = 15;
	
	private static ServiceManager INSTANCE;
	private Context mContext;
	private Bundle 	mBundle = null;
	
	private static PendingIntent currentLocationAlarm = null;
	private static PendingIntent currentCalendarAlarm = null;
	
	public static ServiceManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ServiceManager();
		}
		return INSTANCE;
	}
	
	public void setContext(Context context){
		this.mContext = context;
	}
	
	public Context getContext(){
		return mContext;
	}
	
	public void setExtras(Bundle bndlExtras){
		this.mBundle = bndlExtras;
	}
	
	private AlarmManager getAlarmManager(){
		AlarmManager manager = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
		return manager;
	}
	
/* ---------  Alarm manager to activate GPS service--------- */	
	public void createLocationRequestAlarm(long i){
		PendingIntent locationRequest = this.createGPSLocationRequest();
		this.currentLocationAlarm = locationRequest;
		AlarmManager manager = this.getAlarmManager();
		manager.set(AlarmManager.RTC_WAKEUP, i, locationRequest);
	}
	
	private PendingIntent createGPSLocationRequest(){
		Intent locationRequest = new Intent(mContext,GPSLocationRequestReceiver.class);
		if (this.mBundle!= null){
			locationRequest.putExtras(this.mBundle);
		}
		PendingIntent pendingLocationRequest = PendingIntent.getBroadcast(
				mContext, 
				LOCATION_REQUEST, 
				locationRequest, 
				PendingIntent.FLAG_CANCEL_CURRENT
				);
		return pendingLocationRequest;
	}
	
	public void cancelGPSAlarm(){
		PendingIntent locationRequest = createGPSLocationRequest();
		AlarmManager manager = this.getAlarmManager();
		manager.cancel(locationRequest);
	}
	
/* ---------  Alarm manager to read local calendar --------- */
	public void createCalendarRequestAlarm(){
		this.createCalendarRequestAlarm(CALENDAR_REQ_INTERVAL);
	}
	
	public void createCalendarRequestAlarm(int i){
		if (this.currentCalendarAlarm != null){
			PendingIntent calendarRequest = createCalendarRequest();
			this.currentCalendarAlarm = calendarRequest;
			Calendar actualTime = Calendar.getInstance();
			AlarmManager manager = getAlarmManager();
			manager.setRepeating(AlarmManager.RTC_WAKEUP, actualTime.getTimeInMillis()+1*MINUTES,i*MINUTES,calendarRequest);
		}
	}
	
	private PendingIntent createCalendarRequest(){
		Intent calendarRequest = new Intent(mContext,LocalCalendarRequestReceiver.class);
		PendingIntent pendingLocationRequest = PendingIntent.getBroadcast(
				mContext, 
				CALENDAR_REQUEST, 
				calendarRequest, 
				PendingIntent.FLAG_CANCEL_CURRENT
				);
		return pendingLocationRequest;
	}
	
	public void cancelCalendarAlarm(){
		PendingIntent calendarRequest = createCalendarRequest();
		AlarmManager manager = getAlarmManager();
		manager.cancel(calendarRequest);
	}

}
