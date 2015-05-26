package com.example.share;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class Help extends Activity {
	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);
		Intent intObj = getIntent();
		
	     username = intObj.getStringExtra("username");
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
			Intent i=new Intent(Help.this, MainActivity.class);
			SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
			Editor editor = settings.edit();
		    editor.clear();
		    editor.commit();
			startActivity(i);
			finish();
		}
		public void setting(){
			Intent i=new Intent(Help.this, Setting.class);
			i.putExtra("username",  username);

			startActivity(i);
			
		}
		public void circle(){
			Intent i=new Intent(Help.this, Circle.class);
			i.putExtra("username",  username);

			startActivity(i);
			
		}
}
