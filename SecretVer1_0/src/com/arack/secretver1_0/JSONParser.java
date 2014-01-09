package com.arack.secretver1_0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser {

    static InputStream is = null;
    static JSONArray jArray = null;
    static String json = "";

    // constructor
    public JSONParser() {

    }

    // function get json from url
    // by making HTTP POST or GET mehtod
    public JSONArray makeHttpRequest(String url, String method,
            List<NameValuePair> params ) {

        // Making HTTP request
        try {

            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                System.out.println("inside the parser");
                //content = "{\"SQL\":\"INSERT INTO  secrets VALUES (NULL ,  '9',  '¤¤¤å	' , NOW() ,  '0',  '0', NULL ,  '0',  '0');\"}";
           
                //StringEntity se = new StringEntity(content, "UTF-8");
               // JSONObject jo = new JSONObject();
             //   try{
             //   jo.put("Content", content);
             //   }
              ///  catch ( Exception e)
              //  {
              //  	Log.e("json", "error");
              //  }
              //  StringEntity se = new StringEntity(jo.toString(), "UTF-8");
                //se.setContentType("application/json");
                //httpPost.setEntity(se);
                //jo.
                httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
                //Log.e("HTTP",jo.toString() );

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
     

            }else if(method == "GET"){
                // request method is GET
            	/*
                DefaultHttpClient httpClient = new DefaultHttpClient();
                //String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);

                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                */
            	HttpClient httpclient = new DefaultHttpClient();
        		HttpGet httpget = new HttpGet(url);
        		HttpResponse response = httpclient.execute(httpget);	
        		HttpEntity entity = response.getEntity();
        		is = entity.getContent();
            }           

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "utf-8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();

           // if (sb.toString().charAt(0) != '[' )
            	//return null;
            json = sb.toString();
          
         
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
        		//Log.e("jarrayContent", jArray.toString());
        		jArray = new JSONArray(json);

        } catch (JSONException e) {
            Log.e("JSON Parser haha", "Error parsing data " + e.toString());
            try{
            JSONObject jo = new JSONObject(json);
            jArray = new JSONArray();
            jArray.put(jo);
            }
            catch ( JSONException ee)
            {
            	return null;
            }
            
        }

        // return JSON String
        return jArray;

    }
}
