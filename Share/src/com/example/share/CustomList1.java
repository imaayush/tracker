package com.example.share;




import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CustomList1 extends ArrayAdapter<String> {
	
	public class Send extends Activity{
		
	}
	private final Activity context;
	private final String[] web;
	String p ;
	private final String circle ;
	String username ,circle1;
	boolean status;
	String[]s;
	public class ViewHolder {  
        TextView text;  
        Button button;  
    }
	ViewHolder viewHolder; 
	View rowView;
	public CustomList1(Activity context, String[] web ,String circle) {
		super(context, R.layout.row1, web);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.web=web;
		this.circle=circle;
		
		
	}
	
	public View getView(int position,View view, final ViewGroup parent){
		
	LayoutInflater inflater=context.getLayoutInflater();
	rowView=inflater.inflate(R.layout.row1, null,true);
	viewHolder = new ViewHolder(); 
	viewHolder.text=(TextView) rowView.findViewById(R.id.textView1);
	viewHolder.button = (Button) rowView.findViewById(R.id.button1);
	rowView.setTag(viewHolder); 
	
	 s=web[position].split(",");
	
	viewHolder.text.setText(s[0]);
	
	viewHolder.button.setText(s[1]);
	circle1=circle;
	
   
	
	final String temp = getItem(position); 
	viewHolder.button.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
		//String itemValue=(String) txtTitle.getText().toString();
			//Toast.makeText(getApplicationContext(), "Position: "+itemPosition+" values: "+itemValue, Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), " values: "+itemValue, Toast.LENGTH_SHORT).show();
			s = temp.split(",");
			AsyncCallWS task = new AsyncCallWS();
	     		//Call execute 
	     	task.execute();
			Intent zoom=new Intent(parent.getContext(), Circle.class);   
			zoom.putExtra("username", circle1);
			zoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			parent.getContext().startActivity(zoom);

		
		
		};
	});
	
	
	

		return rowView;
		

}
	  private class AsyncCallWS extends AsyncTask<String, Void, Void> {
			@Override
			protected Void doInBackground(String... params) {
				//Call Web Method
				//Toast.makeText(context,"fuck" , Toast.LENGTH_SHORT).show();
				status = WebService.invokeLoginWS(s[0],(s[1]+","+circle1),"checkcircle");
				
				return null;
			}

			
	  }
	}

