package com.example.share;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;

public class Setting extends Activity {
	String str[]={"Change Password" ,"About As","Help","Website"};
	ArrayAdapter<String> adp;
	String username;
	ListView lv ;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.setting);
	lv=(ListView) findViewById(R.id.listView1);
	adp=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,str);
	lv.setAdapter(adp);
	
	 Intent intObj = getIntent();
		
     username = intObj.getStringExtra("username");
   lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			String st =  (String) lv.getItemAtPosition(position);
			if(st=="About As"){
				
				Intent i=new Intent(Setting.this, About.class);
				i.putExtra("username",  username);
				startActivity(i);
				finish();
			}else if(st=="Change Password"){
			
			Intent i=new Intent(Setting.this, ChangePassward.class);
			i.putExtra("username",  username);
			startActivity(i);
			finish();
			}else if(st=="Help"){
				Intent i=new Intent(Setting.this, Help.class);
				i.putExtra("username",  username);
				startActivity(i);
				finish();
			}else{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(intent);
			}
		
		}
	});
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
	Intent i=new Intent(Setting.this, HomeActivity.class);
	i.putExtra("username",  username);

	startActivity(i);
	finish();
	
}
public void logout() {
	// TODO Auto-generated method stub
	Intent i=new Intent(Setting.this, MainActivity.class);
	SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
	Editor editor = settings.edit();
    editor.clear();
    editor.commit();
	startActivity(i);
	finish();
}
public void setting(){
	Intent i=new Intent(Setting.this, Setting.class);
	i.putExtra("username",  username);

	startActivity(i);
	finish();
}
public void circle(){
	Intent i=new Intent(Setting.this, Circle.class);
	i.putExtra("username",  username);

	startActivity(i);
	finish();

}
}
