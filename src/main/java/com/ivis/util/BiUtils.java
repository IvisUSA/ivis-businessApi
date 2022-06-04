package com.ivis.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.ivis.service.ServerConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BiUtils {
	public static String cpusApi = ServerConfig.cpusapi;
	public static String keycloakApi = ServerConfig.keycloakapi;
	public static String IvisBiApi = ServerConfig.ivisBiApi;

	public Object getTrendsMobile(int accountId, Date date, int fieldId) {
		try {
			Map<String, Object> bodyMap = new HashMap<>();
			OkHttpClient client = new OkHttpClient().newBuilder().build();

			bodyMap.put("id", new CpusUtils().GetClientIdFromSiteId(accountId));
			bodyMap.put("fieldid", fieldId);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
			String strDate = dateFormat.format(date);
			bodyMap.put("startdate", strDate);

			System.out.println("Request Body : " + new Gson().toJson(bodyMap));
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));

			Request request = new Request.Builder().url(IvisBiApi + "/getTrendsMobile").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
//			String responseString2 = response.body().string();

			System.out.println(responseString);
//			System.out.println(responseString2);

			
			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "List generated successfully");
					put("AnalyticsTrends", new JSONArray(responseString).toList());

				}
			};
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");
				}
			};
		}

	}
	public Object getNotWorkingDays(int siteId,int year)
	{
		try {
			OkHttpClient client = new OkHttpClient().newBuilder()
					  .build();
					MediaType mediaType = MediaType.parse("text/plain");
					RequestBody body = RequestBody.create(mediaType, "");
					Request request = new Request.Builder()
					  .url(IvisBiApi + "/getNonWorkingDays?customerSiteId="+new CpusUtils().GetClientIdFromSiteId(siteId)+"&year="+year)
					  .method("GET", null)
					  .build();
					Response response = client.newCall(request).execute();
			String responseString = response.body().string();
//			String responseString2 = response.body().string();

//			System.out.println(responseString2);

			
			return new HashMap<String, Object>() {
				{
					put("Status", "Success");
					put("Message", "List generated successfully");
					put("NotWorkingDaysList", new JSONArray(responseString).toList());

				}
			};
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return new HashMap<String, String>() {
				{
					put("Status", "Failed");
					put("Message", "Failed processing request");
				}
			};
		}
	}
}
