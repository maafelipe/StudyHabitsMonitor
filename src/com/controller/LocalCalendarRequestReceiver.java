package com.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.model.localcalendar.Event;
import com.model.localcalendar.LocalCalendar;

public class LocalCalendarRequestReceiver extends BroadcastReceiver{
	private static final String TAG = LocalCalendarRequestReceiver.class.getSimpleName();
	private ServiceManager service;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG,"Starting LocalCalendarRequestService");
		
		LocalCalendar pCalendar = new LocalCalendar(context);
		Event pEvent = pCalendar.getNextEvent();
		//Logica para revisar si el evento siguiente tiene alarma		
		
		service = ServiceManager.getInstance();
		service.setContext(context);
		service.createCalendarRequestAlarm();
	}
}
