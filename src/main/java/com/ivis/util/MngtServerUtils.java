package com.ivis.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.ivis.service.ServerConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MngtServerUtils {

	String mngtServer = ServerConfig.mgmtServer;

	public JSONObject getCentralUnitDetails_1_0(int siteId) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder()
					.url(mngtServer+"/devices/getCentralUnitDetails_1_0?siteId="+siteId)
					.method("GET", null).build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();

			return new JSONObject(responseString);
		} catch (IOException e) {

			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}

	}

	public JSONObject getDeviceDetailsForSite_1_0(int siteId) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, "");
			Request request = new Request.Builder().url(
					mngtServer+"/devices/getDeviceDetailsForSite_1_0?siteId="+siteId)
					.method("GET", null).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();

			return new JSONObject(responseString);
		} catch (IOException e) {
			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public JSONObject getVerticalCounts_1_0() {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			Request request = new Request.Builder().url(
					mngtServer+"/General/getCounts_1_0")
					.method("GET", null).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			return new JSONObject(responseString);
		} catch (IOException e) {
			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}
	
	

	public JSONObject listCustomers_1_0() {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			Request request = new Request.Builder().url(
					mngtServer+"/customer/listCustomers_1_0")
					.method("GET", null).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			return new JSONObject(responseString);
		} catch (IOException e) {
			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public JSONObject listSites_1_0() {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			Request request = new Request.Builder().url(
					mngtServer+"/sites/listSites_1_0")
					.method("GET", null).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			return new JSONObject(responseString);
		} catch (IOException e) {
			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public JSONObject createCustomer_1_0(HashMap<String, Object> data) {
		/**
		 * TODO
		 * Replace url with the one in configuration
		 */
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/plain");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(data));
			Request request = new Request.Builder().url("http://usmgmt.iviscloud.net:777/mngtServer/customer/addCustomer_1_0")
					.method("POST", body).build();

			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			return new JSONObject(responseString);
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}
}
