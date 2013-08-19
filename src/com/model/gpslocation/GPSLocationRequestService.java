package com.model.gpslocation;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.controller.LogManager;
import com.controller.ServiceManager;

public class GPSLocationRequestService extends Service {	
	private static final String TAG = GPSLocationRequestService.class.getSimpleName();
	private static final long MINUTES = 60 * 1000;
	private int sensingTime = 1; //temporalmente aqui, mover a una classe settings
	
	private LocationManager mLocationManager;
	private ServiceManager service;
	private LogManager mLogManager;
	//private SettingsManager mSettingsManager;
	public static Intent mIntent = null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		//showText(TAG + " is Enabled");
		Log.i(TAG,"fmassa 1");
		checkLocationAvailability();
		Log.i(TAG,"fmassa 2");
		setLogManagerUp();
		Log.i(TAG,"fmassa 3");
		getSensingTime();
		Log.i(TAG,"fmassa 4");
		configureLocationUpdates();
		Log.i(TAG,"fmassa 5");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setLogManagerUp(){
		mLogManager = LogManager.getInstance();
		mLogManager.setContext(getApplicationContext());
	}
	
	private void getSensingTime(){
		//mSettingsManager = SettingsManager.getInstance();
		//mSettingsManager.setContext(getApplicationContext());
		//AppSettings settings = mSettingsManager.getAppSettings();
		//sensingTime = settings.getSensingTime();
	}
	
	private void configureLocationUpdates(){
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, sensingTime * MINUTES, 1, onLocationChange);
	}
			
	@Override
	public void onDestroy() {
		Log.i(TAG, "fmassa GPSLocationService being Destroyed");
		mLocationManager.removeUpdates(onLocationChange);
		super.onDestroy();
	}

	private void checkLocationAvailability() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if(!isGPSEnabled && !isNetworkEnabled){
			enableLocationSettings();
		}
	}
	
	private void enableLocationSettings() {
		Intent locationSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		locationSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(locationSettings);
	}

	/*
	private void showText(String msg){
		Toast text = Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG);
		text.show();
	}*/
	
	private LocationListener onLocationChange = new LocationListener() {

		@Override
		public void onLocationChanged(Location arg0) {
			// TODO Auto-generated method stub
			mLogManager.registerEntry(arg0);
			Toast.makeText(getApplicationContext(),"Latitud = " + String.valueOf(arg0.getLatitude()) +" Longitud= "+ String.valueOf(arg0.getLongitude()),Toast.LENGTH_LONG).show();
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG,"GPS Disabled");
			stopSelf();
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
	};
}

