package com.paragallo.qing;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MyEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ksoap2test2 extends Activity {
    /** Called when the activity is first created. */
	private static final String METHOD_NAME = "BrowseProductsRequest";
	
	private static final String NAMESPACE = "http://Pgm.ExternalProductService.ServiceContracts/2009/07";
	
	private static final String URL = "http://productservice.paragallo.no/TRUNK/ProductAdminService.svc/basic";
	
	private static final String SOAP_ACTION = "BrowseProducts";
	
	
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
    	
    	String searchCriteria = String.format("<ContentTypeID>13</ContentTypeID><RightsOrgID>321</RightsOrgID>");
    	String resultCriteria = String.format("<StartPos>0</StartPos><MaxRows>1</MaxRows><IncludeAllMetadata>false</IncludeAllMetadata>");
    	
		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
			
			PropertyInfo a1 = new PropertyInfo();
		    a1.setName("a1");a1.setValue("value1");
		    
			request.addProperty(a1 );
//			request.addProperty("SearchCriteria",searchCriteria);
//			request.addProperty("ResultCriteria",resultCriteria);
			
//			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
//					SoapEnvelope.VER11);
			MyEnvelope envelope = new MyEnvelope(SoapEnvelope.VER11);
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

