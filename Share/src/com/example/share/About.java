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
import android.widget.ListView;
import android.widget.SearchView;

public class About extends Activity{
	String username;
	String[] temp={"Aayush Tiwari" ,"Sudhakar Singh" ,"Deepesh kumar"};
	String[]web={"Aayush Tiwari" ,"Sudhakar Singh" ,"Deepesh kumar"};
	String[]date={"2011ecs18","2011ec12","2011ecs05"};
	String[]imageId={"Aayush","sud","deepu"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		Intent intObj = getIntent();
		
	     username = intObj.getStringExtra("username");
	     
	     final ListView listView=(ListView) findViewById(R.id.listView1);
	       CustomList adapter =new CustomList(About.this,temp,imageId,web,date);
	        listView.setAdapter(adapter);
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
		Intent i=new Intent(About.this, MainActivity.class);
		SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
		Editor editor = settings.edit();
	    editor.clear();
	    editor.commit();
		startActivity(i);
		finish();
	}
	public void setting(){
		Intent i=new Intent(About.this, Setting.class);
		i.putExtra("username",  username);

		startActivity(i);
		finish();
	}
	public void home(){
		Intent i=new Intent(About.this, HomeActivity.class);
		i.putExtra("username",  username);
	
		startActivity(i);
		finish();
		
	}
	public void circle(){
		Intent i=new Intent(About.this, Circle.class);
		i.putExtra("username",  username);

		startActivity(i);
		finish();
	}

}
