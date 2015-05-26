package com.example.share;


import java.util.Date;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomList extends ArrayAdapter<String>{

	private final Activity context;
	private final String[] web;
	private final String[] web1;
	private final String[] imageId;
	private final String[] date;
	public CustomList(Activity context, String[] web, String[] imageId,String[] web1,String[] date) {
		super(context, R.layout.row, web);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.web=web;
		this.web1=web1;
		this.imageId=imageId;
		this.date=date;
	}
	
	public View getView(int position,View view, ViewGroup parent){
	LayoutInflater inflater=context.getLayoutInflater();
	View rowView=inflater.inflate(R.layout.row, null,true);
	TextView txtTitle=(TextView) rowView.findViewById(R.id.textView1);
	TextView txtTitle1=(TextView) rowView.findViewById(R.id.textView2);
	TextView txtTitle2=(TextView) rowView.findViewById(R.id.textView3);
	ImageView imageView= (ImageView) rowView.findViewById(R.id.imageView1);
	txtTitle1.setText(web1[position]);
	txtTitle.setText(web[position]);
	txtTitle2.setText(date[position].toString());
	txtTitle.setVisibility(View.GONE);
	
	if(imageId[position].equalsIgnoreCase("video")){Integer imageId1=R.drawable.video; imageView.setImageResource(imageId1);  }
	else if(imageId[position].equalsIgnoreCase("movie")){Integer imageId1=R.drawable.movie; imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("software")){Integer imageId1=R.drawable.software; imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("music")){ Integer imageId1=R.drawable.music; imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("sport")){ Integer imageId1=R.drawable.sport;imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("game")){  Integer imageId1=R.drawable.game; imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("project")){  Integer imageId1=R.drawable.project;imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("paper")){  Integer imageId1=R.drawable.paper;imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("Aayush")){  Integer imageId1=R.drawable.aayush;imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("sud")){  Integer imageId1=R.drawable.sud;imageView.setImageResource(imageId1);}
	else if(imageId[position].equalsIgnoreCase("deepu")){  Integer imageId1=R.drawable.deep;imageView.setImageResource(imageId1);}
	else { Integer imageId1=R.drawable.show; imageView.setImageResource(imageId1);}

	
	   

		return rowView;
		
	}
	
	
	


	
	

}
