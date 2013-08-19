package com.log;

import java.text.SimpleDateFormat;
import java.util.Date;

//@PersistenceCapable
public class GPSLogEntry {
	/*@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key primaryKey;*/
	
	//@Persistent
	//private AppUser appUser;
	
	//@Persistent
	private String deviceName;
	
	//@Persistent
	private double latitude;
	
	//@Persistent
	private double longitude;
	
	//@Persistent
	private Date logEntryDate;
	
	public GPSLogEntry(){
		
	}
	
	public GPSLogEntry(
			//AppUser appUser,
			String deviceName,
			double latitude, 
			double longitude, 
			Date logEntryDate
			){
		//this.appUser = appUser;
		this.deviceName = deviceName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.logEntryDate = logEntryDate;
	}

	/*public AppUser getAppUser() {
		return appUser;
	}


	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}*/

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Date getLogEntryDate() {
		return logEntryDate;
	}

	public void setLogEntryDate(Date logEntryDate) {
		this.logEntryDate = logEntryDate;
	}
	
	public String toString(){
		String logEntryString = "";
		//logEntryString += "Username: " + appUser.getUserName() + "\n";
		logEntryString += "Location: (" + getLatitude() + "," + getLongitude() + ")\n";
		logEntryString += "DeviceName: " + getDeviceName() + "\n";
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEE-MMM-dd HH:mm");
		logEntryString += dateFormat.format(getLogEntryDate()) + "\n";
		return logEntryString;
	}
	
}
