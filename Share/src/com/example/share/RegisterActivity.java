package com.example.share;



import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	static boolean errored = false;
	Button b;
    EditText username ,email,password,first,last ; 
    TextView statusTV;
    ProgressBar webservicePG;
    Spinner gender ;
    boolean registerStatus;
    String username1,email1,password1,first1,gender1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 super.onCreate(savedInstanceState);
		  setContentView(R.layout.register);
		
		   //Name Text control
		  username =(EditText) findViewById(R.id.editText1);
		  email =(EditText) findViewById(R.id.editText2);
		  //password=(EditText) findViewById(R.id.editText3);
		  first=(EditText) findViewById(R.id.editText4);
		  last=(EditText) findViewById(R.id.editText5);
		  gender = (Spinner)findViewById(R.id.spinner1);
		  //Display Text control
		  statusTV = (TextView) findViewById(R.id.tv_result1);
		  //Button to trigger web service invocation
		  b= (Button) findViewById(R.id.button1);
		  //Display progress bar until web service invocation completes
		  webservicePG = (ProgressBar) findViewById(R.id.progressBar2);
		  //Button Click Listener
		  
		  b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				if(username.getText().length() != 0 &&email.getText().length() != 0 &&  first.getText().length() != 0 && last.getText().length() != 0 ){
					
					username1 = username.getText().toString();
            		email1 = email.getText().toString();
            		password1 = "aayush";
            		first1 = first.getText().toString();
            		
            		statusTV.setText("");
            		AsyncCallWS1 task1 = new AsyncCallWS1();
            		task1.execute();
				}else{
					statusTV.setText("Please Complete form");
				}
				
			}
		  });
		  String[] items = new String[]{ "Male", "Female","Other"};
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		  gender.setAdapter(adapter);
		  gender.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				   gender1 = gender.getItemAtPosition(position).toString();
				 
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			
			registerStatus = WebService.invokeRegisterWS(username1, email1, password1, first1, gender1, "registerUser");
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			Intent intObj = new Intent(RegisterActivity.this,MainActivity.class);
			//Error status is false
			if(!errored){
				//Based on Boolean value returned from WebService
				if(registerStatus){
					//Navigate to Home Screen
					Toast.makeText(getApplicationContext(),"Password is send to your email",Toast.LENGTH_SHORT).show();
					startActivity(intObj);
				}else{
					//Set Error message
					statusTV.setText("Username or Email already register, try again");
				}
			//Error status is true	
			}else{
				statusTV.setText("Error occured in invoking webservice");
			}
			//Re-initialize Error Status to False
			errored = false;
		}

		@Override
		//Make Progress Bar visible
		protected void onPreExecute() {
			webservicePG.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
		}
	}	
	}




