package com.example.share;

import java.util.ArrayList;

import android.app.ActionBar;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchResultsActivity extends Activity {
	TextView t1;
	ProgressBar webservicePG;
	String username,query;
	String[] web,date,temp,imageId;
	ArrayList<String> noteStatus =new ArrayList<String>();
	static boolean errored = false;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		 // get the action bar
       // ActionBar actionBar = getActionBar();
 
        // Enabling Back navigation on Action Bar icon
        //actionBar.setDisplayHomeAsUpEnabled(true);

	     SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
	     username = settings.getString("key", "");
		webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
		t1=(TextView) findViewById(R.id.textView1);
		  handleIntent(getIntent());
	}
	 @Override
	    protected void onNewIntent(Intent intent) {
	        setIntent(intent);
	        handleIntent(intent);
	    }
	 private void handleIntent(Intent intent) {
	        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	           query = intent.getStringExtra(SearchManager.QUERY);
	            
	            AsyncCallWS2 task = new AsyncCallWS2();
	     		//Call execute 
	     		task.execute();
	            
	          
	        }
	 
	    }
		private class AsyncCallWS2 extends AsyncTask<String, Void, Void> {
			@Override
			protected Void doInBackground(String... params) {
				//Call Web Method
				
				noteStatus = WebService.invokeNotiWS(query, "search");
				return null;
			}

			@Override
			//Once WebService returns response
			protected void onPostExecute(Void result) {
				//Make Progress Bar invisible
				
				webservicePG.setVisibility(View.INVISIBLE);
				web = new String[noteStatus.size()];
				date = new String[noteStatus.size()];
				temp= new String[noteStatus.size()];
				imageId =new String[noteStatus.size()];
				for(int i=0 ;i<noteStatus.size();i++){
					temp[i]=noteStatus.get(i);
					String[] str =temp[i].split(",");
					temp[i]=str[1]+","+str[3];
					web[i]=str[0];
					date[i]=str[2];
					imageId[i]=str[3];
				}
				//Error status is false
				if(!errored){
					//Based on Boolean value returned from WebService
					if(noteStatus!=null){
						
					     final ListView listView=(ListView) findViewById(R.id.listView1);
					       CustomList adapter =new CustomList(SearchResultsActivity.this,temp,imageId,web,date);
					        listView.setAdapter(adapter);
					        
					        listView.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, int position,
										long arg3) {
									// TODO Auto-generated method stub
									int itemPosition = position;
									String itemValue=(String) listView.getItemAtPosition(position);
									//Toast.makeText(getApplicationContext(), "Position: "+itemPosition+" values: "+itemValue, Toast.LENGTH_SHORT).show();
									Intent i = new Intent(getApplicationContext(),FileActivity.class);
									
									i.putExtra("fileid", itemValue);
									i.putExtra("username",  username);
									startActivity(i);
									finish();
								}
							});
						

						 
					}else{
						//Set Error message
						t1.setText(" Failed, try again");
					}
				//Error status is true	
				}else{
					t1.setText("Error occured in invoking webservice");
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
			 if(username==""){getMenuInflater().inflate(R.menu.menu1, menu);
			 SearchManager searchManager =
		                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		         SearchView searchView =
		                 (SearchView) menu.findItem(R.id.search).getActionView();
		         searchView.setSearchableInfo(
		                 searchManager.getSearchableInfo(getComponentName()));
			 }else{
		        getMenuInflater().inflate(R.menu.main, menu);
		        SearchManager searchManager =
		                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		         SearchView searchView =
		                 (SearchView) menu.findItem(R.id.search).getActionView();
		         searchView.setSearchableInfo(
		                 searchManager.getSearchableInfo(getComponentName()));
			 }
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

			public void home() {
				// TODO Auto-generated method stub
				Intent i=new Intent(SearchResultsActivity.this, HomeActivity.class);
				i.putExtra("username",  username);
				startActivity(i);
				finish();
			}

			public void logout() {
				// TODO Auto-generated method stub
				Intent i=new Intent(SearchResultsActivity.this, MainActivity.class);
				SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
				Editor editor = settings.edit();
			    editor.clear();
			    editor.commit();
				startActivity(i);
				finish();
			}
			public void setting(){
				Intent i=new Intent(SearchResultsActivity.this, Setting.class);
				i.putExtra("username",  username);

				startActivity(i);
				finish();
			}
			public void circle(){
				Intent i=new Intent(SearchResultsActivity.this, Circle.class);
				i.putExtra("username",  username);

				startActivity(i);
				
			}

}
