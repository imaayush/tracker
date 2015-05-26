package com.example.share;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Circle extends Activity {
	String username;
	ArrayList<String> circle=new ArrayList<String>();
	ArrayAdapter<String> adp;
	TextView status;
	ProgressBar webservicePG;
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle);
		 Intent intObj = getIntent();
	     username = intObj.getStringExtra("username").toString();
	     webservicePG =(ProgressBar) findViewById(R.id.progressBar2);
	     status=(TextView) findViewById(R.id.tv_result);
	     AsyncCallWS task = new AsyncCallWS();
  		//Call execute 
  		task.execute();
		
	}
	
	

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			
			circle= WebService.invokeNotiWS(username, "circle");
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			
			
			//Error status is false
			if(circle!=null){
				//Based on Boolean value returned from WebService
				String[]temp=new String[circle.size()];
				for(int i=0 ;i<circle.size();i++)
				{
					temp[i]=circle.get(i);
				}
					
				    //lv=(ListView) findViewById(R.id.listView1);
				 	//adp=new ArrayAdapter<String>(Circle.this,android.R.layout.simple_list_item_1,temp);
					//lv.setAdapter(adp);
				   final ListView listView=(ListView) findViewById(R.id.listView1);
			       CustomList1 adapter =new CustomList1(Circle.this,temp,username);
			        listView.setAdapter(adapter);
			        listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position,
								long arg3) {
							// TODO Auto-generated method stub
							//int itemPosition = position;
							//String itemValue=(String) listView.getItemAtPosition(position);
							//Toast.makeText(getApplicationContext(), "Position: "+itemPosition+" values: "+itemValue, Toast.LENGTH_SHORT).show();
							Intent i = new Intent(getApplicationContext(),HomeActivity.class);
							//i.putExtra("fileid", itemValue);
							i.putExtra("username",  username);
							startActivity(i);
						
						}
					});
					

					 
				}else{
					//Set Error message
					status.setText(" Failed, try again");
				}
			//Error status is true	
			}
		
	  
       @Override
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


	public void logout() {
		// TODO Auto-generated method stub
		Intent i=new Intent(Circle.this, MainActivity.class);
		SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
		Editor editor = settings.edit();
	    editor.clear();
	    editor.commit();
		startActivity(i);
		finish();
	}
	public void home(){
		Intent i=new Intent(Circle.this, HomeActivity.class);
		i.putExtra("username",  username);
		
		startActivity(i);
		finish();
	}
	public void setting(){
		Intent i=new Intent(Circle.this, Setting.class);
		i.putExtra("username",  username);

		startActivity(i);
		finish();
	}
	public void circle(){
		Intent i=new Intent(Circle.this, Circle.class);
		i.putExtra("username",  username);

		startActivity(i);
		finish();
	}

	}	


