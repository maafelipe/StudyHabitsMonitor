package com.log;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;

public class UserSettings {
	
	private String user;
	private String pass;
	private String deviceName;
	private Context mContext;
	
	public static final String USER_SETTINGS_PATH = "userSettings.dat";
	
	public String getUser(){
		return user;
	}
	
	public void setUser(String aUser){
		user = aUser;
	}
	
	public String getPass(){
		return pass;
	}
	
	public void setPass(String aPass){
		pass = aPass;
	}
	
	public String getDeviceName(){
		return deviceName;
	}
	
	public void setDeviceName(String aDeviceName){
		deviceName = aDeviceName;
	}
	
	public void setContext(Context context){
		mContext = context;
	}
	
	public void writeToFile(){
		try{
			FileOutputStream outputStream = mContext.openFileOutput(USER_SETTINGS_PATH, Context.MODE_PRIVATE | Context.MODE_APPEND);
			PrintWriter writer = new PrintWriter(new BufferedOutputStream(outputStream));
			//writer.println(sOutput);
			writer.close();
			outputStream.close();
		}catch(IOException e){
			
		}
	}

}
