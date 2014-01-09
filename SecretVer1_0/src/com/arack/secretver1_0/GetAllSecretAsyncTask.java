package com.arack.secretver1_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;





import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


public class GetAllSecretAsyncTask extends AsyncTask<String,Void,ArrayList<Map<String, Object>>> {
	
	private ArrayList<Map<String, Object>> secretsList = new ArrayList<Map<String, Object>>() ;
	JSONArray jArray;
	Context act;
	
	private ProgressDialog pdia;
	
	public GetAllSecretAsyncTask ( Context act )
	{
		super();
		this.act = act;
		
	}
	protected void onPreExecute( ){ 
		   super.onPreExecute();
		      //  pdia = new ProgressDialog(act);
		       // pdia.setMessage("Loading...");
		      //  pdia.show();
		        
		}
	
	
	
	protected void onPostExecute(  ArrayList<Map<String, Object>> result) 
	{
		//pdia.dismiss();
		//SecretMainActivity.secretsList.addAll(result);
		//Message msg = new Message();  
       // msg.what = 1;

        //SecretMainActivity.handler.sendMessage(msg);
	}


	@Override
	protected ArrayList<Map<String, Object>> doInBackground(String... params) {
		
		String startingNum = params[0];
		String url_get_all_secrets = params[1];
		
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();
        
        param.add(new BasicNameValuePair("startingPoint",startingNum));
        
		jArray = new JSONParser().makeHttpRequest(url_get_all_secrets,
                "POST", param);
		
		//	/
		try
		{
			
			for ( int i = 0; i < jArray.length();i++)
			{
				JSONObject json = jArray.getJSONObject(i);
				Map<String,Object> map = new HashMap<String,Object>();
				//if (json.getString("S_Content").length() < 20 )
					map.put("content",json.getString("S_Content"));
				//else
				//	map.put("content",json.getString("S_Content").substring(0, 20) );
					
				map.put("createTime",json.getString("S_CreateTime"));
				map.put("id",json.getString("S_ID"));
				map.put("category",json.getString("S_SG_ID"));
				secretsList.add(map);
			}
			
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
		
		System.out.println(jArray.toString());
		// TODO Auto-generated method stub
		return secretsList;
	}

}
