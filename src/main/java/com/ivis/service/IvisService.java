package com.ivis.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ivis.ApplicationModels.account;
import com.ivis.Businessentity.BIAnalyticsEntity;
import com.ivis.Businessentity.BusinessCamesEntity;
import com.ivis.Businessentity.BusinessEntity;
import com.ivis.Businessentity.CamerasEntity;
import com.ivis.Businessentity.SitesEntity;
import com.ivis.Businessentity.UserEntity;
import com.ivis.util.ReadJson;
import com.ivis.util.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.ivis.ApplicationModels.Analysis;
import com.ivis.ApplicationModels.Services;

@Service
public class IvisService {

	public UserEntity getImMatrixAvailability(String uId) {

		UserEntity u = new UserEntity();
		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/GetSitesListForUser_1_0?uId=";
		sitesUrl = sitesUrl + uId;

		String response = util.readUrlData(sitesUrl);

		Gson gson = new Gson();

		String camResponse = util.readUrlData(sitesUrl);
		List<BusinessEntity> devList = new ArrayList<BusinessEntity>();
		;
		SitesEntity siteData = (SitesEntity) new Gson().fromJson(camResponse, SitesEntity.class);

		List<account> sitesList = siteData.getSiteList();

		for (account s : sitesList) {
			BusinessEntity sites = new BusinessEntity();
			sites.setSiteid(s.getAccountId());
			sites.setSitename(s.getAccountName());
			sites.setProject(s.getFacility());
			sites.setAtmid(s.getCustomerSiteId());
			sites.setState(s.getState());
			sites.setCity(s.getCity());
			sites.setZone(s.getZone());
			sites.setPhase(s.getBatchNo());
			sites.setDeviceInternalId(s.getPotentialId());
			sites.setDeviceExternalId(s.getUnitId());
			sites.setLocationType(s.getSiteType());
			sites.setBusinessvertical(s.getIndustry());
			sites.setLatitude(s.getLatitude());
			sites.setLongitude(s.getLongitude());
			devList.add(sites);

		}

		u.setAddress(siteData.getAddress());
		u.setContact(siteData.getContact());
		u.setEmail(siteData.getEmail());
		u.setUserName(siteData.getUserName());
		u.setUserRole(siteData.getUserRole());
		u.setUserType(siteData.getUserType());
		u.setSiteList(devList);
		return u;

	}

