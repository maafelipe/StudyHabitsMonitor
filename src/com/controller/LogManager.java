package com.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.log.GPSLogEntry;

public class LogManager {
	public static final String LOG_REGISTRY_PATH = "registry_log.dat";
	public static final String accionSoap =	"urn:wsstudyhabits#setLogs";
	public static final String Metodo = "setLogs";
	public static final String namespace = "urn:wsstudyhabits";
	public static final String url = "http://fmatest.p.ht/wslogs/wsstudyhabits.php";
	
	private static LogManager INSTANCE;
	
	private Context mContext;

	public static LogManager getInstance() {
		if(INSTANCE == null){
			INSTANCE = new LogManager();
		}
		return INSTANCE;
	}
	
	public void setContext(Context context){
		mContext = context;
	}
	
	public void registerEntry(Location location){
		String sJson = "";
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		//AppUser appUser = loadAppUser();
		//String deviceName = loadDeviceName();
		GPSLogEntry logEntry = new GPSLogEntry("",latitude,longitude,new Date());
		sJson = this.logToJSON(logEntry)+ "\n";
		this.writeRegistry(sJson);
		//this.sendToServer();
	}
	
	private String logToJSON(GPSLogEntry logEntry){
		Gson oGson = new GsonBuilder().create();
		return oGson.toJson(logEntry);
	}
	
	private void writeRegistry(String sOutput){
		try{
			FileOutputStream outputStream = mContext.openFileOutput(LOG_REGISTRY_PATH, Context.MODE_PRIVATE | Context.MODE_APPEND);
			PrintWriter writer = new PrintWriter(new BufferedOutputStream(outputStream));
			writer.println(sOutput);
			writer.close();
			outputStream.close();
		}catch(IOException e){
			
		}
	}
	
	public String sendToServer(){
    	String lstrRet = "";
    	String lstrLog = "";
		try{
    		SoapObject request = new SoapObject(namespace, Metodo);
    		lstrLog = this.readRegistry();
    		request.addProperty("xmlInput", lstrLog);
    		//Modelo el Sobre
    		SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    		sobre.dotNet = false;
    		sobre.setOutputSoapObject(request);
    		//Modelo el transporte
    		HttpTransportSE transporte = new HttpTransportSE(url);
    		//Llamada
    		transporte.call(accionSoap, sobre);
    		//Resultado
    		SoapPrimitive resultado = (SoapPrimitive) sobre.getResponse();
    		} 
    	catch (Exception e){
    		lstrRet = e.getMessage();
    	}
    	return lstrRet;
	}
	
	private String readRegistry() throws Exception{
		String lstrLog = "";
		try{
			FileInputStream inputStream = mContext.openFileInput(LOG_REGISTRY_PATH);
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			Scanner scanner = new Scanner(reader);
			while(scanner.hasNextLine()){
				lstrLog += scanner.nextLine();
			}
			return lstrLog;
		}catch(Exception e){
			lstrLog = e.getMessage();
			return lstrLog;
		}
	}
		
}
