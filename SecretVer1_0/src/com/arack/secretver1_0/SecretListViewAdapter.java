package com.arack.secretver1_0;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecretListViewAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	Context context;
	private String[] universities;
	
	public SecretListViewAdapter(Context context)
	{
		this.context = context;	
		this.mInflater = LayoutInflater.from(context);
		universities = this.context.getResources().getStringArray(R.array.universities);
	}


	@Override
	public int getCount() {
		
		return AllSecretFragment.secretsList.size();
		
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SecretViewHolder holder = null;
		if (convertView == null)
		{
		      holder=new SecretViewHolder(); 
		          
		      convertView = mInflater.inflate(R.layout.listview_all_secret, null);
		      holder.content = (TextView)convertView.findViewById(R.id.secretListViewContentTextView);
		      holder.createDate = (TextView)convertView.findViewById(R.id.secretListViewCreateTimeTextView);
		      holder.category = (TextView)convertView.findViewById(R.id.secretListViewCatgeoryTextView);
		     
		 
			 convertView.setTag(holder);
		 }
		else 
		{
			            
			 holder = (SecretViewHolder)convertView.getTag();
		}
		             
    
		holder.content.setText((String)AllSecretFragment.secretsList.get(position).get("content"));
		holder.createDate.setText((String)AllSecretFragment.secretsList.get(position).get("createTime"));
		
		int categoryPos = Integer.parseInt((String)AllSecretFragment.secretsList.get(position).get("category"));
		holder.category.setText(universities[categoryPos-1]);
		
		convertView.setTag(R.id.secretListViewContent,(String)AllSecretFragment.secretsList.get(position).get("content") );
		convertView.setTag(R.id.secretListViewID,(String)AllSecretFragment.secretsList.get(position).get("id") );
		convertView.setOnClickListener(convertViewOnClickListener);
		
		
		// TODO Auto-generated method stub
		return convertView;
	}
	
	
	public OnClickListener convertViewOnClickListener = new OnClickListener()
	{

		

		@Override
		public void onClick(View v) {
			
			System.out.println("touch" + v.getTag(R.id.secretListViewID));
			Intent intent = new Intent( context, InsideSecretActivity.class);
			intent.putExtra("content", v.getTag(R.id.secretListViewContent).toString());
			intent.putExtra("id", v.getTag(R.id.secretListViewID).toString());
			context.startActivity(intent);
			
		}
		
	};

}
