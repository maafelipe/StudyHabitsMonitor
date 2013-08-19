package com.controller;


import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.model.gpslocation.GPSLocationRequestService;

public class GPSLocationRequestReceiver extends BroadcastReceiver{
	private static final String TAG = GPSLocationRequestReceiver.class.getSimpleName();
	private ServiceManager service;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		long iMin = -1;
        int idAlarm, icont, on;
        long ibegin,idelta;
		Bundle bundle = intent.getExtras();
        //Reading alarm settings
		on = bundle.getInt("on");
        idAlarm = bundle.getInt("id");
    	icont = bundle.getInt("cont");
    	ibegin = bundle.getLong("begin");
    	idelta = bundle.getLong("delta");
    	
        if(on==0){
        	on = 1;

        	if (GPSLocationRequestService.mIntent != null){
        		Log.i(TAG,"GPS service is running, it will be stopped");
        		context.stopService(GPSLocationRequestService.mIntent);
        	}
        	//Toast.makeText(context, "GPS Off",Toast.LENGTH_LONG).show();
        	if (icont>0 && icont <3 ){
        		iMin = ibegin+ icont*idelta;
        		icont = icont +1;
        	}else{
        		if (icont == 0){
        			//begin good
        			//iMin = ibegin
        			//icont = 1;
        			//end good
	        		
        			//LocalCalendar pCalendar = new LocalCalendar(context);
		    		//Event pEvent = pCalendar.getNextEvent();
		    		//if (pEvent != null){
		    			//iMin = pEvent.getBegin();
		    			//idelta = (pEvent.getEnd()-iMin)/3;
        				Calendar actualTime = Calendar.getInstance(); 
        				iMin = actualTime.getTimeInMillis()+10*1000;
        				idelta = 20*1000;
		    			ibegin = iMin;
		    		//}
		    		icont = 1;
        		}
        	}
        }else{
        	on = 0;
       	
        	Intent serviceIntent = new Intent(context,GPSLocationRequestService.class);
        	GPSLocationRequestService.mIntent = serviceIntent; 
        	context.startService(serviceIntent);
        	Log.i(TAG,"GPS service will be started");
        	//Toast.makeText(context, "GPS On",Toast.LENGTH_LONG).show();
        	Calendar actualTime = Calendar.getInstance(); 
			iMin = actualTime.getTimeInMillis()+10*1000;
        }
    	bundle.putInt("on", on);
    	bundle.putInt("id", idAlarm);
    	bundle.putInt("cont", icont);
    	bundle.putLong("begin", ibegin);
    	bundle.putLong("delta", idelta);
        
        if (iMin > -1){
        	long lstart = iMin;
        	service = ServiceManager.getInstance();
        	service.setContext(context);
        	service.setExtras(bundle);
        	service.createLocationRequestAlarm(lstart);
        }
	}
}
