package com.example.share;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity {
	 protected static final SharedPreferences settings = null;
	static boolean errored = false;
	Button b,b1;
	TextView statusTV;
	EditText userNameET , passWordET;	
	ProgressBar webservicePG;
	String editTextUsername;
	boolean loginStatus;
	String editTextPassword,checkpoint;
	 public static final String PREFS_NAME = "MyApp_Settings";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Name Text control
        SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
	     checkpoint = settings.getString("key", "");
	     if(!checkpoint.equals("")){
	    	 Intent intObj = new Intent(MainActivity.this,HomeActivity.class);
			 intObj.putExtra("username", checkpoint);
			 startActivity(intObj);
				finish();
	     }
        userNameET = (EditText) findViewById(R.id.editText1);
        passWordET = (EditText) findViewById(R.id.editText2);
        //Display Text control
        statusTV = (TextView) findViewById(R.id.tv_result);
        //Button to trigger web service invocation
        b = (Button) findViewById(R.id.button1);
        b1=(Button) findViewById(R.id.button2);
        //Display progress bar until web service invocation completes
        webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
        //Button Click Listener
        b.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //Check if text controls are not empty
                if (userNameET.getText().length() != 0 && userNameET.getText().toString() != "") {
                	if(passWordET.getText().length() != 0 && passWordET.getText().toString() != ""){
                		editTextUsername = userNameET.getText().toString();
                		editTextPassword = passWordET.getText().toString();
                		statusTV.setText("");
                		//Create instance for AsyncCallWS
                		AsyncCallWS task = new AsyncCallWS();
                		//Call execute 
                		task.execute();
                	}
                	//If Password text control is empty
                	else{
                		statusTV.setText("Please enter Password");
                	}
                //If Username text control is empty
                } else {
                    statusTV.setText("Please enter Username");
                }
            }
        });
        //Button1 Click Listener 
        b1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
				startActivity(i);
			}
		});
    }
    
    private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			loginStatus = WebService.invokeLoginWS(editTextUsername,editTextPassword,"authenticateUser");
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			Intent intObj = new Intent(MainActivity.this,HomeActivity.class);
			 intObj.putExtra("username", editTextUsername);
			//Error status is false
			if(!errored){
				//Based on Boolean value returned from WebService
				if(loginStatus){
					//Navigate to Home Screen
					 SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
					 
				     // Writing data to SharedPreferences
				     Editor editor = settings.edit();
				     editor.putString("key", editTextUsername);
				     editor.commit();
				 
					startActivity(intObj);
					finish();
				}else{
					//Set Error message
					statusTV.setText("Login Failed, try again");
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         SearchView searchView =
                 (SearchView) menu.findItem(R.id.search).getActionView();
         searchView.setSearchableInfo(
                 searchManager.getSearchableInfo(getComponentName()));

         return super.onCreateOptionsMenu(menu);
    }
}
