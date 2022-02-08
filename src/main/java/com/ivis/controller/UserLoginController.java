package com.ivis.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ivis.ApplicationModels.UserLogin;
import com.ivis.service.IvisService;

@Controller
@CrossOrigin
@RestController
@RequestMapping("/login")
public class UserLoginController {

	@Autowired
	IvisService ivis;

	@PostMapping(value = "/user")
	public Map<String, String> userLogin(@RequestParam("grant_type") String grant_type,
			@RequestParam("client_id") String client_id, @RequestParam("client_secret") String client_secret,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("realm") String realm) {
		UserLogin user = new UserLogin();
		user.setClient_id(client_id);
		user.setClient_secret(client_secret);
		user.setGrant_type(grant_type);
		user.setPassword(password);
		user.setRealm(realm);
		user.setUsername(username);
		Map<String, String> response = this.ivis.userLogin(user);
		return response;
	}
//	String data = "{\"password\": \""+userdata.get("password")+"\",\"userName\": \""+userdata.get("userName")+"\",\"calling_System_Detail\":\""+userdata.get("calling_System_Detail")+"\"}";

	@PostMapping(value = "/login")
	public Object userLogin2(@RequestBody HashMap<String, String> userdata) {
		JSONObject json = new JSONObject();
		try {
			URL url = new URL("http://smstaging.iviscloud.net:8090/keycloakApp/login");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\"password\": \"" + userdata.get("password") + "\",\"userName\": \""
					+ userdata.get("userName") + "\",\"calling_System_Detail\":\""
					+ userdata.get("calling_System_Detail") + "\"}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONObject(sb.toString());

			http.disconnect();

			return json.toMap();

		} catch (Exception e) {
			System.err.println(e);

			return null;
		}
	}
	
	@PostMapping(value = "/logout")
	public Object userLogout2(@RequestBody HashMap<String, String> userdata) {
		JSONObject json = new JSONObject();
		try {
			URL url = new URL("http://smstaging.iviscloud.net:8090/keycloakApp/logout");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\"AccessToken\": \"" + userdata.get("AccessToken") + "\",\"userName\": \""
					+ userdata.get("userName") + "\"}";

			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONObject(sb.toString());

			http.disconnect();

			return json.toMap();

		} catch (Exception e) {
			System.err.println(e);

			return null;
		}
	}
	@PostMapping(value = "/refreshtoken")
	public Object userRefreshToken2(@RequestBody HashMap<String, String> userdata) {
		JSONObject json = new JSONObject();
		try {
			URL url = new URL("http://smstaging.iviscloud.net:8090/keycloakApp/refreshToken");
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("Accept", "application/json");
			http.setRequestProperty("Authorization", "Bearer {token}");
			http.setRequestProperty("Content-Type", "application/json");

			String data = "{\n\n    \"userName\": \""
					+ userdata.get("userName") + "\",\n    \"calling_System_Detail\": \""
					+ userdata.get("calling_System_Detail") + "\",\n    \"refreshToken\":\""
					+ userdata.get("refreshToken") + "\"\n\n}";
			byte[] out = data.getBytes(StandardCharsets.UTF_8);

			OutputStream stream = http.getOutputStream();
			stream.write(out);
			InputStream resp = http.getInputStream();
			StringBuilder sb = new StringBuilder();
			for (int ch; (ch = resp.read()) != -1;) {
				sb.append((char) ch);
			}
			json = new JSONObject(sb.toString());

			http.disconnect();

			return json.toMap();

		} catch (Exception e) {
			System.err.println(e);

			return null;
		}
	}
	

}
