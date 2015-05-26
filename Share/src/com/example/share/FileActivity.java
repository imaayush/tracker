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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class FileActivity  extends Activity{
	String fileid;
	TextView statusTV ,name,like ,recommended ,view,size,cat,des;
	Button b;
	String re,username;
	ImageView image;
	ProgressBar webservicePG;
	Integer imageId=R.drawable.movie;
	static boolean errored = false;
	String[] s =new String[2];
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file);
		Intent intObj = getIntent();
		username=intObj.getStringExtra("username");
	     fileid = intObj.getStringExtra("fileid");
	     s=fileid.toString().split(",");
	     image=(ImageView) findViewById(R.id.imageView1);
	     statusTV = (TextView) findViewById(R.id.tv_result);
	     name = (TextView) findViewById(R.id.textView1);
	     like = (TextView) findViewById(R.id.textView3);
	     size = (TextView) findViewById(R.id.textView2);
	     recommended = (TextView) findViewById(R.id.textView4);
	     view = (TextView) findViewById(R.id.textView5);
	     cat = (TextView) findViewById(R.id.textView6);
	     des = (TextView) findViewById(R.id.textView8);
	    
	 		 String tag = s[1];
			 	if(tag.equalsIgnoreCase("video")){Integer imageId1=R.drawable.video; image.setImageResource(imageId1);  }
				else if(tag.equalsIgnoreCase("movie")){Integer imageId1=R.drawable.movie; image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("software")){Integer imageId1=R.drawable.software; image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("music")){ Integer imageId1=R.drawable.music; image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("sport")){ Integer imageId1=R.drawable.sport;image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("game")){  Integer imageId1=R.drawable.game; image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("project")){  Integer imageId1=R.drawable.project;image.setImageResource(imageId1);}
				else if(tag.equalsIgnoreCase("paper")){  Integer imageId1=R.drawable.paper;image.setImageResource(imageId1);}
				else { Integer imageId1=R.drawable.show; image.setImageResource(imageId1);}
	     
	 	 webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
	
	     b = (Button) findViewById(R.id.button1);
	    
	     b.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					
					AsyncCallWS task = new AsyncCallWS();
		     		//Call execute 
		     		task.execute();
				}
			});
	     AsyncCallWS1 task1 = new AsyncCallWS1();
	 		//Call execute 
	 		task1.execute();
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			
			re = WebService.invokeFileWS(fileid.toString(), "download");
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			
			//Error status is false
			if(!errored){
				//Based on Boolean value returned from WebService
				if(re!=null){
					
				
					 Toast.makeText(getApplicationContext(),"Download successful",Toast.LENGTH_SHORT).show();
					 
				}else{
					//Set Error message
					statusTV.setText("Register Failed, try again");
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
	
	
	//////
	private class AsyncCallWS1 extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			//Call Web Method
			
			re = WebService.invokeFileViewWS(s[0].toString(), "fileView");
			return null;
		}

		@Override
		//Once WebService returns response
		protected void onPostExecute(Void result) {
			//Make Progress Bar invisible
			webservicePG.setVisibility(View.INVISIBLE);
			
			//Error status is false
			if(!errored){
				//Based on Boolean value returned from WebService
				if(re!=null){
					String[] temp= re.split(",");
					 name.setText(temp[0].toString());
					 cat.setText("Tag : "+temp[1].toString());
					 size.setText("Size : "+temp[8].toString());
					 like.setText("Likes : "+temp[5].toString());
					 view.setText("Views : "+temp[4].toString());
					 des.setText(temp[2].toString());
					 recommended.setText("Recommended: "+temp[6].toString());
					
				}else{
					//Set Error message
					statusTV.setText("file view Failed, try again");
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
		 if(username.equals("") ||username.equals(null)){getMenuInflater().inflate(R.menu.menu1, menu);
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


		public void logout() {
			// TODO Auto-generated method stub
			Intent i=new Intent(FileActivity.this, MainActivity.class);
			SharedPreferences settings = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
			Editor editor = settings.edit();
		    editor.clear();
		    editor.commit();
			startActivity(i);
			finish();
		}
		public void home(){
			Intent i=new Intent(FileActivity.this, HomeActivity.class);
			i.putExtra("username",  username);
			
			startActivity(i);
			finish();
		}
		public void setting(){
			Intent i=new Intent(FileActivity.this, Setting.class);
			i.putExtra("username",  username);

			startActivity(i);
			finish();
		}
		public void circle(){
			Intent i=new Intent(FileActivity.this, Circle.class);
			i.putExtra("username",  username);

			startActivity(i);
			finish();
		}
	
	

}
