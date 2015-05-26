package com.example.share;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class ChangePassward extends Activity {
	static boolean errored = false;
	EditText old ,newpass;
	TextView status;
	ProgressBar  webservicePG;
	String username,password,con;
	Button b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change);
		old = (EditText) findViewById(R.id.editText2);
		newpass=(EditText) findViewById(R.id.editText3);
		webservicePG=(ProgressBar) findViewById(R.id.progressBar2);
		status = (TextView) findViewById(R.id.tv_result1);
		Intent intObj = getIntent();
	    username = intObj.getStringExtra("username").toString();
	    b = (Button) findViewById(R.id.button1);
	    
        
	    b.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				password=newpass.getText().toString();
				con=old.getText().toString();
				if(password.equals(con)&&password!=null){
				   AsyncCallWS task = new AsyncCallWS();
		     		//Call execute 
		     		task.execute();}
				else{
					 Toast.makeText(getApplicationContext(),"Password not change",Toast.LENGTH_SHORT).show();
				}
			}
		});
			
	}
	
	 private class AsyncCallWS extends AsyncTask<String, Void, Void> {
			@Override
			protected Void doInBackground(String... params) {
				//Call Web Method
				errored = WebService.invokeLoginWS(username,password,"changePassword");
				return null;
			}

			@Override
			//Once WebService returns response
			protected void onPostExecute(Void result) {
				//Make Progress Bar invisible
				webservicePG.setVisibility(View.INVISIBLE);
			
				//Error status is false
				if(errored){
					//Based on Boolean value returned from WebService
					 Toast.makeText(getApplicationContext(),"Password Changed",Toast.LENGTH_SHORT).show();
				//Error status is true	
				}else{
					status.setText("Error occured in invoking webservice");
				}
				//Re-initialize Error Status to False
				
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
	        getMenuInflater().inflate(R.menu.main, menu);
	        SearchManager searchManager =
	                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	         SearchView searchView =
	                 (SearchView) menu.findItem(R.id.search).getActionView();
	         searchView.setSearchableInfo(
	                 searchManager.getSearchableInfo(getComponentName()));

	         return super.onCreateOptionsMenu(menu);
	    }


		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case R.id.Home:
				home();
				return true;
			case R.id.action_settings:
				setting();
				return true;
			case R.id.circle:
				circle();
				return true;
			case R.id.Logout:
				
				logout();
				return true;
			

			default:
			return super.onOptionsItemSelected(item);
			
		}
	    

	}
		public void home(){
			Intent i=new Intent(ChangePassward.this, HomeActivity.class);
			i.putExtra("username",  username);

			startActivity(i);
			finish();
		}

		public void logout() {
			// TODO Auto-generated method stub
			Intent i=new Intent(ChangePassward.this, MainActivity.class);
			SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
			Editor editor = settings.edit();
		    editor.clear();
		    editor.commit();
			startActivity(i);
			finish();
		}
		public void setting(){
			Intent i=new Intent(ChangePassward.this, Setting.class);
			i.putExtra("username",  username);

			startActivity(i);
			finish();
			
		}
		public void circle(){
			Intent i=new Intent(ChangePassward.this, Circle.class);
			i.putExtra("username",  username);

			startActivity(i);
			finish();
		}
	

}
