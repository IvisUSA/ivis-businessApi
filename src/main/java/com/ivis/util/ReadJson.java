package com.ivis.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class ReadJson {

	public String Read(Reader re) throws IOException {     // class Declaration
	    StringBuilder str = new StringBuilder();     // To Store Url Data In String.
	    int temp;
	    do {

	      temp = re.read();       //reading Charcter By Chracter.
	      str.append((char) temp);

	    } while (temp != -1);        
	    //  re.read() return -1 when there is end of buffer , data or end of file. 

	    return str.toString();

	}
	public JSONObject readJsonFromUrl(String link) throws IOException, JSONException {
	    InputStream input = new URL(link).openStream();
	    // Input Stream Object To Start Streaming.
	    try {                                 // try catch for checked exception
	      BufferedReader re = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));  
	    // Buffer Reading In UTF-8  
	      String Text = Read(re);         // Handy Method To Read Data From BufferReader
	      JSONObject json = new JSONObject(Text);    //Creating A JSON 
	      return json;    // Returning JSON
	    } catch (Exception e) {
	      return null;
	    } finally {
	      input.close();
	    }
	}
	public JSONArray readJsonArrayFromUrl(String link) throws IOException, JSONException {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(5, TimeUnit.SECONDS)
					  .build();
					Request request = new Request.Builder()
					  .url(link)
					  .method("GET", null)
					  .build();
					Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			
			JSONArray responseJson = new JSONArray(responseString);
			

			
			return responseJson;
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return new JSONArray();
		}
	}
}
