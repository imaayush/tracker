package com.example.share;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Environment;
import android.util.Base64;

public class WebService {
	//Namespace of the Webservice - can be found in WSDL
	private static String NAMESPACE = "http://calculator.me.org/";
	//Webservice URL - WSDL File location	
	private static String URL = "http://192.168.1.12:8084/ShareSmvdu/Calc?wsdl";//Make sure you changed IP address
	//SOAP Action URI again Namespace + Web method name
	private static String SOAP_ACTION = "http://calculator.me.org/";
	
	public static boolean invokeLoginWS(String userName,String passWord, String webMethName) {
		boolean loginStatus = false;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		// Set Username
		unamePI.setName("username");
		// Set Value
		unamePI.setValue(userName);
		// Set dataType
		unamePI.setType(String.class);
		// Add the property to request object
		request.addProperty(unamePI);
		//Set Password
		passPI.setName("password");
		//Set dataType
		passPI.setValue(passWord);
		//Set dataType
		passPI.setType(String.class);
		//Add the property to request object
		request.addProperty(passPI);
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			loginStatus = Boolean.parseBoolean(response.toString());

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			MainActivity.errored = true;
			e.printStackTrace();
		} 
		//Return booleam to calling object
		return loginStatus;
	}
	
	
	
	
	
	public static String invokeFileViewWS(String fileid, String webMethName) {
		String downStatus = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo filePI = new PropertyInfo();
		
		// Set Username
		filePI.setName("fileid");
		// Set Value
		filePI.setValue(fileid);
		// Set dataType
		filePI.setType(String.class);
		// Add the property to request object
		request.addProperty(filePI);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			String str =response.toString();

           
            downStatus=str;
            return downStatus;

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			MainActivity.errored = true;
			e.printStackTrace();
		} 
		//Return booleam to calling object
		return downStatus;
	}
	public static String invokeFileWS(String fileid, String webMethName) {
		String downStatus = null;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo filePI = new PropertyInfo();
		
		// Set Username
		filePI.setName("fileid");
		// Set Value
		filePI.setValue(fileid);
		// Set dataType
		filePI.setType(String.class);
		// Add the property to request object
		request.addProperty(filePI);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			String str =response.toString();

            byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
            
            OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Download/newsex.torrent" );
            out.write(decodedString);
            out.close();
            downStatus="success";
            return downStatus;

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			MainActivity.errored = true;
			e.printStackTrace();
		} 
		//Return booleam to calling object
		return downStatus;
	}
	
	public static ArrayList<String> invokeNotiWS(String userName, String webMethName) {
		
	    ArrayList<String> notiStatus = new ArrayList<String>() ;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName);
		// Property which holds input parameters
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		// Set Username
		unamePI.setName("username");
		// Set Value
		unamePI.setValue(userName);
		// Set dataType
		unamePI.setType(String.class);
		// Add the property to request object
		request.addProperty(unamePI);
		
		
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			//SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			SoapObject obj1 = (SoapObject) envelope.bodyIn;

	       int j  =obj1.getPropertyCount();
	     
	        for (int i = 0; i<j; i++) 
            { 
               
               String id1=obj1.getProperty(i).toString();

               if(id1 != "")
               { 
            	   notiStatus.add(id1);  

               }
                
            }
			
			

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			MainActivity.errored = true;
			e.printStackTrace();
		} 
		//Return booleam to calling object
		return notiStatus;
	}
	    public static boolean invokeRegisterWS(String username,String email ,String password,String name ,String gender,String webMethName){
		boolean registerStatus = false;
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, webMethName );
		// Property which holds input parameters
		PropertyInfo unamePI = new PropertyInfo();
		PropertyInfo passPI = new PropertyInfo();
		PropertyInfo emailPI = new PropertyInfo();
		PropertyInfo  namePI = new PropertyInfo();
		PropertyInfo genderPI = new PropertyInfo();
		
		// Set Username
		unamePI.setName("username");
		// Set Value
		unamePI.setValue(username);
		// Set dataType
		unamePI.setType(String.class);
		// Add the property to request object
		request.addProperty(unamePI);
		
		//Set Password
		passPI.setName("password");
		//Set dataType
		passPI.setValue(password);
		//Set dataType
		passPI.setType(String.class);
		//Add the property to request object
		request.addProperty(passPI);
		
		//Set Password
	    emailPI.setName("email");
		//Set dataType
		emailPI.setValue(email);
		//Set dataType
		emailPI.setType(String.class);
		//Add the property to request object
		request.addProperty(emailPI);
		
		//Set Password
	    namePI.setName("name");
		//Set dataType
		namePI.setValue(name);
		//Set dataType
		namePI.setType(String.class);
		//Add the property to request object
		request.addProperty(namePI);
		
		//Set Password
	    genderPI.setName("gender");
		//Set dataType
		genderPI.setValue(gender);
		//Set dataType
		genderPI.setType(String.class);
		//Add the property to request object
		request.addProperty(genderPI);
		
		
		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			// Invoke web service
			androidHttpTransport.call(SOAP_ACTION+webMethName, envelope);
			// Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			// Assign it to  boolean variable variable
			registerStatus = Boolean.parseBoolean(response.toString());

		} catch (Exception e) {
			//Assign Error Status true in static variable 'errored'
			MainActivity.errored = true;
			e.printStackTrace();
		} 
		//Return booleam to calling object
		return registerStatus;
		
	}
}
