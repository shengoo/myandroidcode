package cn.qing.ksop2test;

import java.io.Writer;

import org.ksoap2.*;
import org.ksoap2.serialization.*;
import org.ksoap2.transport.*;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;

public class ksop2test extends Activity {
	/** Called when the activity is first created. */
	
	//METHOD_NAME is the request
	private static final String METHOD_NAME = "HelloWorldRequest";
//	private static final String METHOD_NAME = "HelloWorld";
	
	//NAMESPACE must end with "/"
	private static final String NAMESPACE = "http://tempuri.org/";
//	private static final String NAMESPACE = "http://tempuri.org";
	
	private static final String URL = "http://192.168.0.2:8080/HelloWCF/Service1.svc";
//	private static final String URL = "http://192.168.0.2:8080/webservice1/Service1.asmx";
	
	final String SOAP_ACTION = "http://tempuri.org/IService1/HelloWorld";
//	final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
	TextView tv;
	StringBuilder sb;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		sb = new StringBuilder();
		call();
		tv.setText(sb.toString());
		setContentView(tv);
	}

	public void call() {
		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			request.addProperty("Name", "Qing");
			
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);


			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive)envelope.getResponse();

			//to get the data
			String resultData = result.toString();


			sb.append(resultData + "\n");
		} catch (Exception e) {
			sb.append("Error:\n" + e.getMessage() + "\n");
		}

	}

}