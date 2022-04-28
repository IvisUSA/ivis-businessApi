package com.ivis.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.ivis.service.ServerConfig;

public class KeycloakUtils {

	
	public static boolean verifyaccesstoken(String username,String accessToken)
	{
		try {
			URL url = new URL(ServerConfig.keycloakapi+"/verifyAccessToken");
			HttpURLConnection http = (HttpURLConnection)url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\n    \"userName\":\""+username+"\",\n    \"accessToken\": \""+accessToken+"\"}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
			
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1; ) {
			    sb.append((char) ch);
			}
			http.disconnect();
			
			System.out.println(sb.toString());
			JSONObject json2 = new JSONObject(sb.toString());
			
			
			
			
			if(json2.get("Status").equals("True"))
				return true;
			else
				return false;
			
		}
		catch(Exception e)
		{
			System.out.println(e);
			return false;
		}
	}
	
}