	// Cameras API
	public List<BusinessCamesEntity> getCamerasList(String uId, String accountId) {
		String camerasUrl = "http://smstaging.iviscloud.net:8090/cpus/cameras/CameraListforSiteId_1_0?uId=";
		camerasUrl = camerasUrl + uId + "&accountId=" + accountId;
		String response = util.readUrlData(camerasUrl);

		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<CamerasEntity>>() {
		}.getType();
		List<CamerasEntity> camesData = (List<CamerasEntity>) new Gson().fromJson(response, collectionType);
		List<BusinessCamesEntity> camesList = new ArrayList<BusinessCamesEntity>();
		for (CamerasEntity c : camesData) {
			BusinessCamesEntity cames = new BusinessCamesEntity();
			cames.setCameraIndex(c.getId());
			cames.setIndexNo(c.getIndexNo());
			cames.setDeviceInternalId(c.getPotentialId());
			cames.setCameraId(c.getCameraId());
			cames.setDeviceExternalId(c.getDeviceId());
			cames.setCameraName(c.getName());
			cames.setServerHost(c.getServerHost());
			cames.setServerPort(c.getServerPort());
			cames.setServerCameraId(c.getServerCameraId());
			cames.setRtspUrl(c.getRtspUrl());
			cames.setUserName(c.getUserName());
			cames.setPassword(c.getPassword());
			cames.setWidth(c.getWidth());
			cames.setHeight(c.getHeight());
			cames.setFps(c.getFps());
			cames.setOnvifUrl(c.getOnvifUrl());
			cames.setPtz(c.getPtz());
			cames.setActive(c.getActive());
			cames.sethTTPTunnel(c.gethTTPTunnel());
			cames.setPushType(c.getPushType());
			cames.setServerHttpPort(c.getServerHttpPort());
			cames.setEventDuration(c.getEventDuration());
			cames.setEventFrames(c.getEventFrames());
			cames.setEventPushUrl(c.getEventPushUrl());
			cames.setMotionDetectionType(c.getMotionDetectionType());
			cames.setMotionDetectionLevel(c.getMotionDetectionLevel());
			cames.setGprsEventDuration(c.getGprsEventDuration());
			cames.setArchiveType(c.getArchiveType());
			cames.setArchiveLocation(c.getArchiveLocation());
			cames.setArchiveDays(c.getArchiveDays());
			cames.setTranscode(c.getTranscode());
			cames.setSpf(c.getSpf());
			cames.setBitrate(c.getBitrate());
			cames.setSnapshotDuration(c.getSnapshotDuration());
			cames.setEventOnSms(c.getEventOnSms());
			cames.setMotiondetectionId(c.getMotiondetectionId());
			cames.setEventPushType(c.getEventPushType());
			cames.setMotionAnalytics(c.getMotionAnalytics());
			cames.setCameraType(c.getCameraType());
			cames.setGtEnabled(c.getGtEnabled());
			cames.setHbCheck(c.getHbCheck());
			cames.setDisplayName(c.getDisplayName());
			cames.setEventPushRetryCount(c.getEventPushRetryCount());
			cames.setCategory(c.getCategory());
			camesList.add(cames);
			String serverHost = c.getServerHost();
			String c1StatusUrl = "http://usvs1.iviscloud.net:7888/Command?action=";
			c1StatusUrl = c1StatusUrl + "status" + "&cameraId=" + c.getCameraId();

			boolean cam_status = false;
			String resp = util.readUrlData(c1StatusUrl);
			// JSONArray respArray = new JSONArray(resp);
			// JSONObject ob = respArray.getJSONObject(0);
			// String cId = ob.getString("cameraId");

			/*
			 * if (cId != null && cId.equalsIgnoreCase(c.getCameraId())) { String time = "";
			 * String camera_status = ob.getString("status");
			 * 
			 * if (camera_status != null && camera_status.equalsIgnoreCase("Connected")) {
			 * time = ob.getString("timeStamp"); cam_status = true; } else if (camera_status
			 * != null && camera_status.equalsIgnoreCase("Disconnected")) { cam_status =
			 * false; }
			 * 
			 * cames.setCameraStatus(camera_status); cames.setTimeStamp(time);
			 * camesList.add(cames); }
			 */
		}

		return camesList;

	}

	public List<BIAnalyticsEntity> getBusinessAnalystics(int accountId, Date date) {
		List<BIAnalyticsEntity> bIAnalytics = new ArrayList<BIAnalyticsEntity>();

		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + accountId;

		String client_id = util.readUrlData(sitesUrl);

		HttpPost post = new HttpPost("https://ivisbi.com/v2/api/getServices");
		HttpPost post2 = new HttpPost("https://ivisbi.com/v2/api/analytics");
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", client_id));

		if(!(date==null))
		{
			urlParameters.add(new BasicNameValuePair("date", date.toString()));

		}
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			Type collection = new TypeToken<Services>() {
			}.getType();
			Services services = new Gson().fromJson(input, collection);

			for (com.ivis.ApplicationModels.Service s : services.getServices()) {

				urlParameters.add(new BasicNameValuePair("parameter", s.getId().toString()));
				post2.setEntity(new UrlEncodedFormEntity(urlParameters));
				CloseableHttpResponse response2 = httpClient.execute(post2);

				int statusCode = response2.getStatusLine().getStatusCode();
				BIAnalyticsEntity biEntity = new BIAnalyticsEntity();

				if (statusCode == 200) {
					String result = EntityUtils.toString(response2.getEntity());

					JsonObject jsonResp = new Gson().fromJson(result, JsonObject.class);
					JsonArray analysis = jsonResp.getAsJsonArray(s.getParameter());
					Type collectionType = new TypeToken<List<Analysis>>() {
					}.getType();
					List<Analysis> analyticsObj = new Gson().fromJson(analysis, collectionType);

					biEntity.setService(s.getParameter());
					biEntity.setAnalytics(analyticsObj);

				} else {
					biEntity.setService(s.getParameter());
					biEntity.setAnalytics(new ArrayList<Analysis>());
				}
				bIAnalytics.add(biEntity);
				urlParameters.remove(1);
			}

		} catch (Exception e) {
			System.err.println(e);
		}

