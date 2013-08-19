package com.androideity.systemservices;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.controller.ServiceManager;

public class SystemServicesTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void startAlert(View view) {
		EditText text = (EditText) findViewById(R.id.txt_tiempo);
		int i = Integer.parseInt(text.getText().toString());
		ServiceManager service = ServiceManager.getInstance();
		service.setContext(this.getApplicationContext());
		Bundle bundle = new Bundle();
		bundle.putInt("on", 0);
    	bundle.putInt("cont", 0);
		service.setExtras(bundle);
		service.createLocationRequestAlarm(i);
		//sendLogs();
		Toast.makeText(this, "Has fijado la alarma en " + i + " segundos",
				Toast.LENGTH_LONG).show();
	}
    
    public void sendLogs(){
    	String accionSoap =	"urn:wsstudyhabits#setLogs";
    	String Metodo = "setLogs";
    	String namespace = "urn:wsstudyhabits";
    	String url = "http://fmatest.p.ht/wslogs/wsstudyhabits.php";
    	
    	try{
    		SoapObject request = new SoapObject(namespace, Metodo);
    		request.addProperty("xmlInput", "testfma");
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
    		e.printStackTrace();
    	}
    }
    
}