package com.ivis.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.ivis.ApplicationModels.UserMgmtUserModel;
import com.ivis.service.ServerConfig;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class KeycloakUtils {

	private static final String keycloakApi = ServerConfig.keycloakapi;

	public Map<String, Object> getUidForUser(UserMgmtUserModel input) {
		try {
			// KeycloakApp getUidForUser
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();

			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("email", input.getEmail());
			bodyMap.put("access_token", input.getAccess_token());
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/getUidForUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp getUidForUser : \n" + responseString);

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	/**
	 * .
	 * 
	 * @author Deepika
	 * @param input
	 * @return
	 */
	public Map<String, Object> createUser(HashMap<String, Object> input) {
		try {
			// KeycloakApp create user api

			HashMap bodyMap = input;

			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/createUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp create user api : \n" + responseString);
			JSONObject responseJsonObj = new JSONObject(responseString);
			return responseJsonObj.toMap();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public Map<String, Object> deleteUser(UserMgmtUserModel input, String uid) {
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();
			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("uid", uid);
			bodyMap.put("access_token", input.getAccess_token());
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/deleteUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp deleteUser : \n" + responseString);

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();

		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public Map<String, Object> updateUser(HashMap<String, Object> input) {
		try {

			HashMap bodyMap = input;

			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/updateUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp updateUser : \n" + responseString + "\n\n");

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();

		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public Map<String, Object> getUser(HashMap<String, Object> input) {
		try {

			HashMap bodyMap = input;
			
			
			
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
		
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/getUserByUserNameOrEmail").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			
			System.out.println("Response from KeycloakApp getUser : \n" + responseString + "\n\n");

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public static boolean verifyaccesstoken(String username, String accessToken) {
		try {
			URL url = new URL(ServerConfig.keycloakapi + "/verifyAccessToken");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\n    \"userName\":\"" + username + "\",\n    \"accessToken\": \"" + accessToken + "\"}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);

			System.out.println(http.getResponseCode() + " " + http.getResponseMessage());

			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			http.disconnect();

			System.out.println(sb.toString());
			JSONObject json2 = new JSONObject(sb.toString());

			if (json2.get("Status").equals("True"))
				return true;
			else
				return false;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public Map<String, Object> forgotPassword(UserMgmtUserModel input, String uid) {
		try {
			// KeycloakApp getUidForUser
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();
			bodyMap.put("email", input.getEmail());
			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("uid", uid);
			bodyMap.put("access_token", input.getAccess_token());
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/forgotPassword").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp forgotPassword : \n" + responseString + "\n\n");

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public Map<String, Object> changePassword(UserMgmtUserModel input, String uid) {
		try {
			// KeycloakApp getUidForUser
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();
			bodyMap.put("password", input.getPassword());
			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("uid", uid);
			bodyMap.put("access_token", input.getAccess_token());
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/changePassword").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();

			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp changePassword : \n" + responseString + "\n\n");

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();
		} catch (IOException e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}
	}

	public Map<String, Object> disableUser(UserMgmtUserModel input, String uid) {

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();
			bodyMap.put("email", input.getEmail());
			bodyMap.put("firstName", input.getEmail());
			bodyMap.put("lastName", input.getEmail());
			bodyMap.put("username", input.getEmail());

			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("enabled", "T");
			bodyMap.put("uid", uid);
			bodyMap.put("access_token", input.getAccess_token());

			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(keycloakApi + "/disableUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			System.out.println("Response from KeycloakApp disableUser : \n" + responseString + "\n\n");

			JSONObject responseJsonObj = new JSONObject(responseString);

			return responseJsonObj.toMap();

		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, Object>() {
				{
					put("Status", "Failed");
					put("Message", "Exception occured : " + e);
				}
			};
		}

	}
}