		return bIAnalytics;
	}

	public Object mapServices2(String url,String Request_type ) {

		ReadJson reader = new ReadJson();
		HashMap<Object, Object> mappeddata = new HashMap<Object, Object>();
		HashMap<Object, Object> data = new HashMap<Object, Object>();
		org.json.JSONObject json;
		try {
			json = reader.readJsonFromUrl(url);

			mappeddata.put("SiteId", json.get("accountId"));

			mappeddata.put("SiteName", json.get("siteName"));

			if(Request_type.equals("Services")) {
			data.put("alarms", json.get("monitoring"));
			data.put("safety_escort", json.get("safety_escort"));
			data.put("LiveView", (((json.getInt("noOfCamerasLivePortal")) > 0) ? "T" : "F"));
			data.put("advertising", json.get("advertising"));
			data.put("business_intelligence", json.get("business_intelligence"));
			mappeddata.put("Services", data);
			}
			if(Request_type!=null && Request_type.equals("Ads")) {
			data = new HashMap<Object, Object>();
			HashMap<Object, Object> data2 = new HashMap<Object, Object>();
			data2.put("screen0", "Connected");
			for (int i = 0; i <= json.getInt("noOfScreensAtSite"); i++) {
				data2.put("screen" + i, "Connected");
			}
			data.put("screens", data2);
			mappeddata.put("Ads", data);
			}
			if(Request_type!=null && Request_type.equals("Analytics")) {
			mappeddata.put("Analytics", this.getAnalyticsdata());
			}
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
//		catch (org.json.JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return mappeddata;

	}

	public Object getAnalyticsdata() {
		ArrayList<Object> data = new ArrayList<Object>();
		ArrayList<Object> data2 = new ArrayList<Object>();
		HashMap<Object, Object> m = new HashMap<Object, Object>();

		HttpPost post = new HttpPost("https://ivisbi.com/v2/api/analytics");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", "6"));
		urlParameters.add(new BasicNameValuePair("parameter", "12"));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			JSONObject json = new JSONObject(input);
			//System.out.println(json);
			HashMap<Object, Object> temp = new HashMap<Object, Object>();
			temp.put("Variance", "3");
			temp.put("count", "134");
			temp.put("status", "raise");
			HashMap<Object, Object> temp2 = new HashMap<Object, Object>();
			temp2.put("day", temp);
			temp2.put("week", temp);
			temp2.put("month", temp);
			m.put("Customer Count", temp2);
			data.add(m);
			m = new HashMap<Object, Object>();
			m.put("Average Waiting time", temp2);
			data.add(m);
			return data;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public Object getbiAnalyticsReport(int siteId, Date fromDate, Date toDate) {
		// TODO Auto-generated method stub

		String sitesUrl = "http://smstaging.iviscloud.net:8090/cpus/sites/getBICustomerSiteId_1_0?accId=";
		sitesUrl = sitesUrl + siteId;

		String client_id = util.readUrlData(sitesUrl);
		
		HttpPost post = new HttpPost("http://ivisbi.com/v2/api/analyticsReport");

		// add request parameter, form parameters
		List<NameValuePair> urlParameters = new ArrayList<>();
		urlParameters.add(new BasicNameValuePair("client_id", "8"));
		urlParameters.add(new BasicNameValuePair("fromDate", fromDate.toString()));
		urlParameters.add(new BasicNameValuePair("toDate", toDate.toString()));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();CloseableHttpResponse response = httpClient.execute(post)) {

			String input = EntityUtils.toString(response.getEntity());
			System.out.println(input);
			JSONObject json = new JSONObject(input);
			//System.out.println(json);
			return input;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

}
