package com.arack.secretver1_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import android.os.AsyncTask;

public class AddSecretAsyncTask extends AsyncTask<String,Void,Void> {

	@Override
	protected Void doInBackground(String... params) {
		int category = Integer.parseInt(params[0]) + 1;
		String content = params[1];
		String url_add_secret = params[2];
		
		List<NameValuePair> param = new ArrayList<NameValuePair>();
		
		param.add(new BasicNameValuePair("Content",content));
		param.add(new BasicNameValuePair("SGID",String.valueOf(category)));
		JSONArray jarray = new JSONParser().makeHttpRequest(url_add_secret, "POST", param);
		System.out.println(jarray.toString());
		// TODO Auto-generated method stub
		return null;
	}

	

}
