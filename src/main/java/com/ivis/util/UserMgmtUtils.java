package com.ivis.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class UserMgmtUtils {

	
	
	
	
	private static final String userMgmtApi = ServerConfig.userMgmt;

	public Map<String, Object> userVerificationApi(UserMgmtUserModel input)
	{
		try {
		//Calling userVerificationApi
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		Map<String,Object> bodyMap = new HashMap<>();

		bodyMap.put("email", input.getEmail());
		bodyMap.put("username", input.getUserName());

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
		Request request = new Request.Builder()
				.url(userMgmtApi+"/verifyUser")
				.method("POST", body)
				.addHeader("Content-Type", "application/json")
				.build();
		Response response;
		
			response = client.newCall(request).execute();
		
		String responseString = response.body().string();
		System.out.println("Response from /verifyUser : \n"+responseString+"\n\n");
		JSONObject responseJsonObj = new JSONObject(responseString);
		return responseJsonObj.toMap();} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<String,Object>(){{
				put("Status","Failed");
				put("Message","Exception occured : "+e);
			}};
		}
	}
	
	public Map<String, Object> createUser(UserMgmtUserModel input,String uid)
	{
		try {
			// userMgmt/createUser
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("application/json");
			HashMap bodyMap = new HashMap<>();

			bodyMap.put("realmId", input.getRealm());
			bodyMap.put("email", input.getEmail());
			bodyMap.put("access_token", input.getAccess_token());
			bodyMap.put("firstName", input.getFirstName());
			bodyMap.put("lastName", input.getLastName());
			bodyMap.put("address_line1", input.getAddress_line1());
			bodyMap.put("address_line2", input.getAddress_Line2());
			bodyMap.put("district", input.getDistrict());
			bodyMap.put("state", input.getState());
			bodyMap.put("pin", input.getPin());
			bodyMap.put("contactNo1", input.getContactNumber_1());
			bodyMap.put("contactNo2", input.getContactNumber_2());
			bodyMap.put("gender", input.getGender());
			bodyMap.put("active", input.getActive());
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			String strDate = dateFormat.format(date);
			bodyMap.put("createdTime", strDate);

			bodyMap.put("createdBy", input.getCalling_user_name());
			bodyMap.put("modifiedTime", null);
			bodyMap.put("modifiedBy", null);
			bodyMap.put("employee", input.getEmployee());
			bodyMap.put("empId", input.getEmployeeId());
			bodyMap.put("singnUp", null);
			bodyMap.put("username", input.getUserName());
			bodyMap.put("enabled", "T");
			JSONObject responseJsonObj;
			bodyMap.put("uid", uid);
			bodyMap.put("user_Country", null);
			RequestBody body = RequestBody.create(mediaType, new Gson().toJson(bodyMap));
			Request request = new Request.Builder().url(userMgmtApi + "/userMgmt/createUser").method("POST", body)
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseString = response.body().string();
			responseJsonObj = new JSONObject(responseString);
			return responseJsonObj.toMap();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new HashMap<String,Object>(){{
				put("Status","Failed");
				put("Message","Exception occured : "+e);
			}};
		}
	}


	
}
