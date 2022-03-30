package com.ivis.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

public class util {

	public static String readUrlData(String serverUrl) {

	    StringBuilder content = new StringBuilder();

	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	    	
	      // create a url object
	      URL url = new URL(serverUrl);

	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();

	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

	      String line;

	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	        content.append(line + "\n");
	      }
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      return null;
	    }
	    return content.toString();
	  
	}
	
	
}
