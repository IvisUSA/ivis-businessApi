package com.ivis.tests;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ivis.ApplicationModels.Analysis;
import com.ivis.ApplicationModels.Services;
import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.service.ServerConfig;
import com.ivis.util.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BiAnalyticsReportsTest {
	public static String cpusApi = ServerConfig.cpusapi;
	public static String keycloakApi = ServerConfig.keycloakapi;
	public static String IvisBiApi = ServerConfig.ivisBiApi;

	public static void main(String[] args) {

		new BiAnalyticsReportsTest().getTrendsMobile((Integer) 0,null);
	}

	public Object getTrendsMobile(int accountId, String date) {
		try {
			Map<String, Object> bodyMap = new HashMap<>();
			OkHttpClient client = new OkHttpClient().newBuilder().build();

			bodyMap.put("id", 1);
			bodyMap.put("fieldid", 8);
			bodyMap.put("startdate", "2022-03-01");

			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));

			Request request = new Request.Builder().url(IvisBiApi + "/getTrendsMobile").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
//			String responseString2 = response.body().string();

//			System.out.println(responseString2);

			return new JSONArray(responseString).toList();
		} catch (Exception e) {
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
